package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Email;
import com.demo.scraper.domain.models.EmailViewModel;
import com.demo.scraper.repository.EmailRepository;
import com.demo.scraper.repository.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService  {
    private final EmailRepository emailRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, ModelMapper mapper) {
        this.emailRepository = emailRepository;
        this.mapper = mapper;
    }

    @Override
    public void add(String email) {
        Email e = new Email(email);
        emailRepository.save(e);
    }

    @Override
    public void delete(Long id) {
        Email email = emailRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No email with that id!"));
        emailRepository.delete(email);
    }

    @Override
    public List<EmailViewModel> findAll() {
        return emailRepository.findAll()
                .stream()
                .map(e -> mapper.map(e, EmailViewModel.class))
                .collect(Collectors.toList());
    }
}
