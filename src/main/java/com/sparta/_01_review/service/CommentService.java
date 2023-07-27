package com.sparta._01_review.service;

import com.sparta._01_review.dto.CommentRequestDto;
import com.sparta._01_review.dto.CommentResponseDto;
import com.sparta._01_review.entity.Comment;
import com.sparta._01_review.entity.Post;
import com.sparta._01_review.entity.User;
import com.sparta._01_review.repository.CommentRepository;
import com.sparta._01_review.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {

        Post post = postService.findPost(postId);

        Comment comment = new Comment(requestDto, user, post);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(commentId);;
        checkUser(comment, user);

        comment.updateComment(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);;
        checkUser(comment, user);

        commentRepository.delete(comment);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
    }

    private void checkUser(Comment comment, User user) {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("댓글 작성자만 수정 및 삭제가 가능합니다.");
        }
    }
}
