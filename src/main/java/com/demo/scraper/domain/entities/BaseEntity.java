package com.demo.scraper.domain.entities;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
