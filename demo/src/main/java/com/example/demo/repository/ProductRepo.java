package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// JpaRepository ( định nghĩa tên entity , và kiểu ữ liệu khóa chính )
public interface ProductRepo extends JpaRepository <Product,Long> {

    Product findProductById(long id);

    List<Product> findProductsByIsDeletedFalse();

}
