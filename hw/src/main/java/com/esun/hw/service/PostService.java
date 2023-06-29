package com.esun.hw.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface PostService {

    public ResponseEntity<?> createPost(UUID reqUserId, String content);

    public ResponseEntity<?> updatePost(UUID reqUserId, UUID postId, String content);

    public ResponseEntity<?> deletePost(UUID reqUserId, UUID postId);

    public ResponseEntity<?> readPost(UUID userId);

}
