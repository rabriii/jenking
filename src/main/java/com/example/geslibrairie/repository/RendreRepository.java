package com.example.geslibrairie.repository;

import com.example.geslibrairie.model.Rendre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RendreRepository extends JpaRepository<Rendre, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rendre r WHERE r.pret.idpret = :idpret")
    boolean existsByPretId(@Param("idpret") Long idpret);
}
