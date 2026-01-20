package com.project.api.repository;

import com.project.api.entity.ActiviteSportive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiviteSportiveRepository extends JpaRepository<ActiviteSportive, Long> {
    List<ActiviteSportive> findByType(String type);
    List<ActiviteSportive> findByNiveau(String niveau);
}
