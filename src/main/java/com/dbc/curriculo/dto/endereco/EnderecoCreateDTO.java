package com.dbc.curriculo.dto.endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EnderecoCreateDTO {


    private Integer numero;

    private String logradouro;

    private String bairro;

    private String cidade;

}
