package com.esun.hw.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface PostService {

    public ResponseEntity<?> createPost(UUID reqUserId, String content);

}
