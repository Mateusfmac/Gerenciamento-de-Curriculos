package com.dbc.curriculo.controller;

import com.dbc.curriculo.documentation.DocumentationCandidatoController;
import com.dbc.curriculo.dto.PageDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoUpdateDTO;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.CandidatoValidarException;
import com.dbc.curriculo.exceptions.S3Exception;
import com.dbc.curriculo.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidato")
@Validated
public class CandidatoController implements DocumentationCandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping("/list-candidato")
    public ResponseEntity<List<CandidatoDadosDTO>> getListCandidato() {
        return ResponseEntity.ok(candidatoService.getAllCandidatoDTO());
    }

    @GetMapping("/get-candidato/{idCandidato}")
    public ResponseEntity<CandidatoDTO> getCandidato(
            @PathVariable Integer idCandidato) throws CandidatoException {
        CandidatoDTO candidatoDTO = candidatoService.getCandidatoPorId(idCandidato);
        return ResponseEntity.ok(candidatoDTO);
    }

    @GetMapping("/list-candidato-paginado")
    public ResponseEntity<PageDTO<CandidatoDadosDTO>> paginacaoCandidato
            (@RequestParam(required = false, defaultValue = "1") Integer pagina ,
             @RequestParam(required = false, defaultValue = "20") Integer qtRegistro){
        return ResponseEntity.ok(candidatoService.getCandidatoPagination(pagina, qtRegistro));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CandidatoDadosDTO> saveCandidato(
            @Valid @RequestPart("candidato") CandidatoCreateDTO candidato,
            @Valid @NotNull @RequestPart("documento") MultipartFile documento
    )
            throws S3Exception, IOException, CandidatoValidarException {
        CandidatoDadosDTO candidatoDTO = candidatoService.saveCandidato(candidato, documento);
        return ResponseEntity.ok(candidatoDTO);
    }

    @PutMapping("/update-candidato")
    public ResponseEntity<CandidatoDTO> updateCandidato(
            @Valid @RequestBody CandidatoUpdateDTO candidatoUpdateDTO) throws CandidatoException {
        CandidatoDTO candidatoDTO = candidatoService.updateCandidato(candidatoUpdateDTO);
        return ResponseEntity.ok(candidatoDTO);
    }

    @PutMapping(value="/update-documento/{idCandidato}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateCandidatoDocumento(
            @PathVariable Integer idCandidato,
            @Valid @NotNull @RequestPart MultipartFile documento) throws CandidatoException, S3Exception, IOException {
        String urlCurriculo = candidatoService.updateDocumentoCandidato(idCandidato, documento);
        return ResponseEntity.ok(urlCurriculo);
    }

    @DeleteMapping("/{idCandidato}")
    public void delete(@PathVariable("idCandidato") Integer idCandidato) throws CandidatoException {
        candidatoService.deleteCandidato(idCandidato);
    }

}
