package com.example.demo.api;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// đánh dấu
@RestController
@RequestMapping("/api/product")
public class ProductAPI {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity getAllProduct() {
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(products);

    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody  Product product) {
        Product newProduct = productService.create(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        Product product = productService.delete(id);
        return ResponseEntity.ok(product);
    }



}
