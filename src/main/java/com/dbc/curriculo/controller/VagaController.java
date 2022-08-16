package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.vaga.VagaApiRootDTO;
import com.dbc.curriculo.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
