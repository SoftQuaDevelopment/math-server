package com.server.math.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectMessageResponse<T> {
    private String message;
    private T object;
}