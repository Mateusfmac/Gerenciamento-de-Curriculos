package com.dbc.curriculo.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenDTO {

    @Schema(example = "Bearer eyJhbGciOiJIUzI.JzdWIiOiIxMjM0N.SflKxwRJSMeKKF")
    private final String token;
}
