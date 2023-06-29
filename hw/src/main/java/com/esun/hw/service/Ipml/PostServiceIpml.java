package com.esun.hw.service.Ipml;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.esun.hw.dao.PostRepository;
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

}
