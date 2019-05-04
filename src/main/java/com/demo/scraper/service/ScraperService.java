package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Product;
import com.demo.scraper.service.api.LogService;
import com.demo.scraper.service.api.ProductService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScraperService {
    private final String priceRegex = "\\d{1,}[.|,]\\d+";
    private final WebClient webClient;

    @Autowired
    public ScraperService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String[] scrapeProductInfo(Product product) throws IOException {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        String searchUrl = product.getUrl();
        HtmlPage page = webClient.getPage(searchUrl);

        HtmlElement productPrice = page.getFirstByXPath(product.getxPathToPrice());
        HtmlElement productTitle = page.getFirstByXPath(product.getxPathToTitle());

        String title = productTitle == null ? "not found" : productTitle.asText();
        String price = getPrice(productPrice.asText());
        String currency = getCurrency(productPrice.asText());

        return new String[]{price, currency, title};
    }

    private String getPrice(String input) {
        Pattern pattern = Pattern.compile(priceRegex);
        Matcher m = pattern.matcher(input);

        if (m.find()) {
            String price = m.group().contains(",")
                    ? m.group().replace(",", ".")
                    : m.group();

            return price;
        }
        return "";
    }

    private String getCurrency(String input) {
        String currency;

        if (input.contains("$") || input.contains("USD")) {
            currency = "USD";
        } else if (input.contains("€") || input.contains("EUR")) {
            currency = "EUR";
        } else if (input.contains("лв") || input.contains("BGN")) {
            currency = "BGN";
        } else {
            currency = "unknown";
        }
        return currency;
    }

}
