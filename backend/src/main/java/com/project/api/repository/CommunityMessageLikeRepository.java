package com.project.api.repository;

import com.project.api.entity.CommunityMessageLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMessageLikeRepository extends JpaRepository<CommunityMessageLike, Long> {
    
    @Query("SELECT ml FROM CommunityMessageLike ml WHERE ml.message.id = :messageId AND ml.user.id = :userId")
    Optional<CommunityMessageLike> findByMessageIdAndUserId(@Param("messageId") Long messageId, @Param("userId") Long userId);
    
    @Query("SELECT COUNT(ml) FROM CommunityMessageLike ml WHERE ml.message.id = :messageId")
    Long countByMessageId(@Param("messageId") Long messageId);
    
    @Query("SELECT ml FROM CommunityMessageLike ml WHERE ml.user.id = :userId ORDER BY ml.dateCreation DESC")
    List<CommunityMessageLike> findByUserId(@Param("userId") Long userId);
    
    void deleteByMessageIdAndUserId(Long messageId, Long userId);
    
    boolean existsByMessageIdAndUserId(Long messageId, Long userId);
}