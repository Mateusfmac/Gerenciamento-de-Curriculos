package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidadoDadosDTO {

    private Integer idCandidato;

    private String nome;

    private String cargo;

    private LocalDate dataNascimento;

    private TipoSenioridade senioridade;

}
