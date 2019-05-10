package com.demo.scraper.util;


import java.io.IOException;
import java.math.BigDecimal;

public interface CurrencyConverter {

    BigDecimal convert(String from, String to, BigDecimal amountToConvert);
}
