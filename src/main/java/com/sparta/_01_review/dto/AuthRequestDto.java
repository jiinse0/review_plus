package com.sparta._01_review.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthRequestDto {
    @NotNull
    @Size(min = 3, message = "아이디는 최소 3자 이상이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 알파벳 대소문자, 숫자로 구성되어야 합니다.")
    private String username;

    @NotNull
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이여야 합니다.")
    private String password;

    private String checkPassword;
}
