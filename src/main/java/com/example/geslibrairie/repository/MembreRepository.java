package com.example.geslibrairie.repository;

import com.example.geslibrairie.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {
}
