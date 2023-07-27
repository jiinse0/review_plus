package com.sparta._01_review.service;

import com.sparta._01_review.dto.PostRequestDto;
import com.sparta._01_review.dto.PostResponseDto;
import com.sparta._01_review.entity.Post;
import com.sparta._01_review.entity.User;
import com.sparta._01_review.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostRequestDto requestDto, User user) {

        Post post = new Post(requestDto, user);
        return postRepository.save(post);
    }

    public List<PostResponseDto> getPost() {
        return postRepository.findAllByOrderByCreateAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public PostResponseDto getOnePost(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);
 checkUser(user, post);

        post.updatePost(requestDto);
        return new PostResponseDto(post);
    }

    public void deletePost(Long id, User user) {
        Post post = findPost(id);
        checkUser(user, post);

        postRepository.delete(post);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
    }

    public void checkUser(User user, Post post) {
        if (!user.getUsername().equals(post.getUsername())) {
            throw new IllegalArgumentException("본인 게시글만 수정, 삭제 가능합니다.");
        }
    }
}
