package com.dbc.curriculo.dto.escolaridade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class EscolaridadeCreateDTO {

    @NotEmpty
    private String instituicao;
    @NotEmpty
    private String descricao;
    @NotEmpty
    private String nivel;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataFim;
}
