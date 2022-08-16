package com.dbc.curriculo.dto.experiencia;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ExperienciaCreateDTO {

    @NotEmpty
    private String instituicao;
    @NotEmpty
    private String descricao;
    @NotEmpty
    private String cargo;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataFim;
}
