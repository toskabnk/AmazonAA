package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.repository.OrderRepository;
import com.svalero.AmazonAA.repository.ProductRepository;
import com.svalero.AmazonAA.repository.ReviewRepository;
import com.svalero.AmazonAA.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) throws ProductNotFoundException {
        logger.info("ID Product: " + id);
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        logger.info("Name Product: " + name);
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> findByCategory(String category) {
        logger.info("Category Product: " + category);
        return productRepository.findByCategory(category);
    }


    @Override
    public Product addProduct(ProductDTO productDTO) {
        logger.info("Added Product: " + productDTO);
        Product newProduct = new Product();

        modelMapper.map(productDTO, newProduct);
        newProduct.setReviews(new ArrayList<>());
        newProduct.setInventories(new ArrayList<>());

        return productRepository.save(newProduct);
    }

    @Override
    public boolean deteleProduct(long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<Order> order = orderRepository.findByProduct_Id(product.getId());
        List<Review> review = reviewRepository.findByProductReview(product);
        List<Stock> stock = stockRepository.findByProductStock_Id(product.getId());
        if(!order.isEmpty() || !review.isEmpty() || !stock.isEmpty()){
            logger.error("Product " + product + " no se puede borrar ya que referencia a otras tablas en la base de datos, elimina esas referencias antes.");
            return false;
        }
        logger.info("Delete Product: " + product);
        productRepository.delete(product);
        return true;
    }

    @Override
    public Product modifyProduct(long id, Product newProduct) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        logger.info("Existing Product: " + existingProduct);
        logger.info("New Product: " + newProduct);
        List<Review> reviews = existingProduct.getReviews();
        List<Stock> inventories = existingProduct.getInventories();
        modelMapper.map(newProduct,existingProduct);
        existingProduct.setId(id);
        existingProduct.setInventories(inventories);
        existingProduct.setReviews(reviews);
        return productRepository.save(existingProduct);
    }
}
