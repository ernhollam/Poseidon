package com.nnk.springboot.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoseidonConfig {
    /**
     * Bean to convert an entity to view model object or vice versa.
     * @return Model mapper object.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
