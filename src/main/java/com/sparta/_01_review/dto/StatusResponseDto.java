package com.sparta._01_review.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

@Getter
public class StatusResponseDto {
    private String msg;
    private Integer statusCode;

    public StatusResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
