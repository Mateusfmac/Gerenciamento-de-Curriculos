package com.dbc.curriculo.dto.vaga;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * Essa classe é a root para buscar os dados da API
 * @author Clebson Mendonça
 */
@Data
public class VagaApiRootDTO {
        @JsonProperty("id")
        public String id;
        public ArrayList<VagaAPIRootListDTO> vagaGeralList;
        public int total;
        public int paginas;
        public int pagina;
        public int quantidade;
}
