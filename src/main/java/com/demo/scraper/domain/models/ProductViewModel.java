package com.demo.scraper.domain.models;

import com.demo.scraper.domain.entities.Competitor;

import java.math.BigDecimal;
import java.util.List;

public class ProductViewModel {

    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
