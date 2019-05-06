package com.demo.scraper.domain.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    private Product product;
    private Date timestamp;
    private BigDecimal price;
    private String currency;
    private String title;

    public Log(Product product, BigDecimal price, String currency, String title) {
        this.product = product;
        this.price = price;
        this.currency = currency;
        this.title = title;
    }

    @PrePersist
    public void prePersist() {
        timestamp = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
