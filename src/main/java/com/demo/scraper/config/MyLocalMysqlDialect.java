package com.demo.scraper.config;

import org.hibernate.dialect.MySQL8Dialect;

public class MyLocalMysqlDialect extends MySQL8Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}