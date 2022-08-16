package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.candidato.CandidadoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping("/list-candidato")
    public ResponseEntity<List<CandidadoDadosDTO>> getListCandidato(){
        return ResponseEntity.ok(candidatoService.getAllCandidatoDTO());
    }

    @PostMapping("/create")
    public ResponseEntity<CandidatoDTO> save(@RequestBody CandidatoCreateDTO candidatoCreateDTO) {
        return ResponseEntity.ok(candidatoService.saveCandidato(candidatoCreateDTO));
    }

    @DeleteMapping("/{idUsuario}")
    public void delete(@RequestParam("idUsuario") Integer idUsuario) {
        candidatoService.deleteCandidato(idUsuario);
    }
}
