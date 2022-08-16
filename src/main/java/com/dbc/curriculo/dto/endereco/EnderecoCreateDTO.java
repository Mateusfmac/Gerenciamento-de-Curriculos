package com.dbc.curriculo.dto.endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EnderecoCreateDTO {

    @NotNull
    private Integer numero;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;

}
