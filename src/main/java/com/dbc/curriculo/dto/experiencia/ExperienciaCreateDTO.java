package com.dbc.curriculo.dto.experiencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienciaCreateDTO {

    @NotBlank
    private String instituicao;
    @NotBlank
    private String descricao;
    @NotBlank
    private String cargo;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataFim;
}
