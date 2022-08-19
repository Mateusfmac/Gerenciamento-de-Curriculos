package com.dbc.curriculo.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoCreateDTO {

    @Pattern(regexp = "\\d{8}", message = "o cep deve ser somente números e conter 8 dígitos.")
    @NotBlank
    private String cep;
    @NotNull
    private Integer numero;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    @Size(min = 2, max = 2)
    private String estado;

}
