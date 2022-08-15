package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.vaga.VagaApiDTO;
import com.dbc.curriculo.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaga")
@Controller
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @GetMapping
    public ResponseEntity<VagaApiDTO> getVagas(Integer pagina, Integer quantidade){
        return ResponseEntity.ok(vagaService.getVagas(pagina, quantidade));
    }


}
