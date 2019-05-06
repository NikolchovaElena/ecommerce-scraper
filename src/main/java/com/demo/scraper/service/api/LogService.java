package com.demo.scraper.service.api;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.LogViewModel;

import java.util.List;

public interface LogService {

    void create(Product product, String price, String currency, String title);

    List<LogViewModel> findAll(Long id);

    Log getCurrentLog(Product product);


}
