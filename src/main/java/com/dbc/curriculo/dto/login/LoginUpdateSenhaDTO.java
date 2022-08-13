package com.dbc.curriculo.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LoginUpdateSenhaDTO extends LoginCredenciaisDTO{

    @JsonIgnore
    private String email;

}
