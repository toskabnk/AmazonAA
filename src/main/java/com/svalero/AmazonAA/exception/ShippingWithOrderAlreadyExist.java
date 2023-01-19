package com.svalero.AmazonAA.exception;

public class ShippingWithOrderAlreadyExist extends Exception{
    public ShippingWithOrderAlreadyExist(){ super("Envio con el pedido ya existe");}
}
