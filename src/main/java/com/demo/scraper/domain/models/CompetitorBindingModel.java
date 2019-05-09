package com.demo.scraper.domain.models;

import com.demo.scraper.domain.entities.BaseEntity;
import com.demo.scraper.domain.entities.Log;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CompetitorBindingModel extends BaseEntity {

    private Long productId;
    private String url;
    private String xPathToPrice;
    private String xPathToTitle;

    @NotNull(message = "Field cannot be empty")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @NotNull(message = "Field cannot be empty")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NotNull(message = "Field cannot be empty")
    public String getxPathToPrice() {
        return xPathToPrice;
    }

    public void setxPathToPrice(String xPathToPrice) {
        this.xPathToPrice = xPathToPrice;
    }

    @NotNull(message = "Field cannot be empty")
    public String getxPathToTitle() {
        return xPathToTitle;
    }

    public void setxPathToTitle(String xPathToTitle) {
        this.xPathToTitle = xPathToTitle;
    }
}
