package com.dbc.curriculo.api;

import com.dbc.curriculo.dto.vaga.VagaApiDTO;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(
        name = "compelo-dbc",
        url = "https://api.compleo.com.br"
)
@Headers("Content-Type: application/json")
public interface ApiCompleo {

    @RequestLine("GET api/Relatorios/ListarRelatorioVagasGeral?Pagina={Pagina}&Quantidade={Quantidade}")
    public VagaApiDTO getVagas(@HeaderMap Map<String, String> headers,
                               @Param Integer Pagina,
                               @Param Integer Quantidade);

}
