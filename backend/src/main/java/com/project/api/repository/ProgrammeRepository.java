package com.project.api.repository;

import com.project.api.entity.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    List<Programme> findByObjectif(String objectif);
    
    // ✅ FIXED: Load programme with plats first, then activities separately to avoid MultipleBagFetchException
    @Query("SELECT DISTINCT p FROM Programme p " +
           "LEFT JOIN FETCH p.plats " +
           "WHERE p.id = :id")
    Optional<Programme> findByIdWithPlats(@Param("id") Long id);
    
    @Query("SELECT DISTINCT p FROM Programme p " +
           "LEFT JOIN FETCH p.activites " +
           "WHERE p.id = :id")
    Optional<Programme> findByIdWithActivites(@Param("id") Long id);
}
