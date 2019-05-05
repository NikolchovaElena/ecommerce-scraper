package com.demo.scraper.repository;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Log findFirstByProductOrderByPriceAsc(Product product);

    Log findFirstByProductOrderByDateAsc(Product product);
}
