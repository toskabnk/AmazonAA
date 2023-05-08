package com.svalero.AmazonAA.exception;

public class FileNotImageException extends Exception{
    public FileNotImageException(){ super("El archivo debe ser una imagen");}
}
