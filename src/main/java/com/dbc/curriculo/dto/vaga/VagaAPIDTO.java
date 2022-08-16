package com.dbc.curriculo.dto.vaga;

import lombok.Data;

import java.util.ArrayList;

@Data
public class VagaAPIDTO {
    public ArrayList<VagaApiListDTO> vagas;
    public int total;
    public int paginas;
    public int pagina;
    public int quantidade;
}
