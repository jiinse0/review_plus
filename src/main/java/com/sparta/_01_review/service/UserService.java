package com.sparta._01_review.service;

import com.sparta._01_review.dto.AuthRequestDto;
import com.sparta._01_review.entity.User;
import com.sparta._01_review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(AuthRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String checkPassword = requestDto.getCheckPassword();

        validateSignup(username, password, checkPassword);

        String encodePassword = passwordEncoder.encode(password);

        User user = new User(username, encodePassword);
        userRepository.save(user);
    }

    public void login(AuthRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하는 회원이 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원가입 유효성 검사
     * @param username 아이디
     * @param password 비밀번호
     * @param checkPassword 확인 비밀번호
     */
    private void validateSignup(String username, String password, String checkPassword) {

        // 중복된 회원
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 회원이 존재합니다.");
        }

        // 비밀번호 확인
        if (!password.equals(checkPassword)) {
            throw new IllegalArgumentException("비밀번호와 확인 비밀번호가 다릅니다. 다시 입력해주세요");
        }

        // 닉네임과 같은 값이 포함된 경우 회원가입에 실패
        if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 아이디가 포함되어있습니다. 다른 비밀번호를 사용해 주세요");
        }
    }
}
