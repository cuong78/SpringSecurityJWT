package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotBlank
    public String name;
    @Min(value = 0)
   public  float price;
    @Min(value = 0)
  public   int quantity;
    @NotBlank
   public  String image;

    //PD001
    @Pattern(regexp = "PD\\d{3}", message = "code must be PDxxx")
    @Column(unique = true)
    public String code;

    public boolean isDeleted = false;



}
