package com.dbc.curriculo.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginCredenciaisDTO {

    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@dbccompany.com.br")
    @NotBlank
    @Schema(
            description = "Email para login do usuario.",
            example = "sonia_jesus@dbccompany.com.br"
    )
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Senha do usuario.")
    private String senha;
}
