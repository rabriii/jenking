package com.example.geslibrairie.repository;

import com.example.geslibrairie.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {
    @Query("SELECT p FROM Pret p " +
            "WHERE p.daterendu < :currentDate " +
            "AND p.idpret NOT IN (SELECT r.pret.idpret FROM Rendre r)")
    List<Pret> findOverduePretsNotReturned(@Param("currentDate") LocalDateTime currentDate);
}