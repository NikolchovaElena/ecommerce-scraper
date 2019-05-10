package com.demo.scraper.util;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.util.EditDataService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class XEditableImpl implements EditDataService {
    @Override
    public void edit(Competitor competitor, String name, String value) {
        switch (name) {
            case "url":
                competitor.setUrl(value);
                break;
            case "xPathToPrice":
                competitor.setxPathToPrice(value);
                break;
            case "xPathToTitle":
                competitor.setxPathToTitle(value);
                break;
            default:
                throw new IllegalArgumentException("Competitor has no param with that name");
        }
    }

    @Override
    public void edit(Product product, String name, String value) {
        switch (name) {
            case "name":
                product.setName(value);
                break;
            case "currentPrice":
                product.setCurrentPrice(new BigDecimal(value));
                break;
            case "currency":
                product.setCurrency(value);
                break;
            default:
                throw new IllegalArgumentException("Product has no param with that name");
        }
    }

    @Override
    public void edit(Product product, String value) {
        product.setName(value);
    }

}
