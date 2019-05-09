package com.demo.scraper.domain.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductBindingModel {

    private String name;
    private String currentPrice;
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
