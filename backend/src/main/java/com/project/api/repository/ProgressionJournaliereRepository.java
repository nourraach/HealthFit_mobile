package com.project.api.repository;

import com.project.api.entity.ProgressionJournaliere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressionJournaliereRepository extends JpaRepository<ProgressionJournaliere, Long> {
    List<ProgressionJournaliere> findByUserProgrammeId(Long userProgrammeId);
    Optional<ProgressionJournaliere> findByUserProgrammeIdAndDate(Long userProgrammeId, LocalDate date);
    List<ProgressionJournaliere> findByUserProgrammeIdOrderByDateDesc(Long userProgrammeId);
    long countByUserProgrammeIdAndStatutJour(Long userProgrammeId, String statutJour);
}
