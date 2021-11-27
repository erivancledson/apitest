package com.erivan.apitest.services.impl;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.repositories.UserRepository;
import com.erivan.apitest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElse(null); //se não encontrar retorna nulo
    }
}
