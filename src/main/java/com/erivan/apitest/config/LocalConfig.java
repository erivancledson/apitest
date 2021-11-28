package com.erivan.apitest.config;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startBD(){

        User u1 = new User(null, "Erivan Cledson", "erivancled@hotmail.com", "123" );
        User u2 = new User(null, "Eric Felipe", "eric@hotmail.com", "123" );

        repository.saveAll(List.of(u1, u2)); //salvar os dois
    }

}
