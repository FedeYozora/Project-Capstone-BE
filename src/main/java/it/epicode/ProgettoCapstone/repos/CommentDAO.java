package it.epicode.ProgettoCapstone.repos;

import it.epicode.ProgettoCapstone.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.work.id = :id")
    List<Comment> findCommentsByWorkId(Long id);
}
