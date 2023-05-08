package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.FileNotImageException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();
    Product findById(long id) throws ProductNotFoundException;

    List<Product> findByNameContaining(String name);
    Product saveImage(long id, MultipartFile multipartFile) throws ProductNotFoundException, IOException, FileNotImageException;

    List<Product> findByCategory(String category);
    List<Product> findByDescription(String description);
    Product addProduct(ProductDTO productDTO);
    boolean deteleProduct(long id) throws ProductNotFoundException;
    Product modifyProduct(long id, Product newProduct) throws ProductNotFoundException;
    List<Product> findSoldOut();
}
