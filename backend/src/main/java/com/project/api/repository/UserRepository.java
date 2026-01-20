package com.project.api.repository;

import com.project.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAdresseEmail(String adresseEmail);
    boolean existsByAdresseEmail(String adresseEmail);
    boolean existsByNumTel(String numTel);
}
