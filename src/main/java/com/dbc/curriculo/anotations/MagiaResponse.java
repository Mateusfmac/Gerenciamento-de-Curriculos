package com.dbc.curriculo.anotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200",
                        description = "Operação concluída com sucesso."),
                @ApiResponse(responseCode = "400",
                        description = "Bad Request. Verifique seus parâmetros",
                        content = @Content(schema =
                        @Schema(implementation = Map.class)
                        )
                )
        }
)
public @interface MagiaResponse {
}