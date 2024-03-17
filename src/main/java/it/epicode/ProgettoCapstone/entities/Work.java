package it.epicode.ProgettoCapstone.entities;

import it.epicode.ProgettoCapstone.enums.WorksStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "works")
@Getter
@Setter
@NoArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String image;
    private String description;
    private LocalDate dateCreated;
    private LocalDate dateUploaded;

    @Enumerated(EnumType.STRING)
    private WorksStatus worksStatus;
    private boolean featured;

    @OneToMany(mappedBy = "work", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Work(String name, String image, String description, LocalDate dateCreated, WorksStatus worksStatus) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateUploaded = LocalDate.now();
        this.worksStatus = worksStatus;
    }
}

