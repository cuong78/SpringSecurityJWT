package com.example.demo.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;



    public List<Product> getAllProduct() {
        return productRepo.findProductsByIsDeletedFalse();
    }

    public Product create (Product product) {
        return  productRepo.save(product);
    }

    public Product delete (long id) {
       Product product = productRepo.findProductById(id);
        product.isDeleted = true;
        return productRepo.save(product);
    }




}
