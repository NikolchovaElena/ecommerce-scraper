package com.demo.scraper.service.api;

import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductViewModel;

import java.util.List;

public interface ProductService {

    void add(ProductBindingModel model);

    List<ProductViewModel> findAllWithPrices();

    ProductViewModel findBy(Long id);
}
