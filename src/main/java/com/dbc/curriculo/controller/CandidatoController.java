package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.entity.CandidatoEntity;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {
    private final ObjectMapper objectMapper;
    
    private final CandidatoRepository candidatoRepository;
    
    
    @PostMapping("/save")
    public CandidatoCreateDTO save(CandidatoCreateDTO candidatoCreateDTO) {
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDTO, CandidatoEntity.class);
        candidatoRepository.save(candidatoEntity);
        System.out.println(candidatoEntity);
        return objectMapper.convertValue(candidatoEntity, CandidatoCreateDTO.class);
    }
}
