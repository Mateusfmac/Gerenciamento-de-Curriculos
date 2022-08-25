package com.dbc.curriculo.dto.completoApi;

import lombok.Data;

import java.util.ArrayList;

// FIXME falta documentação swagger
@Data
public class VagaAPIDTO {
    // FIXME deve ser list...
    public ArrayList<VagaApiListDTO> vagas;
    public int total;
    public int paginas;
    public int pagina;
    public int quantidade;
}
