package com.svalero.AmazonAA;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonAACConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
