package com.miweb.blog.controller;

import com.miweb.blog.model.Post;
import com.miweb.blog.service.PostService; // Asumimos que creaste el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el Frontend
public class PostController {

    @Autowired
    private PostService postService;

    // GET: Obtener todos los posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    // POST: Crear un nuevo post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }

    // Aquí agregarías endpoints para buscar por ID, borrar, etc.

}