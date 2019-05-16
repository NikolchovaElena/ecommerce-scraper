package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductProjection;
import com.demo.scraper.domain.models.ProductViewModel;
import com.demo.scraper.repository.ProductRepository;
import com.demo.scraper.util.EditDataService;
import com.demo.scraper.service.api.LogService;
import com.demo.scraper.service.api.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String TIME_ZONE = "Europe/Sofia";
    private final static String DEFAULT_CURRENCY = "BGN";

    private final ProductRepository productRepository;
    private final EditDataService editDataService;
    private final EventService eventService;
    private final LogService logService;
    private final ModelMapper mapper;
    private final ScraperService scraper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              EditDataService editDataService, EventService eventService, LogService logService, ModelMapper mapper,
                              ScraperService scraper) {
        this.productRepository = productRepository;
        this.editDataService = editDataService;
        this.eventService = eventService;
        this.logService = logService;
        this.mapper = mapper;
        this.scraper = scraper;
    }

    @Override
    public void add(ProductBindingModel model) {
        Product product = this.productRepository.findByName(model.getName()).orElse(null);

        if (product == null) {
            String currency = (model.getCurrency() == null || model.getCurrency().equals(""))
                    ? DEFAULT_CURRENCY
                    : model.getCurrency();

            BigDecimal currentPrice = (model.getCurrentPrice() == null || model.getCurrentPrice().equals(""))
                    ? BigDecimal.ZERO
                    : new BigDecimal(model.getCurrentPrice());

            product = new Product(model.getName(), currentPrice, currency);
            this.productRepository.save(product);
        }
    }

    @Override
    public void edit(Map<String, String> body) {
        String name = body.get("name");
        String value = body.get("value");
        Long id = Long.parseLong(body.get("pk"));

        Product product = productRepository.findById(id).orElseThrow(()
                -> new NullPointerException("No such product!"));

        editDataService.edit(product, name, value);
        productRepository.save(product);
    }

    @Override
    public void edit(String name, String newName) {
        Product product = productRepository.findByName(name).orElseThrow(()
                -> new NullPointerException("No such product!"));

        editDataService.edit(product, newName);
        productRepository.save(product);
    }

    @Override
    public boolean doesProductExists(Long id) {
        Product p = productRepository.findById(id).orElse(null);
        return p != null;
    }

    public List<ProductProjection> findAllProductNames() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, ProductProjection.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompetitor(Product product, Competitor competitor) {
        product.getCompetitors().remove(competitor);
        productRepository.save(product);
    }

    @Override
    public List<ProductViewModel> findAll() {
        return productRepository.findAll().
                stream()
                .map(p -> mapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    // scrapes competitors product prices and titles
    // creates new log for each scrape
    // runs every day at noon
    @Scheduled(fixedRate = 18000000) //for testing
    // @Scheduled(cron = "0 0 12 * * ?", zone = TIME_ZONE)
    private void updateLogs() throws IOException {
        List<Product> products = this.productRepository.findAll();

        for (Product product : products) {
            for (Competitor competitor : product.getCompetitors()) {
                String[] scrapeResult = this.scraper.scrapeProductInfo(competitor);

                if (scrapeResult != null) {
                    eventService.onLowerPrice(competitor, scrapeResult);
                    logService.create(competitor, scrapeResult[0], scrapeResult[1], scrapeResult[2]);
                }
            }
        }
    }

    // deletes all products with no competitors
    // runs every day at midnight
    @Scheduled(fixedRate = 18000000) //for testing
    //@Scheduled(cron = "0 0 0 * * ?", zone = TIME_ZONE)
    private void removeProductsWithNoCompetitors() {
        List<Product> products = this.productRepository.findAllByCompetitorsIsEmpty();
        this.productRepository.deleteAll(products);
    }


}
