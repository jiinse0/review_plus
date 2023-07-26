package com.sparta._01_review.controller;

import com.sparta._01_review.dto.PostRequestDto;
import com.sparta._01_review.dto.PostResponseDto;
import com.sparta._01_review.dto.StatusResponseDto;
import com.sparta._01_review.entity.Post;
import com.sparta._01_review.security.UserDetailsImpl;
import com.sparta._01_review.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Post post = postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new PostResponseDto(post));
    }

    @GetMapping("/post")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getOnePost(@PathVariable Long id) {

        try {
            PostResponseDto post = postService.getOnePost(id);
            return ResponseEntity.ok().body(post);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            PostResponseDto post = postService.updatePost(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(post);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("작성한 게시물만 수정 가능합니다.");
        }
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<StatusResponseDto> deletePost(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new StatusResponseDto("게시글이 삭제되었습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

}
