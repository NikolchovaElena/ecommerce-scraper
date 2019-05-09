package com.demo.scraper.service.api;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Product;

import java.io.Serializable;

public interface EditDataService extends Serializable {

    void edit(Competitor competitor, String name, String value);

    void edit(Product product, String name, String value);

    void edit(Product product, String value);

}
