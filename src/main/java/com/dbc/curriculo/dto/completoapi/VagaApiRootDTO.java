package com.dbc.curriculo.dto.completoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VagaApiRootDTO {
        @JsonProperty("id")
        private String id;

        private List<VagaAPIRootListDTO> vagaGeralList;
        private int total;
        private int paginas;
        private int pagina;
        private int quantidade;
}
