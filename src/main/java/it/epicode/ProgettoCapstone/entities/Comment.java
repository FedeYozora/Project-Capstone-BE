package it.epicode.ProgettoCapstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.ProgettoCapstone.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private LocalDate createdAt;
    private String author;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "works_id")
    private Work work;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(String content, CommentStatus commentStatus, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDate.now();
        this.commentStatus = CommentStatus.VISIBLE;
    }
}