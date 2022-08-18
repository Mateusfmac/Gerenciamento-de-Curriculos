package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.vaga.VagaDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    List<VagaDTO> vagas;

}
