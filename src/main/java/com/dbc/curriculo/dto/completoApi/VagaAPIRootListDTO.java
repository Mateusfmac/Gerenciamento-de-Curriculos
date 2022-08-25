// FIXME pacote deve ser todo minusculo
package com.dbc.curriculo.dto.completoApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class VagaAPIRootListDTO {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("Titulo")
    public String titulo;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("DataAbertura")
    public Date dataAbertura;
    @JsonProperty("Categoria")
    public Object categoria;
    @JsonProperty("Cidade")
    public String cidade;
    @JsonProperty("Estado")
    public String estado;

    // FIXME deve ser lista
    @JsonProperty("Tags")
    public ArrayList<Object> tags;
}
