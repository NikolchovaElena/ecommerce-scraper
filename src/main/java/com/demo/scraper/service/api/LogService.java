package com.demo.scraper.service.api;

import com.demo.scraper.domain.entities.Product;

public interface LogService {

    void create(Product product, String price, String currency, String title);

    String findMinPrice(Product product);

}
