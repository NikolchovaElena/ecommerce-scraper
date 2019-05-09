package com.demo.scraper.repository;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Log findFirstByCompetitorOrderByTimestampDesc(Competitor competitor);

    List<Log> findAllByCompetitorId(Long id);
}
