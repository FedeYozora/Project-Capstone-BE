package it.epicode.ProgettoCapstone.repos;

import it.epicode.ProgettoCapstone.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkDAO extends JpaRepository<Work, Long> {
    @Query("SELECT c.work FROM Comment c WHERE c.id = :commentId")
    Work findWorkByCommentId(Long commentId);
}
