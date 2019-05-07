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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static int MAX_PARAM_LENGTH = 30;
    private final static String TIME_ZONE = "Europe/Sofia";

    private final ProductRepository productRepository;
    private final LogService logService;
    private final ModelMapper mapper;
    private final ScraperService scraper;

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
    public void delete(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No product by that id!"));
        this.productRepository.delete(p);
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
        model.setCurrentPrice(getCurrentPrice(getCurrentLog(p)));
        return model;
    }

    @Override
    public void edit(Map<String, String> body) {
        String name = body.get("name");
        String value = body.get("value");
        Long id = Long.parseLong(body.get("pk"));

        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No product by that id!"));

        edit(p, name, value);
        productRepository.save(p);
    }

    private void edit(Product product, String name, String value) {
        switch (name) {
            case "url":
                product.setUrl(value);
                break;
            case "xPathToPrice":
                product.setxPathToPrice(value);
                break;
            case "xPathToTitle":
                product.setxPathToTitle(value);
                break;
            default:
                throw new IllegalArgumentException("Product has no param with that name");
        }
    }

    /**
     * runs every day at noon
     */
    @Scheduled(cron = "0 0 12 * * ?", zone = TIME_ZONE)
    //@Scheduled(fixedRate = 18000000)
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

        return log == null ? "" : log.getPrice() + " " + log.getCurrency();
    }

    private String getCurrentTitle(Log log) {
        return log == null ? "" : log.getTitle();
    }
}
