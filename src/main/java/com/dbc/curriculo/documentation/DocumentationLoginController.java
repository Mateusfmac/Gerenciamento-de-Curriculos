package com.dbc.curriculo.documentation;


import com.dbc.curriculo.anotations.MagiaResponse;
import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.exceptions.LoginException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface DocumentationLoginController {

    @Operation(
            summary = "Busca um token para o usuário com credenciais válidas.",
            description = "Se as credenciais do usuário forem válidas retornará uma token válido de acesso"
    )
    @MagiaResponse
    public ResponseEntity<TokenDTO> getTokenLogin(@RequestBody @Valid LoginCredenciaisDTO login);

    @Operation(
            summary = "Cria um usuário para acessar o sistema.",
            description = "As credenciais criadas serão utilizadas para fazer login e entre outros."
    )
    @MagiaResponse
    public ResponseEntity<LoginDTO> criarCredenciasUsuario(@RequestBody @Valid LoginCredenciaisDTO loginCredenciais) throws LoginException;

}
