package com.dbc.curriculo.controller;

import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.vaga.VagaCreateDTO;
import com.dbc.curriculo.entity.*;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.dbc.curriculo.repository.EscolaridadeRepository;
import com.dbc.curriculo.repository.ExperienciaRepository;
import com.dbc.curriculo.repository.VagaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {
    private final ObjectMapper objectMapper;
    
    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;
    private final ExperienciaRepository experienciaRepository;
    private final EscolaridadeRepository escolaridadeRepository;

    private static Integer idVaga = 15;

    
    
//    @PostMapping("/save")
//    public CandidatoCreateDTO save(@RequestBody CandidatoCreateDTO candidatoCreateDTO) throws JsonProcessingException {
//        EnderecoEntity enderecoEntity = objectMapper.convertValue(candidatoCreateDTO.getEnderecoEntity(),
//                EnderecoEntity.class);
//
//        Set<ExperienciaEntity> experienciaEntities =
//                candidatoCreateDTO.getExperienciaEntities()
//                        .stream().map(e->objectMapper.convertValue(e, ExperienciaEntity.class))
//                        .collect(Collectors.toSet());
//
//        Set<EscolaridadeEntity> escolaridadeEntities =
//                candidatoCreateDTO.getEscolaridadeEntities()
//                        .stream().map(e->objectMapper.convertValue(e, EscolaridadeEntity.class))
//                        .collect(Collectors.toSet());
//
//
//
//        VagaEntity vagaEntities = new VagaEntity();
//        vagaEntities.setIdVaga(idVaga);
//
//
//        vagaRepository.save(vagaEntities);
//        idVaga += 1;
//
//        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDTO, CandidatoEntity.class);
//
//
//
//        candidatoEntity.setEnderecoEntity(enderecoEntity);
//        //CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoCreateDTO, CandidatoEntity.class);
//        candidatoEntity.setExperienciaEntities(experienciaEntities);
//        candidatoEntity.setEscolaridadeEntities(escolaridadeEntities);
//        candidatoEntity.setVagaEntities(Set.of(vagaEntities));
//        candidatoRepository.save(candidatoEntity);
//
//
//        experienciaEntities.forEach(m->m.setCandidatoEntity(candidatoEntity));
//        escolaridadeEntities.forEach(m->m.setCandidatoEntity(candidatoEntity));
//
//        experienciaRepository.saveAll(experienciaEntities);
//        escolaridadeRepository.saveAll(escolaridadeEntities);
//
//
//        //objectMapper.convertValue(candidatoEntity, CandidatoCreateDTO.class);
//        return null;
//    }
}
