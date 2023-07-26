package com.sparta._01_review.controller;

import com.sparta._01_review.dto.AuthRequestDto;
import com.sparta._01_review.dto.StatusResponseDto;
import com.sparta._01_review.jwt.JwtUtil;
import com.sparta._01_review.security.UserDetailsImpl;
import com.sparta._01_review.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<StatusResponseDto> signup(@RequestBody @Valid AuthRequestDto requestDto) {

        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto("중복된 회원이 존재합니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new StatusResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<StatusResponseDto> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {

        try {
            userService.login(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(("아이디 또는 패스워드를 확인해주세요."), HttpStatus.BAD_REQUEST.value()));
        }

        jwtUtil.addJwtToCookie(jwtUtil.createToken(requestDto.getUsername()), response);

        return ResponseEntity.ok().body(new StatusResponseDto(("로그인 성공"), HttpStatus.OK.value()));
    }
}
