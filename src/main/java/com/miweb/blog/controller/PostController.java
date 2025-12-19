package com.miweb.blog.controller;

import com.miweb.blog.model.Post;
import com.miweb.blog.service.PostService; // Asumimos que creaste el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        return (post != null) ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    // POST: Crear un nuevo post
// Crear Post (Asigna el usuario logueado)
    @PostMapping
    public Post createPost(@RequestBody Post post, Authentication authentication) {
        return postService.save(post, authentication.getName());
    }


    // --- NUEVO: Editar Post ---
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post, Authentication authentication) {
        try {
            Post actualizado = postService.updatePost(id, post, authentication.getName());
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // --- NUEVO: Eliminar Post ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication authentication) {
        try {
            // Verificar si es ADMIN
            boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

            postService.deletePost(id, authentication.getName(), isAdmin);
            return ResponseEntity.ok("Post eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}