package com.dbc.curriculo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAutorizationFilter extends OncePerRequestFilter {
    
    protected static final String BEARER = "Bearer ";
    
    private final TokenService tokenService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        
        UsernamePasswordAuthenticationToken dtoSpring = tokenService.validarTokenUsuario(token);
        
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                dtoSpring
                );
        
        filterChain.doFilter(request, response);
    }
    
    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        return token.replace(BEARER, "");
    }
}
