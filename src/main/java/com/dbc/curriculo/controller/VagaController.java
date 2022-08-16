package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.completoApi.VagaApiRootDTO;
import com.dbc.curriculo.dto.vaga.VagaCreateDTO;
import com.dbc.curriculo.service.VagaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaga")
@Controller
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @GetMapping("/pagina={pagina}/quantidade={quantidade}")
    public ResponseEntity<VagaApiRootDTO> getVagas(
            @PathVariable("pagina") Integer pagina,
            @PathVariable("quantidade") Integer quantidade){
        return ResponseEntity.ok(vagaService.getVagas(pagina, quantidade));
    }

    @PostMapping
    public void create(@RequestBody VagaCreateDTO vagaCreate) throws JsonProcessingException {
        vagaService.addicionarCandidatosVaga(vagaCreate);
    }

}
