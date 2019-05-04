package com.demo.scraper.domain.models;

import com.demo.scraper.domain.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScrapedBindingModel {

    private String price;
    private String title;

    public ScrapedBindingModel() {
    }

    public ScrapedBindingModel(String price, String title) {
        this.price = price;
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
