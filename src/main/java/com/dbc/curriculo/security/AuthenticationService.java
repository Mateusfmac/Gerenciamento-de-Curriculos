package com.dbc.curriculo.security;

import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.entity.LoginEntity;
import com.dbc.curriculo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final LoginService loginService;
    private static final String errorLogin = "Credênciais inválidas.";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<LoginEntity> usuarioEntity = loginService.buscarLoginPorEmail(email);
        return usuarioEntity
                .orElseThrow(()-> new UsernameNotFoundException(errorLogin));
    }

}
