package com.dbc.curriculo.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginCredenciaisDTO {

    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@dbccompany.com.br")
    @NotBlank
    @Schema(
            description = "Email para login do usuario.",
            example = "sonia_jesus@bakerhughes.com"
    )
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Senha do usuario.")
    private String senha;
}
