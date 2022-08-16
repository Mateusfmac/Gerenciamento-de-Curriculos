package com.dbc.curriculo.dto.vaga;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class VagaApiListDTO {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("Titulo")
    public String titulo;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("DataAbertura")
    public Date dataAbertura;

    public ArrayList<String> categorias;

    @JsonProperty("Cidade")
    public String cidade;
    @JsonProperty("Estado")
    public String estado;

    public ArrayList<String> tags;
}