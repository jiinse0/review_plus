package com.sparta._01_review.entity;

import com.sparta._01_review.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.post = post;
    }

    public void updateComment(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
