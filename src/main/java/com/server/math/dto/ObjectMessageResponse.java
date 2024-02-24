package com.server.math.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ObjectMessageResponse<T> {
    private String message;
    private T object;
}