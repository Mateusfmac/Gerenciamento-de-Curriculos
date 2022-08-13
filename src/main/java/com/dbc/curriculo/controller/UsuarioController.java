package com.dbc.curriculo.controller;

import com.dbc.curriculo.config.Response;
import com.dbc.curriculo.dto.UsuarioDTO;
import com.dbc.curriculo.dto.UsuarioLoginDTO;
import com.dbc.curriculo.dto.UsuarioSenhaDTO;
import com.dbc.curriculo.entity.UsuarioEntity;
import com.dbc.curriculo.exceptions.RegraDeNegocioException;
import com.dbc.curriculo.security.TokenService;
import com.dbc.curriculo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    //@Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;
    
    private final TokenService tokenService;
    
    @Response
    @PostMapping("/login")
    public String login(@RequestBody @Valid UsuarioLoginDTO login) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getSenha()
                );
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        Object usuarioLogado = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioLogado;
        //TODO passar usuario service no get token
        String token = tokenService.getToken(usuarioEntity);
        return token;
    }
    
    @Operation(summary = "Exibir usuario logado")
    @Response
    @GetMapping("/logado")
    public ResponseEntity<UsuarioDTO> getLoggedUser() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegarUsuarioLogado(), HttpStatus.OK);
    }
    
    @Operation(summary = "Alterar senha de usuário")
    @Response
    @PutMapping("/atualizar-senha")
    public ResponseEntity<UsuarioDTO> atualizarSenha(@RequestBody @Valid UsuarioSenhaDTO usuarioSenha) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.atualizarSenha(usuarioSenha), HttpStatus.ACCEPTED);
    }
}

