package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.dto.login.LoginUpdateSenhaDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.entity.LoginEntity;
import com.dbc.curriculo.exceptions.LoginException;
import com.dbc.curriculo.repository.LoginRepository;
import com.dbc.curriculo.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final String errorLogin = "Error ao buscar usuário.";
    private final LoginRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    private final TokenService tokenService;

    private Integer getIdUsuarioLogado() throws LoginException {
        Integer idUser;
        try {
            idUser =  (Integer) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            if(idUser == null) {
                throw new LoginException(errorLogin);
            }
        } catch (Exception e){
            throw new LoginException(errorLogin);
        }
        return idUser;
    }

    public Optional<LoginEntity> buscarLoginPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean verificarSeEmailJaEstaCadastrado(String email){
        return buscarLoginPorEmail(email).isPresent();
    }

    public LoginEntity buscarPorId(Integer idLogin) throws LoginException {
        return usuarioRepository.findById(idLogin)
                .orElseThrow(() -> new LoginException(errorLogin));
    }
    
    public LoginDTO getUsuarioLogado() throws LoginException {
        return loginEntityConvertToLoginDTO(buscarPorId(getIdUsuarioLogado()));
    }

    public LoginDTO createLoginUser(LoginCredenciaisDTO loginCredenciais) {
        LoginEntity loginEntity = LoginCredenciaisConvertToLoginEntity(loginCredenciais);
        loginEntity.setEnable(1);
        loginEntity.setSenha(criptografarSenha(loginEntity.getSenha()));
        usuarioRepository.save(loginEntity);

        return loginEntityConvertToLoginDTO(loginEntity);
    }

    public TokenDTO autenticarTokenLogin(
            LoginCredenciaisDTO login,
            AuthenticationManager authenticationManager) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        Object usuarioLogado = authentication.getPrincipal();
        LoginEntity loginEntity = (LoginEntity) usuarioLogado;

        return new TokenDTO(tokenService.getToken(loginEntity));
    }
    
    private String criptografarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }
    
    public LoginDTO loginEntityConvertToLoginDTO(LoginEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, LoginDTO.class);
    }

    public LoginEntity LoginCredenciaisConvertToLoginEntity(LoginCredenciaisDTO loginCredenciais) {
        return objectMapper.convertValue(loginCredenciais, LoginEntity.class);
    }
}
