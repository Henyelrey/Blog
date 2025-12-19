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

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String imagenUrl;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación: Un Post tiene muchos comentarios
    // mappedBy apunta al atributo "post" en la clase Comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // --- Relación con User ---
    @ManyToOne(fetch = FetchType.EAGER) // Traer el usuario cuando consultes el post
    @JoinColumn(name = "user_id")
    private User usuario;
}