package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.LogViewModel;
import com.demo.scraper.repository.LogRepository;
import com.demo.scraper.service.api.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

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

    @Override
    public List<LogViewModel> findAll(Long id) {
        return logRepository.findAllByProductId(id)
                .stream()
                .map(l -> new LogViewModel(l.getTimestamp().getTime(), l.getPrice()))
                .collect(Collectors.toList());
    }

    public Log getCurrentLog(Product product) {
        return this.logRepository.findFirstByProductOrderByTimestampDesc(product);
    }



}
