package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }


    @Override
    public Product addProduct(ProductDTO productDTO) {
        Product newProduct = new Product();

        modelMapper.map(productDTO, newProduct);
        newProduct.setReviews(new ArrayList<>());
        newProduct.setInventories(new ArrayList<>());

        return productRepository.save(newProduct);
    }

    @Override
    public void deteleProduct(long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

    @Override
    public Product modifyProduct(long id, Product newProduct) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<Review> reviews = existingProduct.getReviews();
        List<Stock> inventories = existingProduct.getInventories();
        modelMapper.map(newProduct,existingProduct);
        existingProduct.setId(id);
        existingProduct.setInventories(inventories);
        existingProduct.setReviews(reviews);
        return productRepository.save(existingProduct);
    }
}
