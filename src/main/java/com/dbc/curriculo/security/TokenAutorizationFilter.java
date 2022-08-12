package com.dbc.curriculo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAutorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        tokenService.validarTokenUsuario(request.getHeader(AUTHORIZATION))
                );

        filterChain.doFilter(request, response);
    }
}
