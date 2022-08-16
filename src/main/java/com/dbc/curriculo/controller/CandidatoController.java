package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.candidato.CandidadoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.exceptions.S3Exception;
import com.dbc.curriculo.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidato")
@Validated
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping("/list-candidato")
    public ResponseEntity<List<CandidadoDadosDTO>> getListCandidato(){
        return ResponseEntity.ok(candidatoService.getAllCandidatoDTO());
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CandidatoDTO> save(@RequestBody @Valid @ModelAttribute CandidatoCreateDTO candidatoCreateDTO) throws S3Exception {
        return ResponseEntity.ok(candidatoService.saveCandidato(candidatoCreateDTO));
    }

    @DeleteMapping("/{idUsuario}")
    public void delete(@RequestParam("idUsuario") Integer idUsuario) {
        candidatoService.deleteCandidato(idUsuario);
    }
}
