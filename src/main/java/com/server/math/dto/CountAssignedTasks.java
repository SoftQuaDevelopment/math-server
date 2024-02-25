package com.server.math.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountAssignedTasks<T> {
    int count;
    T object;

}
