package com.dbc.curriculo.documentation;

import com.dbc.curriculo.anotations.Response;
import com.dbc.curriculo.dto.completoapi.VagaApiRootDTO;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.DefaultException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DocumentationVagaController {

    @Operation(
            summary = "Busca as vagas na API.",
            description = "Busca as vagas paginadas da API Compleo."
    )
    @Response
    public ResponseEntity<VagaApiRootDTO> getVagas(
            @PathVariable("pagina") Integer pagina,
            @PathVariable("quantidade") Integer quantidade);


    @Operation(
            summary = "Vincula candidatos nas vagas.",
            description = "Adiciona os candidatos nas vagas do compleo."
    )
    public void vincularCandidato(@PathVariable Integer idVaga,
                                  @PathVariable Integer idCandidato)
            throws CandidatoException;

    @Operation(
            summary = "Remove um candidato da vaga.",
            description = "Desvincula um candidato de uma determinada vaga."
    )
    public void removerCandidato(@PathVariable Integer idVaga,
                                 @PathVariable Integer idCandidato)
            throws CandidatoException, DefaultException;

}
