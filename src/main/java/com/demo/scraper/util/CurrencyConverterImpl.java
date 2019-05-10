package com.demo.scraper.util;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {

    @Override
    public BigDecimal convert(String from, String to, BigDecimal val)  {

        if (from.equals("USD")) {
            val = val.multiply(BigDecimal.valueOf(1.74206));
        } else if (from.equals("EUR")) {
            val = val.multiply(BigDecimal.valueOf(1.95583));
        }

        if (to.equals("USD")) {
            val = val.divide(BigDecimal.valueOf(1.74206));
        } else if (to.equals("EUR")) {
            val = val.divide(BigDecimal.valueOf(1.95583));
        }

        return val;
    }
}

