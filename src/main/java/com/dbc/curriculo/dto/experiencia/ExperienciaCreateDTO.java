package com.dbc.curriculo.dto.experiencia;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ExperienciaCreateDTO {

    private String instituicao;
    private String descricao;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
