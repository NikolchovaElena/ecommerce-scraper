package com.demo.scraper.domain.models;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class ProductViewModel {

    private Long id;
    private String name;
    private String url;
    private String xPathToPrice;
    private String xPathToTitle;
    private String minPrice;

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

    @Length(max = 20)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getxPathToPrice() {
        return xPathToPrice;
    }

    public void setxPathToPrice(String xPathToPrice) {
        this.xPathToPrice = xPathToPrice;
    }

    public String getxPathToTitle() {
        return xPathToTitle;
    }

    public void setxPathToTitle(String xPathToTitle) {
        this.xPathToTitle = xPathToTitle;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}
