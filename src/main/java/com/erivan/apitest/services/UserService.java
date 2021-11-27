package com.erivan.apitest.services;

import com.erivan.apitest.domain.User;

public interface UserService {

    User findById(Integer id);
}
