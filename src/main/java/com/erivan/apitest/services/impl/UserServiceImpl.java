package com.erivan.apitest.services.impl;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;
import com.erivan.apitest.repositories.UserRepository;
import com.erivan.apitest.services.UserService;
import com.erivan.apitest.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        //converter de dto para entidade
        return repository.save(modelMapper.map(obj, User.class));
    }
}
