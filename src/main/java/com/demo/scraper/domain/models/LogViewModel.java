package com.demo.scraper.domain.models;

import java.math.BigDecimal;

public class LogViewModel {

    private long timestamp;
    private BigDecimal value;

    public LogViewModel(long timestamp, BigDecimal value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
