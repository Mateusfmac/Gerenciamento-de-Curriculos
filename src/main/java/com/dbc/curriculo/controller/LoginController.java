package com.dbc.curriculo.controller;

import com.dbc.curriculo.config.Response;
import com.dbc.curriculo.documentation.DocumentationLoginController;
import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.exceptions.LoginException;
import com.dbc.curriculo.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginDTO> criarCredenciasUsuario(@RequestBody @Valid LoginCredenciaisDTO loginCredenciais){
        LoginDTO loginDTO = loginService.createLoginUser(loginCredenciais);
        return ResponseEntity.ok(loginDTO);
    }

//    @GetMapping("/veficar-se-email-ja-esta-cadastrado")
//    public ResponseEntity<Boolean> verificarSeEmailJaEstaCadastrado(@RequestParam String email){
//        return ResponseEntity.ok(loginService.verificarSeEmailJaEstaCadastrado(email));
//    }


}

