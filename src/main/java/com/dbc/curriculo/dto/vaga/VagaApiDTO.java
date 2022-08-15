package com.dbc.curriculo.dto.vaga;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class VagaApiDTO {
        @JsonProperty("id")
        public String id;
        public ArrayList<VagaAPIListDTO> vagaGeralList;
        public int total;
        public int paginas;
        public int pagina;
        public int quantidade;
}
