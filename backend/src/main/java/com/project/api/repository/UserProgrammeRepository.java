package com.project.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.entity.UserProgramme;

@Repository
public interface UserProgrammeRepository extends JpaRepository<UserProgramme, Long> {
    List<UserProgramme> findByUserId(Long userId);
    List<UserProgramme> findByUserIdAndStatut(Long userId, String statut);
    boolean existsByUserIdAndStatut(Long userId, String statut);
}
