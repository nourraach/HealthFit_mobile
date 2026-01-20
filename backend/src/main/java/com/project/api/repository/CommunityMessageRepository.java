package com.project.api.repository;

import com.project.api.entity.CommunityMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommunityMessageRepository extends JpaRepository<CommunityMessage, Long> {
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.parentMessage IS NULL AND m.isDeleted = false ORDER BY m.dateCreation DESC")
    Page<CommunityMessage> findMainMessages(Pageable pageable);
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.parentMessage.id = :parentId AND m.isDeleted = false ORDER BY m.dateCreation ASC")
    List<CommunityMessage> findRepliesByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.auteur.id = :userId AND m.isDeleted = false ORDER BY m.dateCreation DESC")
    Page<CommunityMessage> findByAuteurId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.contenu LIKE %:keyword% AND m.isDeleted = false AND m.parentMessage IS NULL ORDER BY m.dateCreation DESC")
    Page<CommunityMessage> searchMessages(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(m) FROM CommunityMessage m WHERE m.parentMessage.id = :parentId AND m.isDeleted = false")
    Long countRepliesByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.parentMessage IS NULL AND m.isDeleted = false ORDER BY m.likesCount DESC, m.dateCreation DESC")
    Page<CommunityMessage> findMostLikedMessages(Pageable pageable);
    
    @Query("SELECT m FROM CommunityMessage m WHERE m.parentMessage IS NULL AND m.isDeleted = false AND m.dateCreation >= :weekAgo ORDER BY m.dateCreation DESC")
    Page<CommunityMessage> findRecentMessages(@Param("weekAgo") LocalDateTime weekAgo, Pageable pageable);
}