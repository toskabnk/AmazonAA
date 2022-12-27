package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.dto.OrderDTO;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.repository.OrderRepository;
import com.svalero.AmazonAA.repository.PersonRepository;
import com.svalero.AmazonAA.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) throws OrderNotFoundException {
        logger.info("ID Order: " + id);
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> findByPersonUsername(String username) throws PersonNotFoundException{
        logger.info("Username Order: " + username);
        Person person = personRepository.findByUsername(username);
        if(person == null){
            throw new PersonNotFoundException();
        }
        return orderRepository.findByCustomer_Id(person.getId());
    }

    @Override
    public List<Order> findByProductId(long id) throws ProductNotFoundException{
        logger.info("ID Product Order: " + id);
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return orderRepository.findByProduct_Id(product.getId());
    }

    @Override
    public List<Order> findByPaid(boolean paid){
        List<Order> orders = orderRepository.findByPaidEquals(paid);
        return orders;
    }

    @Override
    public Order addOrder(OrderDTO orderDTO) throws PersonNotFoundException, ProductNotFoundException {
        logger.info("Order added: " + orderDTO);
        Order newOrder = new Order();
        Product product;
        Person person;

        product = productRepository.findById(orderDTO.getProductId()).orElseThrow(ProductNotFoundException::new);
        person = personRepository.findById(orderDTO.getCustomerId()).orElseThrow(PersonNotFoundException::new);

        newOrder.setPaid(orderDTO.isPaid());
        newOrder.setQuantity(orderDTO.getQuantity());
        newOrder.setCustomer(person);
        newOrder.setProduct(product);

        float totalPrice = orderDTO.getQuantity() * product.getPrice();

        newOrder.setTotalPrice(totalPrice);

        return orderRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        logger.info("Order deleted: " + order);
        orderRepository.delete(order);
    }

    @Override
    public Order modifyOrder(long id, OrderDTO orderDTO) throws OrderNotFoundException, ProductNotFoundException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        logger.info("Existing Order: " + existingOrder);
        logger.info("OrderDTO Order: " + orderDTO);
        Product product = productRepository.findById(orderDTO.getProductId()).orElseThrow(ProductNotFoundException::new);

        existingOrder.setQuantity(orderDTO.getQuantity());
        existingOrder.setPaid(orderDTO.isPaid());
        existingOrder.setProduct(product);
        existingOrder.setTotalPrice(existingOrder.getQuantity()* product.getPrice());

        return orderRepository.save(existingOrder);
    }
}
