package com.demo.scraper.domain.models;

public class CompetitorViewModel {

    private Long id;
    private String productName;
    private String url;
    private String currentTitle;
    private String currentPriceWithCurrency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentPriceWithCurrency() {
        return currentPriceWithCurrency;
    }

    public void setCurrentPriceWithCurrency(String currentPriceWithCurrency) {
        this.currentPriceWithCurrency = currentPriceWithCurrency;
    }
}
