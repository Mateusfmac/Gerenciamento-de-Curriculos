package com.dbc.curriculo.dto.completoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VagaApiListDTO {

    @Schema(description = "Id da vaga")
    @JsonProperty("id")
    private Integer id;

    @Schema(description = "Título da vaga")
    @JsonProperty("Titulo")
    private String titulo;

    @Schema(description = "Status atual da vaga")
    @JsonProperty("Status")
    private String status;

    @Schema(description = "Data de abertura da vaga.")
    @JsonProperty("DataAbertura")
    private Date dataAbertura;

    @Schema(description = "Categorias da vaga.")
    private List<String> categorias;

    @Schema(description = "Cidade onde a vaga foi ofertada.")
    @JsonProperty("Cidade")
    private String cidade;

    @Schema(description = "Estado onde a vaga foi ofertada.")
    @JsonProperty("Estado")
    private String estado;

    @Schema(description = "Tags da vaga.")
    private List<String> tags;
}