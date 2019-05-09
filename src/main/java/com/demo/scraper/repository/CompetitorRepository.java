package com.demo.scraper.repository;

import com.demo.scraper.domain.entities.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor,Long> {

}
