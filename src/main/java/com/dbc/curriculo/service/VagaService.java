package com.dbc.curriculo.service;

import com.dbc.curriculo.client.compleo.ApiCompleo;
import com.dbc.curriculo.dto.completoapi.VagaApiRootDTO;
import com.dbc.curriculo.dto.token.TokenApiCompleo;
import com.dbc.curriculo.entity.CandidatoEntity;
import com.dbc.curriculo.entity.VagaEntity;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.DefaultException;
import com.dbc.curriculo.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final TokenApiCompleo tokenApiCompleo;
    private final ApiCompleo apiCompleo;
    private final CandidatoService candidatoService;
    private final ObjectMapper objectMapper;
    private final VagaRepository vagaRepository;

    public VagaApiRootDTO getVagas(Integer pagina, Integer quantidade){
        Map<String, String> authToken = tokenApiCompleo.getAuthToken();
        return apiCompleo.getVagas(authToken, pagina, quantidade);
    }


    public void vincularCandidatoVaga(Integer idVaga, Integer idCandidato) throws CandidatoException {

        CandidatoEntity candidatos = candidatoService.findCandidatoById(idCandidato);

        Optional<VagaEntity> vagaOptional = vagaRepository.findById(idVaga);

        VagaEntity vagaEntity = vagaOptional.orElseGet(() -> new VagaEntity(idVaga, new HashSet<>()));

        Set<CandidatoEntity> candidatoEntitySet = new HashSet<>();
        if(vagaEntity.getCandidatoEntities() != null){
            candidatoEntitySet = vagaEntity.getCandidatoEntities();
        }

        candidatoEntitySet.add(candidatos);
        vagaEntity.setCandidatoEntities(candidatoEntitySet);
        vagaRepository.save(vagaEntity);

    }


    public void removerCandidatoVaga(Integer idVaga, Integer idCandidato) throws CandidatoException, DefaultException {


        CandidatoEntity candidato = candidatoService.findCandidatoById(idCandidato);

        Optional<VagaEntity> vagaOptional = vagaRepository
                .findById(idVaga);

        if(vagaOptional.isEmpty()){
            throw new DefaultException("Candidato não está cadastrado na vaga atual.");
        }

        VagaEntity vagaEntity = vagaOptional.get();

        if(vagaEntity.getCandidatoEntities() != null){
            vagaEntity.getCandidatoEntities().remove(candidato);
        }

        vagaRepository.save(vagaEntity);
    }

}
