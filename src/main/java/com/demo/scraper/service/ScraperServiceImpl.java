package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.service.api.ScraperService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScraperServiceImpl implements ScraperService {
    private final String priceRegex = "\\d{1,}[.|,]\\d+";
    private final WebClient webClient;

    @Autowired
    public ScraperServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public String scrapeProductInfo(String searchUrl, String xPath) {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = webClient.getPage(searchUrl);

            HtmlElement scrapedElement = page.getFirstByXPath(xPath);
            return scrapedElement.asText();
        } catch (Exception e) {
            return null;
        }
    }

    String scrapePrice(Competitor competitor) {
        String scrapedElement = scrapeProductInfo(competitor.getUrl(),competitor.getxPathToPrice());

        if (scrapedElement != null) {
            Pattern pattern = Pattern.compile(priceRegex);
            Matcher m = pattern.matcher(scrapedElement);

            if (m.find()) {
                String price = m.group().contains(",")
                        ? m.group().replace(",", ".")
                        : m.group();

                return price;
            }
        }
        return null;
    }

    String scrapeTitle(Competitor competitor) {
        String scrapedElement = scrapeProductInfo(competitor.getUrl(),competitor.getxPathToTitle());

        if (scrapedElement != null) {
            return scrapedElement;
        }
        return null;
    }

    String scrapeCurrency(Competitor competitor) {
        String scrapedElement = scrapeProductInfo(competitor.getUrl(),competitor.getxPathToPrice());
        String currency = null;

        if (scrapedElement != null) {
            if (scrapedElement.contains("$") || scrapedElement.contains("USD")) {
                currency = "USD";
            } else if (scrapedElement.contains("€") || scrapedElement.contains("EUR")) {
                currency = "EUR";
            } else if (scrapedElement.contains("лв") || scrapedElement.contains("BGN")) {
                currency = "BGN";
            }
        }
        return currency;
    }

}
