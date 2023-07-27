package com.sparta._01_review.controller;

import com.sparta._01_review.dto.CommentRequestDto;
import com.sparta._01_review.dto.CommentResponseDto;
import com.sparta._01_review.dto.StatusResponseDto;
import com.sparta._01_review.security.UserDetailsImpl;
import com.sparta._01_review.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok().body(commentService.createComment(postId, requestDto, userDetails.getUser()));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto comment = commentService.updateComment(commentId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(comment);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
        }
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<StatusResponseDto> deleteComment(@PathVariable Long commentId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new StatusResponseDto("삭제 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
