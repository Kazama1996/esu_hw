package com.esun.hw.dao;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.esun.hw.dto.response.PostResponseDto;
import com.esun.hw.model.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Procedure("create_post")
    public void createPost(@Param("p_user_id") UUID userId, @Param("p_post_content") String postContent);

    @Procedure("update_post")
    public void updatePost(@Param("p_post_id") UUID postId, @Param("p_post_content") String postContent,
            @Param("p_created_at") Instant currentTime);

    @Procedure("delete_post")
    public void deletePost(@Param("p_post_id") UUID postId, @Param("p_user_id") UUID reqUserId);

    @Query(value = "SELECT * FROM read_post(:p_user_id)", nativeQuery = true)
    public List<Object[]> readPost(@Param("p_user_id") UUID userId);
}
