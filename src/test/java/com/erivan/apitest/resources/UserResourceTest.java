package com.erivan.apitest.resources;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;
import com.erivan.apitest.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Erivan";
    public static final String EMAIL = "erivancled@hotmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    private User user = new User();
    private UserDTO userDTO = new UserDTO();

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        //inicializa os mocks da classe
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        //faz a chamada do mapper
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response); //não pode ser nulo
        assertNotNull(response.getBody()); // o corpo da resposta não pode ser nulo
        //assegurar que a classe ResponseEntity é a mesma do response
        assertEquals(ResponseEntity.class, response.getClass());
        // eu quero que UserDTO seja a mesma classe que vem do corpo do response
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId()); //assegurar que o id estatico é o mesmo do meu response
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        //retorna para me uma lista de um usuario
        when(service.findAll()).thenReturn(List.of(user));
        //mapeia todos os objetos e transforma em dto
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response); //assegurar que o reponse não vai ser nulo
        assertNotNull(response.getBody()); //assegurar que o corpo não vai ser nulo
        assertEquals(HttpStatus.OK, response.getStatusCode());  //o status do reponse tem que ser ok
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass()); //assegurar que vai vir um arraylist
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        //mockando e retorna o novo usuario
        when(service.create(any())).thenReturn(user);
        //instanciando
        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); //verifica se é o mesmo status
        assertNotNull(response.getHeaders().get("Location")); //verifica se response.getHeaders tem a chave Location
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(userDTO)).thenReturn(user);
        //quando mapear a entidade para dto, retorna ele como userDTO
        when(mapper.map(any(), any())).thenReturn(userDTO);
        //passa o id e o user dto
        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response); //assegurar que o response não vai ser nullo
        assertNotNull(response.getBody()); //assegurar que o corpo da resposta não vai chegar nula
        assertEquals(HttpStatus.OK, response.getStatusCode()); //que o status vai ser 200
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass()); //UserDTO é o corpo do meu dto

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        //doNothing não faça nada quando meu service.delete passar um valor inteiro como parametro
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response); //assegurar que o response não está vazio
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); //assegurar que vai ser NO_CONTENT
        //so pode ser chamado uma vez no service no metodo delete
        verify(service, times(1)).delete(anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL,  PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}