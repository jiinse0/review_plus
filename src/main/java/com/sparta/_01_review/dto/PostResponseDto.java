package com.sparta._01_review.dto;

import com.sparta._01_review.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String username;
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.username = post.getUser().getUsername();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
