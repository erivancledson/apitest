package com.erivan.apitest.services;

import com.erivan.apitest.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
}
