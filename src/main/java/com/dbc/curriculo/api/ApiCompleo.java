package com.dbc.curriculo.api;

import com.dbc.curriculo.dto.completoApi.VagaApiRootDTO;
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

    @RequestLine("GET api/Relatorios/ListarRelatorioVagasGeral?Pagina={Pagina}&Quantidade={Quantidade}&RetornaHistoricoMudancaStatus=true&RetornaListaContatos=true&RetornaTags=true")
    public VagaApiRootDTO getVagas(@HeaderMap Map<String, String> headers,
                                   @Param Integer Pagina,
                                   @Param Integer Quantidade);

}
