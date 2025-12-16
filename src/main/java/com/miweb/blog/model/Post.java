package com.miweb.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String imagenUrl;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación: Un Post tiene muchos comentarios
    // mappedBy apunta al atributo "post" en la clase Comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}