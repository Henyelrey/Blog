package com.miweb.blog.controller;

import com.miweb.blog.model.Comment;
import com.miweb.blog.model.Post;
import com.miweb.blog.service.CommentService;
import com.miweb.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    // POST: Crear un comentario asociado a un Post específico
    // URL ejemplo: /api/comments/post/1
    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        // 1. Buscamos el Post por su ID
        Post post = postService.findById(postId);

        if (post == null) {
            // Si el post no existe, devolvemos error 404
            return ResponseEntity.notFound().build();
        }

        // 2. Asociamos el comentario al post encontrado
        comment.setPost(post);

        // 3. Guardamos el comentario
        Comment nuevoComentario = commentService.save(comment);
        return ResponseEntity.ok(nuevoComentario);
    }

    // GET: Ver todos los comentarios (opcional, para depuración)
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }
}