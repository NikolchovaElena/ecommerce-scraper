package com.demo.scraper.service.events;

import com.demo.scraper.domain.entities.Competitor;
import org.springframework.context.ApplicationEvent;
import java.math.BigDecimal;

public class OnLowerPriceUpdate extends ApplicationEvent {
    private Competitor competitor;
    private BigDecimal newPrice;
    private String currency;

    public OnLowerPriceUpdate(Competitor competitor, BigDecimal newPrice, String currency) {
        super(competitor);
        this.competitor = competitor;
        this.newPrice = newPrice;
        this.currency = currency;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
