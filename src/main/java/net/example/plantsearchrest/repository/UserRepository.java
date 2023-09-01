package net.example.plantsearchrest.repository;

import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findAll(Pageable pageable);
}
