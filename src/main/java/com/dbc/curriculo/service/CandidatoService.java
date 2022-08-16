package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.candidato.CandidadoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoVagaDTO;
import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.endereco.EnderecoDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaDTO;
import com.dbc.curriculo.entity.CandidatoEntity;
import com.dbc.curriculo.entity.EnderecoEntity;
import com.dbc.curriculo.entity.EscolaridadeEntity;
import com.dbc.curriculo.entity.ExperienciaEntity;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final ObjectMapper objectMapper;
    private final CandidatoRepository candidatoRepository;

    public List<CandidatoEntity> getAllCandidatoEntityById(List<CandidatoVagaDTO> vagaCreate){
        List<Integer> listIds = vagaCreate.stream().map(CandidatoVagaDTO::getIdCandidato).toList();
        return candidatoRepository.findAllById(listIds);
    }

    public List<CandidadoDadosDTO> getAllCandidatoDTO(){
        return candidatoRepository.findAll()
                .stream().map(this::getDadoCandidato).toList();
    }

    public CandidatoDTO saveCandidato(CandidatoCreateDTO candidatoCreate){

        CandidatoEntity candidato = convertToCandidatoEntity(candidatoCreate);

        if(candidatoCreate.getEndereco() != null){
            EnderecoEntity enderecoEntity = convertToEnderecoEntity(candidatoCreate.getEndereco());
            candidato.setEnderecoEntity(enderecoEntity);
        }

        if(candidatoCreate.getEscolaridades() != null){
            Set<EscolaridadeEntity> escolaridades = candidatoCreate.getEscolaridades().stream().map(
                    esc-> convertToEscolaridadeEntity(esc, candidato)
            ).collect(Collectors.toSet());
            candidato.setEscolaridadeEntities(escolaridades);
        }

        if(candidatoCreate.getExperiencias() != null){
            Set<ExperienciaEntity> experiencias = candidatoCreate.getExperiencias().stream().map(
                    exp-> convertToExperienciaEntity(exp, candidato)
            ).collect(Collectors.toSet());
            candidato.setExperienciaEntities(experiencias);
        }

        candidatoRepository.save(candidato);

        candidato.getExperienciaEntities().forEach(System.out::println);

        return getAllDadosCandidato(candidato);
    }

    public void deleteCandidato(Integer idCandidato){
        candidatoRepository.deleteById(idCandidato);
    }

    private CandidadoDadosDTO getDadoCandidato(CandidatoEntity candidato){
        return objectMapper.convertValue(candidato, CandidadoDadosDTO.class);
    }

    private CandidatoDTO getAllDadosCandidato(CandidatoEntity candidato){
        CandidatoDTO candidatoDTO = converterCandidatoDTO(candidato);

        if(candidato.getEnderecoEntity() != null){
            EnderecoDTO enderecoDTO = converterEnderecoDTO(candidato.getEnderecoEntity());
            candidatoDTO.setEndereco(enderecoDTO);
        }

        if(candidato.getExperienciaEntities() != null){
            List<ExperienciaDTO> experienciaDTOList =
                    candidato
                            .getExperienciaEntities()
                            .stream()
                            .map(this::converterExperienciaDTO).toList();
            candidatoDTO.setExperiencia(experienciaDTOList);
        }

        if(candidato.getEscolaridadeEntities() != null){
            List<EscolaridadeDTO> escolaridadeDTOList =
                    candidato
                            .getEscolaridadeEntities()
                            .stream().map(this::converterEscolaridadeDTO).toList();
            candidatoDTO.setEscolaridade(escolaridadeDTOList);
        }

        return candidatoDTO;
    }

    private CandidatoEntity convertToCandidatoEntity(CandidatoCreateDTO candidatoCreate){
        return objectMapper.convertValue(candidatoCreate, CandidatoEntity.class);
    };

    private CandidatoDTO converterCandidatoDTO(CandidatoEntity candidatoEntity){
        return objectMapper.convertValue(candidatoEntity, CandidatoDTO.class);
    }

    private EnderecoEntity convertToEnderecoEntity(EnderecoCreateDTO enderecoCreate){
        return objectMapper.convertValue(enderecoCreate, EnderecoEntity.class);
    };

    private EnderecoDTO converterEnderecoDTO(EnderecoEntity enderecoEntity){
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    private EscolaridadeEntity convertToEscolaridadeEntity(EscolaridadeCreateDTO escolaridadeCreate,
                                                          CandidatoEntity candidato){
        EscolaridadeEntity escolaridadeEntity = objectMapper
                .convertValue(escolaridadeCreate, EscolaridadeEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    };

    private EscolaridadeDTO converterEscolaridadeDTO(EscolaridadeEntity escolaridadeEntity){
        return objectMapper.convertValue(escolaridadeEntity, EscolaridadeDTO.class);
    }

    private ExperienciaEntity convertToExperienciaEntity(ExperienciaCreateDTO experienciaCreateDTO,
                                                        CandidatoEntity candidato){
        ExperienciaEntity escolaridadeEntity = objectMapper
                .convertValue(experienciaCreateDTO, ExperienciaEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    };

    private ExperienciaDTO converterExperienciaDTO(ExperienciaEntity experienciaEntity){
        return objectMapper.convertValue(experienciaEntity, ExperienciaDTO.class);
    }

}
