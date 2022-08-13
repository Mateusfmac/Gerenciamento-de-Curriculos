package com.dbc.curriculo.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginCredenciaisDTO {

    @NotEmpty
    @Schema(
            description = "Email para login do usuario.",
            example = "sonia_jesus@bakerhughes.com"
    )
    private String email;

    @NotBlank
    @Size(min = 8)
    @Schema(description = "Senha do usuario.")
    private String senha;
}
