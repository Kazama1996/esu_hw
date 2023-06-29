package com.esun.hw.service.Ipml;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.esun.hw.dao.PostRepository;
import com.esun.hw.model.Post;
import com.esun.hw.service.PostService;

@Service
public class PostServiceIpml implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceIpml(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<?> createPost(UUID reqUserId, String content) {

        System.out.println(content);
        postRepository.createPost(reqUserId, content);
        return new ResponseEntity<>("you create a post", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updatePost(UUID reqUserId, UUID postId, String content) {

        Post targetPost = postRepository.findById(postId).get();

        if (!targetPost.getUserId().toString().equals(reqUserId.toString())) {
            return new ResponseEntity<>("This post is not belong to you", HttpStatus.BAD_REQUEST);
        }

        postRepository.updatePost(postId, content, Instant.now());
        return new ResponseEntity<>("you update a post", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deletePost(UUID reqUserId, UUID postId) {

        Post targetPost = postRepository.findById(postId).get();

        if (!targetPost.getUserId().toString().equals(reqUserId.toString())) {
            return new ResponseEntity<>("This post is not belong to you", HttpStatus.BAD_REQUEST);
        }

        postRepository.deletePost(postId, reqUserId);

        return new ResponseEntity<>("you delete a post", HttpStatus.OK);
    }

}
