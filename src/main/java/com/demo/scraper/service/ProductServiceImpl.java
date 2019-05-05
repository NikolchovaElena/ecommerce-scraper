package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductDetailsViewModel;
import com.demo.scraper.domain.models.ProductViewModel;
import com.demo.scraper.repository.ProductRepository;
import com.demo.scraper.service.api.LogService;
import com.demo.scraper.service.api.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static int MAX_PARAM_LENGTH = 30;

    private ProductRepository productRepository;
    private LogService logService;
    private ModelMapper mapper;
    private ScraperService scraper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, LogService logService, ModelMapper mapper, ScraperService scraper) {
        this.productRepository = productRepository;
        this.logService = logService;
        this.mapper = mapper;
        this.scraper = scraper;
    }

    @Override
    public void add(ProductBindingModel model) {
        Product product = mapper.map(model, Product.class);
        productRepository.save(product);
    }

    @Override
    public List<ProductViewModel> findAll() {
        List<ProductViewModel> products = this.productRepository.findAll().stream()
                .map(p -> {
                    ProductViewModel product = mapper.map(p, ProductViewModel.class);
                    product.setId(p.getId());
                    product.setName(trimProduct(p.getName(), MAX_PARAM_LENGTH));
                    product.setUrl(trimProduct(p.getUrl(), MAX_PARAM_LENGTH));
                    product.setCurrentPrice(getCurrentPrice(getCurrentLog(p)));
                    product.setCurrentTitle(getCurrentTitle(getCurrentLog(p)));
                    return product;
                }).collect(Collectors.toList());

        return products;
    }

    @Override
    public ProductDetailsViewModel findBy(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No product by that id!"));

        ProductDetailsViewModel model = mapper.map(p, ProductDetailsViewModel.class);
        model.setCurrentPrice(logService.findMinPrice(p));
        return model;
    }

    //TODO substitute with cron

    /**
     * runs every 24 hours
     */
    @Scheduled(fixedRate = 86400000)
    private void updateLogs() throws IOException {
        List<Product> products = this.productRepository.findAll();

        if (products.size() == 0) {
            return;
        }
        for (Product product : products) {
            String[] scrapeResult = this.scraper.scrapeProductInfo(product);

            logService.create(product, scrapeResult[0], scrapeResult[1], scrapeResult[2]);
        }
    }

    private String trimProduct(String s, int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength);
        }
        return s;
    }

    private Log getCurrentLog(Product product) {
        return this.logService.getCurrentLog(product);
    }

    private String getCurrentPrice(Log log) {
        return log.getPrice() + " " + log.getCurrency();
    }

    private String getCurrentTitle(Log log) {
        return log.getTitle();
    }
}
