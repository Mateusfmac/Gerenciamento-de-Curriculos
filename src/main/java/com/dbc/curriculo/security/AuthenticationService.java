package com.dbc.curriculo.security;

import com.dbc.curriculo.entity.UsuarioEntity;
import com.dbc.curriculo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    
    private final UsuarioService usuarioService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorEmail(email);
        return usuarioEntity
                .orElseThrow(()-> new UsernameNotFoundException("Usuário inválido!"));
    }
}
