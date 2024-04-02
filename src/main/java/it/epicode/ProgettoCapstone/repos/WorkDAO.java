package it.epicode.ProgettoCapstone.repos;

import it.epicode.ProgettoCapstone.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkDAO extends JpaRepository<Work, Long> {
    @Query("SELECT c.work FROM Comment c WHERE c.id = :commentId")
    Work findWorkByCommentId(Long commentId);

    @Query("SELECT w FROM Work w WHERE w.featured = true")
    List<Work> findByFeaturedTrue();

    @Query("SELECT w FROM Work w WHERE LOWER(w.name) LIKE %:searchTerm% OR LOWER(w.description) LIKE %:searchTerm%")
    List<Work> findBySearchTermIgnoreCase(String searchTerm);
}
