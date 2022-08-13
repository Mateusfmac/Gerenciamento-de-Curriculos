package com.dbc.curriculo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    
    @Schema(description = "id do usuario")
    private Integer idLogin;
    
    @Schema(description = "email para login do usuario")
    private Integer email;
}
