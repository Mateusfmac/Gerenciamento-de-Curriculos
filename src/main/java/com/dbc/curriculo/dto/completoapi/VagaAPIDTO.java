package com.dbc.curriculo.dto.completoapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class VagaAPIDTO {

    private List<VagaApiListDTO> vagas;

    @Schema(example = "1", description = "Quantidade total de elementos disponível.")
    private int total;

    @Schema(example = "10", description = "Quantidade de páginas paginada disponível.")
    private int paginas;

    @Schema(example = "1", description = "Página atual da lista paginada.")
    private int pagina;

    @Schema(example = "10")
    private int quantidade;
}
