package com.dbc.curriculo.controller;

import com.dbc.curriculo.documentation.DocumentationVagaController;
import com.dbc.curriculo.dto.completoApi.VagaApiRootDTO;
import com.dbc.curriculo.dto.vaga.VagaCreateDTO;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.DefaultException;
import com.dbc.curriculo.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaga")
@Controller
@RequiredArgsConstructor
public class VagaController implements DocumentationVagaController {

    private final VagaService vagaService;

    @GetMapping("/pagina={pagina}/quantidade={quantidade}")
    public ResponseEntity<VagaApiRootDTO> getVagas(
            @PathVariable("pagina") Integer pagina,
            @PathVariable("quantidade") Integer quantidade){
        return ResponseEntity.ok(vagaService.getVagas(pagina, quantidade));
    }

    // Todo -> Remover
    @PostMapping
    public void adicionarCandidato(@RequestBody VagaCreateDTO vagaCreate) {
        vagaService.adicionarCandidatosVaga(vagaCreate);
    }

    @PostMapping("/desvincular/vaga/{idVaga}/candidato/{idCandidato}")
    public void removerCandidato(@PathVariable Integer idVaga,
                                 @PathVariable Integer idCandidato)
            throws CandidatoException, DefaultException {
        vagaService.removerCandidatoVaga(idVaga, idCandidato);
    }

    @PostMapping("/vincular/vaga/{idVaga}/candidato/{idCandidato}")
    public void vincularCandidato(@PathVariable Integer idVaga,
                                 @PathVariable Integer idCandidato)
            throws CandidatoException {
        vagaService.vincularCandidatoVaga(idVaga, idCandidato);
    }

}
