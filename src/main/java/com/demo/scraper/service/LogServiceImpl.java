package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.repository.LogRepository;
import com.demo.scraper.service.api.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void create(Product product, String scrapedPrice, String currency, String title) {
        BigDecimal price = scrapedPrice.isEmpty()
                ? BigDecimal.ZERO
                : new BigDecimal(scrapedPrice);

        Log log = new Log(product, price, currency, title);
        logRepository.save(log);
    }

    public Log getCurrentLog(Product product) {
        return this.logRepository.findFirstByProductOrderByDateAsc(product);
    }

    @Override
    public String findMinPrice(Product product) {
        Log log = logRepository.findFirstByProductOrderByPriceAsc(product);
        return log == null ? "0" : log.getPrice() + " " + log.getCurrency();
    }
}
