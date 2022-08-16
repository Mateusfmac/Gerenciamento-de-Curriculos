package com.dbc.curriculo.dto.vaga;

import com.dbc.curriculo.dto.candidato.CandidatoVagaDTO;
import lombok.Data;

import java.util.List;

@Data
public class VagaCreateDTO {

    private Integer idVaga;

    private List<CandidatoVagaDTO> candidatos;

}
