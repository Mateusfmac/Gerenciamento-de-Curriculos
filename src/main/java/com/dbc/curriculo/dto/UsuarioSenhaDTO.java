package com.dbc.curriculo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSenhaDTO {
    
    @NotBlank
    private String senha;
}
