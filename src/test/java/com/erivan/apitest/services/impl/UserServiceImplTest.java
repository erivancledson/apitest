package com.erivan.apitest.services.impl;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;
import com.erivan.apitest.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Erivan";
    public static final String EMAIL = "erivancled@hotmail.com";
    public static final String PASSWORD = "123";

    @InjectMocks //cria os mocks de mentira
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    //antes de tudo realize o texto de codigo passado aqui dentro
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //agora tenhos os mocks
        startUser();
    }

    @Test
    void whenfindByIdThenReturnUserInstance() {
        //quando findById for chamado retorne um optinalUser para testar
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);
        //verifica se está nulo
        assertNotNull(response);
        //tem que retornar um objeto que a sua classe vai ser do tipo user
        //assertEquals a segure para me que ambos são iguais
        //primeiro argumento é o que vou receber
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId()); //compara se é por id
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }


    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}