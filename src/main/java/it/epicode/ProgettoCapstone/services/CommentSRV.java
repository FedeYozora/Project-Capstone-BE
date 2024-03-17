package it.epicode.ProgettoCapstone.services;

import it.epicode.ProgettoCapstone.entities.Comment;
import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.enums.CommentStatus;
import it.epicode.ProgettoCapstone.exceptions.NotFoundException;
import it.epicode.ProgettoCapstone.payloads.NewComment;
import it.epicode.ProgettoCapstone.repos.CommentDAO;
import it.epicode.ProgettoCapstone.repos.UserDAO;
import it.epicode.ProgettoCapstone.repos.WorkDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentSRV {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private WorkDAO workDAO;

    @Autowired
    private UserDAO userDAO;

    public Page<Comment> getComments(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return commentDAO.findAll(pageable);

    }

    public Comment getCommentById(Long id) {
        return commentDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Comment> getCommentByWorkId(Long id) {
        return commentDAO.findCommentsByWorkId(id);
    }

    public Comment createComment(NewComment newComment, User user, Long workId) {
        User userFound = userDAO.findById(user.getId()).orElseThrow();
        Comment comment = new Comment();
        comment.setContent(newComment.content());
        comment.setCommentStatus(CommentStatus.VISIBLE);
        comment.setCreatedAt(LocalDate.now());
        comment.setAuthor(userDAO.findUsernameById(userFound.getId()));
        comment.setUser(userFound);
        comment.setWork(workDAO.findById(workId).orElseThrow(() -> new EntityNotFoundException("Work not found")));
        return commentDAO.save(comment);
    }

    public Comment updateCommentById(NewComment updatedComment, Long id) {
        Comment found = getCommentById(id);
        found.setContent(updatedComment.content());
        found.setCommentStatus(updatedComment.commentStatus());
        commentDAO.save(found);
        return found;
    }

    public void deleteComment(Long id) {
        Comment found = getCommentById(id);
        commentDAO.delete(found);
    }
}
