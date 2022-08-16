package com.dbc.curriculo.dto.escolaridade;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EscolaridadeCreateDTO {
    
    private String instituicao;
    
    private String descricao;
    
    private String nivel;
    
    private LocalDate dataInicio;
    
    private LocalDate dataFim;
}
