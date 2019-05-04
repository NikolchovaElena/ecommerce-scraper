package com.demo.scraper.domain.models;

import com.demo.scraper.domain.entities.BaseEntity;
import com.demo.scraper.domain.entities.Log;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductBindingModel extends BaseEntity {

    private String name;
    private String url;
    private String xPathToPrice;
    private String xPathToTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getxPathToPrice() {
        return xPathToPrice;
    }

    public void setxPathToPrice(String xPathToPrice) {
        this.xPathToPrice = xPathToPrice;
    }

    public String getxPathToTitle() {
        return xPathToTitle;
    }

    public void setxPathToTitle(String xPathToTitle) {
        this.xPathToTitle = xPathToTitle;
    }
}
