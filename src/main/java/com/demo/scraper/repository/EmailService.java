package com.demo.scraper.repository;

import com.demo.scraper.domain.models.EmailViewModel;

import java.util.List;

public interface EmailService {

    void add(String email);

    void delete(Long id);

    List<EmailViewModel> findAll();

}
