package com.miweb.blog.service;

import com.miweb.blog.model.Post;
import com.miweb.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // <--- Esta anotación es VITAL. Le dice a Spring "Soy un servicio, úsame".
public class PostService {

    @Autowired
    private PostRepository postRepository;

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
}