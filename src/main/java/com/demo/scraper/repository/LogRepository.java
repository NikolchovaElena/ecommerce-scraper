package com.demo.scraper.repository;

import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Log findFirstByProductOrderByTimestampDesc(Product product);

    List<Log> findAllByProductId(Long id);
}
