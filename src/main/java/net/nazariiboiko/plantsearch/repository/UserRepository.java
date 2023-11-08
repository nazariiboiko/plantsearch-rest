package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmailOrLogin(String email, String login);
}
