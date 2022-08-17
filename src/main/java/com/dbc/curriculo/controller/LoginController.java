package com.dbc.curriculo.controller;

import com.dbc.curriculo.documentation.DocumentationLoginController;
import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.exceptions.LoginException;
import com.dbc.curriculo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController implements DocumentationLoginController {

    private final LoginService loginService;

    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/get-token/login")
    public ResponseEntity<TokenDTO> getTokenLogin(@RequestBody @Valid LoginCredenciaisDTO login) {
        TokenDTO tokenDTO = loginService.autenticarTokenLogin(login, authenticationManager);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/criar-usuario")
    public ResponseEntity<LoginDTO> criarCredenciasUsuario(
            @RequestBody @Valid LoginCredenciaisDTO loginCredenciais) throws LoginException {
        LoginDTO loginDTO = loginService.createLoginUser(loginCredenciais);
        return ResponseEntity.ok(loginDTO);
    }

}

