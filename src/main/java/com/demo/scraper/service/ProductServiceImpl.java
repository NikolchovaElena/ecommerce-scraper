package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.ProductBindingModel;
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
    public List<ProductViewModel> findAllWithPrices() {
        List<ProductViewModel> products = this.productRepository.findAll().stream()
                .map(p -> {
                    ProductViewModel product = mapper.map(p, ProductViewModel.class);
                    product.setId(p.getId());
                    product.setName(trimProduct(p.getName()));
                    product.setUrl(trimProduct(p.getUrl()));
                    product.setxPathToPrice(trimProduct(p.getxPathToPrice()));
                    product.setxPathToTitle(trimProduct(p.getxPathToTitle()));
                    product.setMinPrice(logService.findMinPrice(p));
                    return product;
                }).collect(Collectors.toList());

        return products;
    }

    @Override
    public ProductViewModel findBy(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No product by that id!"));

        ProductViewModel model = mapper.map(p, ProductViewModel.class);
        model.setMinPrice(logService.findMinPrice(p));
        return model;
    }

    /**
     * runs every 12 hours
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

    private String trimProduct(String s) {
        if (s.length() > MAX_PARAM_LENGTH) {
            return s.substring(0, MAX_PARAM_LENGTH);
        }
        return s;
    }


    //    private void getMinPrice(List<Product> products) {
//        BigDecimal minValue = BigDecimal.ZERO;
//
//        for (Log log : this.logs) {
//            if (log.getPrice().compareTo(minValue) > 0) {
//                minValue = log.getPrice();
//            }
//        }
//        this.minPrice = minValue;
//    }

}
