package com.dbc.curriculo.documentation;

import com.dbc.curriculo.anotations.MagiaResponse;
import com.dbc.curriculo.dto.completoApi.VagaApiRootDTO;
import com.dbc.curriculo.dto.vaga.VagaCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DocumentationVagaController {

    @Operation(
            summary = "Busca as vagas na API.",
            description = "Busca as vagas paginadas da API Compleo."
    )
    @MagiaResponse
    public ResponseEntity<VagaApiRootDTO> getVagas(
            @PathVariable("pagina") Integer pagina,
            @PathVariable("quantidade") Integer quantidade);

    @Operation(
            summary = "Adicionar candidatos nas vagas.",
            description = "Adiciona os candidatos nas vagas do compleo."
    )
    public void adicionarCandidato(@RequestBody VagaCreateDTO vagaCreate);

}
