package com.sparta._01_review.dto;

import com.sparta._01_review.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long postId;
    private String username;
    private Long commentId;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
