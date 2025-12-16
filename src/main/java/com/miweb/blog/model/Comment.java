package com.miweb.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private String autor;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación: Muchos comentarios pertenecen a un Post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // Nombre de la columna en MySQL
    @JsonIgnore // Importante para evitar bucles infinitos al convertir a JSON
    private Post post;
}