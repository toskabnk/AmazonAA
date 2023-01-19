package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Shipping;
import com.svalero.AmazonAA.domain.dto.ShippingDTO;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ShippingNotFoundException;
import com.svalero.AmazonAA.exception.ShippingWithOrderAlreadyExist;
import com.svalero.AmazonAA.repository.OrderRepository;
import com.svalero.AmazonAA.repository.PersonRepository;
import com.svalero.AmazonAA.repository.ShippingRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService{

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ShippingServiceImpl.class);

    @Override
    public List<Shipping> findAll() {
        return shippingRepository.findAll();
    }

    @Override
    public Shipping findById(long id) throws ShippingNotFoundException {
        logger.info("ID Shipping: " + id);
        return shippingRepository.findById(id).orElseThrow(ShippingNotFoundException::new);
    }

    @Override
    public Shipping findByOrder(Order order) throws ShippingNotFoundException {
        logger.info("Order Shipping: " + order);
        Shipping shipping = shippingRepository.findByOrder(order);
        if(shipping == null){
            throw new ShippingNotFoundException();
        }

        return shippingRepository.findByOrder(order);
    }

    @Override
    public List<Shipping> findByCarrierContainingIgnoreCase(String carrrier) {
        logger.info("String Carrier Shipping: " + carrrier);
        return shippingRepository.findByCarrierContainingIgnoreCase(carrrier);
    }

    @Override
    public Shipping addShipping(ShippingDTO shippingDTO) throws OrderNotFoundException, ShippingWithOrderAlreadyExist {
        logger.info("Shipping added: " + shippingDTO);
        Order order = orderRepository.findById(shippingDTO.getOrderId()).orElseThrow(OrderNotFoundException::new);
        if(shippingRepository.findByOrder(order) == null){
            Shipping shipping = new Shipping();
            modelMapper.map(shippingDTO, shipping);
            shipping.setOrder(order);
            return shippingRepository.save(shipping);
        } else {
            throw new ShippingWithOrderAlreadyExist();
        }

    }

    @Override
    public Shipping modifyShipping(long id, ShippingDTO shippingDTO) throws ShippingNotFoundException {
        Shipping existingShipping = shippingRepository.findById(id).orElseThrow(ShippingNotFoundException::new);
        logger.info("Shipping1: " + existingShipping);
        Order order = existingShipping.getOrder();
        long idOrder = order.getId();
        logger.info("Order1: " + order);
        //ModelMapper tiene que hacer algo guarro aqui, que el id de Order lo pone a 0
        modelMapper.map(shippingDTO, existingShipping);
        existingShipping.setId(id);
        order.setId(idOrder);
        existingShipping.setOrder(order);
        logger.info("Shipping2: " + existingShipping);
        logger.info("Order2: " + order);
        return shippingRepository.save(existingShipping);
    }

    @Override
    public void deleteShipping(long id) throws ShippingNotFoundException {
        Shipping existingShipping = shippingRepository.findById(id).orElseThrow(ShippingNotFoundException::new);
        shippingRepository.delete(existingShipping);
    }

    @Override
    public List<Shipping> findDeliveredByUser(long userId) throws PersonNotFoundException {
        Person person = personRepository.findById(userId).orElseThrow(PersonNotFoundException::new);
        List<Shipping> shippings = shippingRepository.findDeliveredByUser(person.getId());
        return shippings;
    }
}
