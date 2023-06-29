package com.esun.hw.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esun.hw.dto.request.PostContentDto;
import com.esun.hw.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestAttribute("reqUserId") UUID reqUserId,
            @RequestBody PostContentDto postRequestDto) {

        return postService.createPost(reqUserId, postRequestDto.getContent());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestAttribute("reqUserId") UUID reqUserId,
            @PathVariable("postId") UUID postId,
            @RequestBody PostContentDto postContentDto) {
        return postService.updatePost(reqUserId, postId, postContentDto.getContent());
    }

}
