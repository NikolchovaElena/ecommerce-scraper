package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.service.events.OnLowerPriceUpdate;
import com.demo.scraper.util.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EventService {

    private ApplicationEventPublisher eventPublisher;
    private CurrencyConverter currencyConverter;

    @Autowired
    public EventService(ApplicationEventPublisher eventPublisher, CurrencyConverter currencyConverter) {
        this.eventPublisher = eventPublisher;
        this.currencyConverter = currencyConverter;
    }

    private void initiateEvent(Competitor competitor, BigDecimal newPrice, String currency) {
        eventPublisher.publishEvent(
                new OnLowerPriceUpdate(competitor, newPrice, currency));
    }

    void onLowerPrice(Competitor competitor, String[] scrapedResult)  {
        if (scrapedResult[0].equals("") || scrapedResult[1].equals("")) {
            return;
        }
        BigDecimal currentPrice = competitor.getProduct().getCurrentPrice();
        String defaultCurrency = competitor.getProduct().getCurrency();
        BigDecimal scrapedPrice = new BigDecimal(scrapedResult[0]);
        String scrapedCurrency = scrapedResult[1];

        scrapedPrice = currencyConverter.convert(scrapedCurrency, defaultCurrency, scrapedPrice);

        if (isLower(scrapedPrice, currentPrice)) {
            initiateEvent(competitor, scrapedPrice, scrapedCurrency);
        }
    }

    // true if scrapedPrice is <= than currentPrice with more than 10%
    private boolean isLower(BigDecimal scrapedPrice, BigDecimal currentPrice) {
        BigDecimal val = currentPrice.multiply(BigDecimal.valueOf(0.9));
        return (scrapedPrice.compareTo(val)) <= 0;
    }

}

