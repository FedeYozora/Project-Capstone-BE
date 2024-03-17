package it.epicode.ProgettoCapstone.repos;

import it.epicode.ProgettoCapstone.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkDAO extends JpaRepository<Work, Long> {
}
