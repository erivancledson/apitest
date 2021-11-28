package com.erivan.apitest.resources;

import com.erivan.apitest.domain.User;
import com.erivan.apitest.domain.dto.UserDTO;
import com.erivan.apitest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    public static final String ID = "/{id}";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService service;

    @GetMapping(value = ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class)); //pego o id que usuário informou e retorno o DTO
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        //o x é o User que vai ser transformado em DTO
        List<UserDTO> listDTO = service.findAll().stream().map(x-> mapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj)
                .getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj){
        obj.setId(id);
        User newObj = service.update(obj);
        return ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
