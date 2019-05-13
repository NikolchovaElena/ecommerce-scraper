# ecommerce-scraper
A custom e-commerce scrapper with email notification functionality<br />

## Key Functionalities

The user creates a list of competitor e-commerce websites to be monitored.<br />
Price information is scraped via HtmlUnit by xPath which the user provides.<br />
Scraping is done on a scheduled time.<br />
Notification emails are sent on price change condition.<br />

## Built With

* Java 11.0.2
* Spring-MVC
* Maven
* Thymeleaf
* Hibernate
* MySQL
* JavaScript (jQuery)

Other tools:
* HtmlUnit to scrape price information
* DataTables for representation of gathered data
* X-editable for in-place editing
* JavaMail for sending notification emails
* Highcharts to visualize price change in time
* ModelMapper
* Bootstrap

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

