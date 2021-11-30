package com.erivan.apitest.services.impl;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;
import com.erivan.apitest.repositories.UserRepository;
import com.erivan.apitest.services.exceptions.DataIntegratyViolationException;
import com.erivan.apitest.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Erivan";
    public static final String EMAIL = "erivancled@hotmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";


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

    //excessão para objeto não encontrado
    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.findById(ID);

        }catch (Exception ex){
            //verifica se ObjectNotFoundException é a mesma classe do ex
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            //verifica se é a mensagem que está vindo é a mesma que foi informada
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<User> response = service.findAll();

        assertNotNull(response); //não pode ser nulo
        assertEquals(1, response.size()); //tem que vir somente um usuário
        assertEquals(User.class, response.get(INDEX).getClass()); //objeto que vem nessa lista seja do tipo User
        assertEquals(ID, response.get(INDEX).getId()); //o primeiro objeto que vem nessa lista é o id
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        //qualquer user que ele receber retorna o user
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass()); //tem que ser uma classe do tipo user
        assertEquals(ID, response.getId()); //o id do parametro vai ser o mesmo id que foi passado por estatico
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            //id 2
            optionalUser.get().setId(1);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass()); //verifica se o tipo da exeção é a informada
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage()); //verifica se é a mensagem esperada é essa
        }
    }


    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

         assertNotNull(response);
         assertEquals(User.class, response.getClass());
         assertEquals(ID, response.getId());
         assertEquals(NAME, response.getName());
         assertEquals(EMAIL, response.getEmail());
         assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(1);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }


    @Test
    void deleteWithSuccess() {
        //retorna o optionalUser
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        //doNothing não faça nada quando o repository for chamado no metodo repositorio byId
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID); //metodo de deletar
        //verifica quantas vezes o metodo foi chamado do deleteById o esperado é 1
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        //anyInt = quando o repositorio receber qualquer valor inteiro
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}