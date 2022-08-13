package com.dbc.curriculo.security;

import com.dbc.curriculo.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("$jwt.issuer")
    private String issuer;

    // Todo - Adicionar usuário
    public String getToken(UsuarioEntity usuarioEntity){

        Integer idFaker = 1;

        final Date now = new Date();
        final Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        String token = Jwts.builder()
                .setIssuer(issuer)
                .claim(Claims.ID, usuarioEntity.getIdLogin())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TokenAutorizationFilter.BEARER + token;
    }

    public UsernamePasswordAuthenticationToken validarTokenUsuario(String token){
        if(token == null){
            return null;
        }

        Claims body = getBodyClaims(token);
        Integer idUsuario = body.get(Claims.ID, Integer.class);

        if(idUsuario != null){
            return new UsernamePasswordAuthenticationToken(
                    idUsuario,
                    null,
                    new ArrayList<>()
            );
        }
        return null;
    }

    private Claims getBodyClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    }


}
