package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.service.events.OnLowerPriceUpdate;
import com.demo.scraper.util.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    void onLowerPriceChange(Competitor competitor, String scrapedPriceAsString, String scrapedCurrency) {

        BigDecimal currentPrice = competitor.getProduct().getCurrentPrice();
        String defaultCurrency = competitor.getProduct().getCurrency();
        BigDecimal scrapedPrice = new BigDecimal(scrapedPriceAsString);

        scrapedPrice = currencyConverter.convert(scrapedCurrency, defaultCurrency, scrapedPrice);

        //if competitor price is changed and if new price is lower than ours
        if (priceHasChanged(scrapedPrice, competitor) && isLower(scrapedPrice, currentPrice)) {
            initiateEvent(competitor, scrapedPrice, scrapedCurrency);
        }
    }

    // true if scrapedPrice is <= than currentPrice with more than 10%
    private boolean isLower(BigDecimal scrapedPrice, BigDecimal currentPrice) {
        BigDecimal val = currentPrice.multiply(BigDecimal.valueOf(0.9));
        return (scrapedPrice.compareTo(val)) <= 0;
    }

    private boolean priceHasChanged(BigDecimal scrapedPrice, Competitor competitor) {
        Log log = competitor.getLogs().get(competitor.getLogs().size() - 1);

        return log.getPrice().compareTo(scrapedPrice) != 0;
    }

}

