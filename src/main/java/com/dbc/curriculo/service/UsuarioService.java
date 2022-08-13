package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.UsuarioDTO;
import com.dbc.curriculo.dto.UsuarioLoginDTO;
import com.dbc.curriculo.dto.UsuarioSenhaDTO;
import com.dbc.curriculo.entity.UsuarioEntity;
import com.dbc.curriculo.exceptions.RegraDeNegocioException;
import com.dbc.curriculo.repository.UsuarioRepository;
import com.dbc.curriculo.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    private final ObjectMapper objectMapper;
    
   // private final AuthenticationManager authenticationManager;
    
   // private final TokenService tokenService;
    
   /* private final BCryptPasswordEncoder passwordEncoder;*/
    
    public UsuarioEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    }
    
    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Integer pegarIdUsuarioLogado() {
        Integer idUsuario = (Integer) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return idUsuario;
    }
    
    public UsuarioDTO pegarUsuarioLogado() throws RegraDeNegocioException {
        return usuarioEntityToDTO(buscarPorId(pegarIdUsuarioLogado()));
    }
    
    public UsuarioDTO atualizarSenha(UsuarioSenhaDTO usuarioSenhaDTO) throws RegraDeNegocioException {
        
        Integer idUsuario = pegarIdUsuarioLogado();
        UsuarioEntity usuarioEntity = buscarPorId(idUsuario);
        //usuarioEntity.setSenha(criptografarSenha(usuarioSenhaDTO.getSenha()));
        
        usuarioRepository.save(usuarioEntity);
        
        return usuarioEntityToDTO(usuarioEntity);
    }
    
    /*public String autenticarTokenLogin(UsuarioLoginDTO login) {
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
    }*/
    
   /* private String criptografarSenha(String senha) {
        String encoder = passwordEncoder.encode(senha);
        return encoder;
    }*/
    
    public UsuarioDTO usuarioEntityToDTO(UsuarioEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
}
