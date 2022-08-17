package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidatoDadosDTO {

    private Integer idCandidato;

    private String nome;

    private String cargo;

    private LocalDate dataNascimento;

    private TipoSenioridade senioridade;

    private String curriculoUrl;

}
