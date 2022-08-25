package com.dbc.curriculo.dto.completoApi;
// FIXME package minusculo
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

// FIXME todos os atributos devem ser private
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
    // FIXME list
    public ArrayList<String> categorias;

    @JsonProperty("Cidade")
    public String cidade;
    @JsonProperty("Estado")
    public String estado;
    // FIXME list
    public ArrayList<String> tags;
}