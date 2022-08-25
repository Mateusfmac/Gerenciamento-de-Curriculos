package com.dbc.curriculo.dto.completoApi;
// FIXME package minusculo
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VagaApiRootDTO {
        @JsonProperty("id")
        public String id;
        // FIXME list
        public ArrayList<VagaAPIRootListDTO> vagaGeralList;
        public int total;
        public int paginas;
        public int pagina;
        public int quantidade;
}
