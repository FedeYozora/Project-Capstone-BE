package it.epicode.ProgettoCapstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.ProgettoCapstone.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String avatarUser;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Work work;
    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(String content, CommentStatus commentStatus, String author) {
        this.content = content;
        this.author = this.getUser().getUsername();
        this.createdAt = LocalDate.now();
        this.commentStatus = CommentStatus.VISIBLE;
        this.avatarUser = this.getUser().getAvatar();
    }
}