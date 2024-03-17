package it.epicode.ProgettoCapstone.repos;

import it.epicode.ProgettoCapstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID uuid);

    @Query("SELECT u.username FROM User u WHERE u.id = :id")
    String findUsernameById(UUID id);
}
