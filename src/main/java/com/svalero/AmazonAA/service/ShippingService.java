package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Shipping;
import com.svalero.AmazonAA.domain.dto.ShippingDTO;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.ShippingNotFoundException;
import com.svalero.AmazonAA.exception.ShippingWithOrderAlreadyExist;

import java.util.List;

public interface ShippingService {
    List<Shipping> findAll();
    Shipping findById(long id) throws ShippingNotFoundException;
    Shipping findByOrder(Order order) throws ShippingNotFoundException;
    List<Shipping> findByCarrierContainingIgnoreCase(String carrrier);
    Shipping addShipping(ShippingDTO shippingDTO) throws OrderNotFoundException, ShippingWithOrderAlreadyExist;
    Shipping modifyShipping(long id, ShippingDTO shippingDTO) throws ShippingNotFoundException, OrderNotFoundException;
    void deleteShipping(long id) throws ShippingNotFoundException;
}
