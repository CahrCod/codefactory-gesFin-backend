package com.pruebareservas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private Boolean success;
}
