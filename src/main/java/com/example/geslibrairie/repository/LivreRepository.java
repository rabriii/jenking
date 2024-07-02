package com.example.geslibrairie.repository;

import com.example.geslibrairie.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}
