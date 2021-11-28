package com.erivan.apitest.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    //para escrita é liberado o password e ele ignora quando for somente leitura
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
