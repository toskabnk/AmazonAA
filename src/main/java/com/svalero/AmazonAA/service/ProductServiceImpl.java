package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.FileNotImageException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.repository.OrderRepository;
import com.svalero.AmazonAA.repository.ProductRepository;
import com.svalero.AmazonAA.repository.ReviewRepository;
import com.svalero.AmazonAA.repository.StockRepository;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Tika tika;

    @Autowired
    ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final Path root = Paths.get("productImages");
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletRequest servletRequest;

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
    public Product saveImage(long id, MultipartFile multipartFile) throws ProductNotFoundException, IOException, FileNotImageException {
        Path path;
        try {
            String mimeType = tika.detect(multipartFile.getInputStream());
            if (!mimeType.startsWith("image/")) {
                throw new FileNotImageException();
            }
            path = Paths.get(root.toAbsolutePath().toString(), multipartFile.getOriginalFilename());
            Files.write(path, multipartFile.getBytes());
        } catch (FileNotImageException e){
            throw new FileNotImageException();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        Product existingProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        String contextPath = request.getContextPath();
        String productURL = servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort() + contextPath + "/" + root.toFile().getPath() + "/" + multipartFile.getOriginalFilename();
        existingProduct.setImageURL(productURL);
        logger.info("Product path: " + productURL);
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public List<Product> findByCategory(String category) {
        logger.info("Category Product: " + category);
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByDescription(String description) {
        logger.info("Description Product: " + description);
        return productRepository.findByDescriptionContainingIgnoreCase(description);
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

    @Override
    public List<Product> findSoldOut() {
        return productRepository.findSoldOut();
    }
}
