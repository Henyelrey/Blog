package com.miweb.blog.service;

import com.miweb.blog.model.Post;
import com.miweb.blog.model.User;
import com.miweb.blog.repository.PostRepository;
import com.miweb.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // <--- Esta anotación es VITAL. Le dice a Spring "Soy un servicio, úsame".
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    // Método para obtener todos los posts
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // Método para guardar un post
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // Método para buscar por ID (lo necesitarás para agregar comentarios)
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // Guardar post ASIGNANDO el autor
    public Post save(Post post, String username) {
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        post.setUsuario(usuario);
        return postRepository.save(post);
    }

    // --- NUEVO: Lógica para Borrar ---
    public void deletePost(Long postId, String currentUsername, boolean isAdmin) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        // Permitir borrar si: Es ADMIN o Es el DUEÑO del post
        if (isAdmin || post.getUsuario().getUsername().equals(currentUsername)) {
            postRepository.deleteById(postId);
        } else {
            throw new RuntimeException("No tienes permiso para eliminar este post");
        }
    }

    // --- NUEVO: Lógica para Editar ---
    public Post updatePost(Long id, Post postActualizado, String currentUsername) {
        Post postExistente = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        // Solo el DUEÑO puede editar
        if (!postExistente.getUsuario().getUsername().equals(currentUsername)) {
            throw new RuntimeException("No tienes permiso para editar este post");
        }

        postExistente.setTitulo(postActualizado.getTitulo());
        postExistente.setContenido(postActualizado.getContenido());
        postExistente.setImagenUrl(postActualizado.getImagenUrl());

        return postRepository.save(postExistente);
    }

}