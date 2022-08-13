package com.dbc.curriculo.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDTO {
    
    @Schema(description = "id do usuario")
    private Integer idLogin;
    
    @Schema(description = "email para login do usuario")
    private String email;
}
