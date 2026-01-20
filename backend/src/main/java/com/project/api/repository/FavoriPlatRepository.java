package com.project.api.repository;

import com.project.api.entity.FavoriPlat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriPlatRepository extends JpaRepository<FavoriPlat, Long> {
    
    @Query("SELECT fp FROM FavoriPlat fp WHERE fp.user.id = :userId AND fp.plat.id = :platId")
    Optional<FavoriPlat> findByUserIdAndPlatId(@Param("userId") Long userId, @Param("platId") Long platId);
    
    @Query("SELECT fp FROM FavoriPlat fp JOIN FETCH fp.plat WHERE fp.user.id = :userId ORDER BY fp.dateAjout DESC")
    Page<FavoriPlat> findByUserIdOrderByDateAjoutDesc(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT fp FROM FavoriPlat fp JOIN FETCH fp.plat WHERE fp.user.id = :userId ORDER BY fp.dateAjout DESC")
    List<FavoriPlat> findByUserIdOrderByDateAjoutDesc(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(fp) FROM FavoriPlat fp WHERE fp.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(fp) FROM FavoriPlat fp WHERE fp.plat.id = :platId")
    Long countByPlatId(@Param("platId") Long platId);
    
    boolean existsByUserIdAndPlatId(Long userId, Long platId);
    
    void deleteByUserIdAndPlatId(Long userId, Long platId);
}