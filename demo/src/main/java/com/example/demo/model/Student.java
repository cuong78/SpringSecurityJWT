package com.example.demo.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class Student {
    @NotEmpty(message = "Id can not blank!")
    public String id;

    @NotBlank(message = "Name can not blank!")
    public String name;

    @Pattern(regexp = "SE\\d{6}", message = "Student code not match struct!")
    public String studentCode;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 10, message = "Score must be at most 10")
    public float score;
}
