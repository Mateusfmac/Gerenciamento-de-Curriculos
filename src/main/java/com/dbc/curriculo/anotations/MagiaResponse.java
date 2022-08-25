package com.dbc.curriculo.anotations;

import com.dbc.curriculo.exceptions.DefaultError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200",
                        description = "Operação concluída com sucesso."),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request! Parâmetros inválidos",
                        content = @Content(schema =
                            @Schema(implementation = DefaultError.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Permissão inválida! Ação não autorizada.",
                        content = @Content(schema =
                            @Schema(implementation = DefaultError.class)
                        )
                ),

                @ApiResponse(
                        responseCode = "404",
                        description = "Página não encontrada.",
                        content = @Content(schema =
                            @Schema(implementation = DefaultError.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erro! Não foi possível conectar com o servidor.",
                        content = @Content(schema =
                        @Schema(implementation = DefaultError.class)
                )
                )
        }
)
public @interface MagiaResponse {
    //FIXME Magia? o que seria magia? não usar nomes assim para um trabalho final e nem em clientes!
}