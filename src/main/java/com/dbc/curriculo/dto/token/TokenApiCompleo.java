package com.dbc.curriculo.dto.token;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class TokenApiCompleo {

    @Value("${compleo.vagas.token}")
    private String authorization;

    public Map<String, String> getAuthToken(){
        return new HashMap<>(Map.of("auth-token", authorization));
    }
}
