package com.dbc.curriculo.dto.candidato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoUpdateDTO extends CandidatoCreateDTO {
    private Integer idCandidato;
}
