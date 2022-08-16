package com.dbc.curriculo.dto.endereco;

import lombok.Data;

@Data
public class EnderecoCreateDTO {
    
    private Integer numero;
    
    private String logradouro;
    
    private String bairro;
    
    private String cidade;
}
