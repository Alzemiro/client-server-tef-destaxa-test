package com.destaxa.autorize.server.infraestructure.config;

import org.jpos.iso.packager.GenericPackager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class Iso8583Config {

    @Bean
    public GenericPackager genericPackager() throws Exception{
        InputStream resourceAsStream = getClass().getResourceAsStream("/basic.xml");

        return new GenericPackager(resourceAsStream);
    }

}
