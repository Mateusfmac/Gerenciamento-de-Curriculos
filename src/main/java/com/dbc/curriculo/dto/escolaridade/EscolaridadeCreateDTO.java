package com.dbc.curriculo.dto.escolaridade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EscolaridadeCreateDTO {

    @Schema(example = "IFMA")
    @NotBlank
    private String instituicao;

    @Schema(example = "Curso de desenvolvimento java.")
    @NotBlank
    private String descricao;

    @Schema(example = "Técnico")
    @NotBlank
    private String nivel;

    @Schema(example = "2011-01-27")
    @NotNull
    private LocalDate dataInicio;

    @Schema(example = "2013-12-15")
    @NotNull
    private LocalDate dataFim;
}
