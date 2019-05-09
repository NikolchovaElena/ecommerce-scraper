package com.demo.scraper.domain.entities;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "competitors")
public class Competitor extends BaseEntity{

    private String url;
    private String xPathToPrice;
    private String xPathToTitle;
    private List<Log> logs;
    private Product product;

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

    @OneToMany(mappedBy = "competitor",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


