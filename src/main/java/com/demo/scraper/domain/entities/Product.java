package com.demo.scraper.domain.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private String url;
    private String xPathToPrice;
    private String xPathToTitle;
    private List<Log> logs;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getxPathToPrice() {
        return xPathToPrice;
    }

    public void setxPathToPrice(String xPathToPrice) {
        this.xPathToPrice = xPathToPrice;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getxPathToTitle() {
        return xPathToTitle;
    }

    public void setxPathToTitle(String xPathToTitle) {
        this.xPathToTitle = xPathToTitle;
    }

    @OneToMany(mappedBy = "product")
    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
