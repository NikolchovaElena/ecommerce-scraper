package com.demo.scraper.service.api;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductProjection;
import com.demo.scraper.domain.models.ProductViewModel;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void add(ProductBindingModel model);

    //TODO refactor
    void edit(Map<String, String> body);

    //TODO refactor
    void edit(String oldName, String newName);

    boolean doesProductExists(Long id);

    List<ProductProjection> findAllProductNames();

    void deleteCompetitor(Product product, Competitor competitor);

    List<ProductViewModel> findAll();
}
