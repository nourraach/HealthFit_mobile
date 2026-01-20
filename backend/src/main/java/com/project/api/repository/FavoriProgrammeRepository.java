package com.project.api.repository;

import com.project.api.entity.FavoriProgramme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriProgrammeRepository extends JpaRepository<FavoriProgramme, Long> {
    
    @Query("SELECT fp FROM FavoriProgramme fp WHERE fp.user.id = :userId AND fp.programme.id = :programmeId")
    Optional<FavoriProgramme> findByUserIdAndProgrammeId(@Param("userId") Long userId, @Param("programmeId") Long programmeId);
    
    @Query("SELECT fp FROM FavoriProgramme fp JOIN FETCH fp.programme WHERE fp.user.id = :userId ORDER BY fp.dateAjout DESC")
    Page<FavoriProgramme> findByUserIdOrderByDateAjoutDesc(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT fp FROM FavoriProgramme fp JOIN FETCH fp.programme WHERE fp.user.id = :userId ORDER BY fp.dateAjout DESC")
    List<FavoriProgramme> findByUserIdOrderByDateAjoutDesc(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(fp) FROM FavoriProgramme fp WHERE fp.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(fp) FROM FavoriProgramme fp WHERE fp.programme.id = :programmeId")
    Long countByProgrammeId(@Param("programmeId") Long programmeId);
    
    boolean existsByUserIdAndProgrammeId(Long userId, Long programmeId);
    
    void deleteByUserIdAndProgrammeId(Long userId, Long programmeId);
}