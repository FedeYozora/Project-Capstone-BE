package it.epicode.ProgettoCapstone.controllers;

import it.epicode.ProgettoCapstone.entities.Comment;
import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.exceptions.BadRequestException;
import it.epicode.ProgettoCapstone.payloads.NewComment;
import it.epicode.ProgettoCapstone.services.CommentSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentCTRL {
    @Autowired
    private CommentSRV commentSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Comment> getAllComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.commentSRV.getComments(page, size, orderBy);
    }

    @GetMapping("/visible")
    public List<Comment> getVisibleComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return this.commentSRV.getVisibleComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return this.commentSRV.getCommentById(id);
    }

    @GetMapping("/works/{id}")
    public List<Comment> getCommentsByWorkId(@PathVariable Long id) {
        return this.commentSRV.getCommentByWorkId(id);
    }

    @GetMapping("/works/{id}/visible")
    public List<Comment> getCommentsByWorkIdAndCommentStatus(@PathVariable Long id) {
        return this.commentSRV.getCommentByWorkIdAndCommentStatus(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN')")
    public Comment saveComment(@RequestBody @Validated NewComment newComment, @AuthenticationPrincipal User user, @RequestParam Long workId, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.commentSRV.createComment(newComment, user, workId);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Comment updateCommentById(@PathVariable Long id, @RequestBody @Validated NewComment newComment, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.commentSRV.updateCommentById(newComment, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCommentById(@PathVariable Long id) {
        this.commentSRV.deleteComment(id);
    }
}
