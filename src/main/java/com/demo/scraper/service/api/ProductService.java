package com.demo.scraper.service.api;

import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductDetailsViewModel;
import com.demo.scraper.domain.models.ProductViewModel;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void add(ProductBindingModel model);

    void delete(Long id);

    List<ProductViewModel> findAll();

    ProductDetailsViewModel findBy(Long id);

    void edit(Map<String, String> body);
}
