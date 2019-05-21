package com.demo.scraper.config;

import org.hibernate.dialect.PostgreSQL9Dialect;

public class MyLocalPostgreSQLDialect extends PostgreSQL9Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}