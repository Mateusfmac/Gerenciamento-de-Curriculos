package com.dbc.curriculo.service;

import com.dbc.curriculo.api.ApiCompleo;
import com.dbc.curriculo.dto.token.TokenApiCompleo;
import com.dbc.curriculo.dto.vaga.VagaApiRootDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final TokenApiCompleo tokenApiCompleo;
    private final ApiCompleo apiCompleo;



    public VagaApiRootDTO getVagas(Integer pagina, Integer quantidade){
        Map<String, String> authToken = tokenApiCompleo.getAuthToken();
        return apiCompleo.getVagas(authToken, pagina, quantidade);
    }

}
