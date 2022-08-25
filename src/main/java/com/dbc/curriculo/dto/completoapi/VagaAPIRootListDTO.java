package com.dbc.curriculo.dto.completoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VagaAPIRootListDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("Titulo")
    private String titulo;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("DataAbertura")
    private Date dataAbertura;
    @JsonProperty("Categoria")
    private Object categoria;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Estado")
    private String estado;

    @JsonProperty("Tags")
    public List<Object> tags;
}
