package com.erivan.apitest.services;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);
    User update(UserDTO obj);
}
