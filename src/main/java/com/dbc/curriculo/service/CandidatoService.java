package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDadosDTO;
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
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.S3Exception;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final ObjectMapper objectMapper;
    private final CandidatoRepository candidatoRepository;
    private final AmazonS3Service amazonS3Service;

    public List<CandidatoEntity> getAllCandidatoEntityById(List<CandidatoVagaDTO> vagaCreate){
        List<Integer> listIds = vagaCreate.stream().map(CandidatoVagaDTO::getIdCandidato).toList();
        return candidatoRepository.findAllById(listIds);
    }

    public CandidatoDTO getCandidatoPorId(Integer idCandidato) throws CandidatoException {
        return candidatoRepository.findById(idCandidato)
                .stream()
                .map(this::getAllDadosCandidato)
                .findFirst()
                .orElseThrow(()-> new CandidatoException("Error ao buscar candidato"));
    }

    public List<CandidatoDadosDTO> getAllCandidatoDTO(){
        return candidatoRepository.findAll()
                .stream().map(this::getDadoCandidato).toList();
    }


    public CandidatoDadosDTO saveCandidato(CandidatoCreateDTO candidatoCreate,
                                           MultipartFile documento) throws S3Exception, CandidatoException {

        CandidatoEntity candidato = convertToCandidatoEntity(candidatoCreate);

        validarSeCPFOrTelefoneJaEstaoCadastrado(candidato);

        if(candidatoCreate.getEndereco() != null){
            EnderecoEntity enderecoEntity = convertToEnderecoEntity(candidatoCreate.getEndereco());
            candidato.setEnderecoEntity(enderecoEntity);
        } else {
            candidato.setExperienciaEntities(Set.of());
        }

        if(candidatoCreate.getEscolaridades() != null){
            Set<EscolaridadeEntity> escolaridades = candidatoCreate.getEscolaridades().stream().map(
                    esc-> convertToEscolaridadeEntity(esc, candidato)
            ).collect(Collectors.toSet());
            candidato.setEscolaridadeEntities(escolaridades);
        }else {
            candidato.setEscolaridadeEntities(Set.of());
        }

        if(candidatoCreate.getExperiencias() != null){
            Set<ExperienciaEntity> experiencias = candidatoCreate.getExperiencias().stream().map(
                    exp-> convertToExperienciaEntity(exp, candidato)
            ).collect(Collectors.toSet());
            candidato.setExperienciaEntities(experiencias);
        } else {
            candidato.setExperienciaEntities(Set.of());
        }

        URI amazonUri = amazonS3Service.uploadFile(documento);
        candidato.setCurriculoUrl(amazonUri.toString());

        candidatoRepository.save(candidato);

        return getDadoCandidato(candidato);

    }

    private void validarSeCPFOrTelefoneJaEstaoCadastrado(CandidatoEntity candidato)
            throws CandidatoException {
        Optional<CandidatoEntity> candidatoCPF =
                candidatoRepository.findByCpf(candidato.getCpf());

        Optional<CandidatoEntity> candidatoNumero =
                candidatoRepository.findByTelefone(candidato.getTelefone());

        if(candidatoCPF.isPresent() && candidatoNumero.isPresent()){
            throw new CandidatoException("CPF já cadastrado e número já cadastrado.");
        } else if(candidatoCPF.isPresent()){
            throw new CandidatoException("CPF já cadastrado.");
        } else if(candidatoNumero.isPresent()){
            throw new CandidatoException("Número já cadastrado.");
        }
    }

    public CandidatoDTO updateCandidato(CandidatoCreateDTO candidatoCreateDTO, Integer idCandidato) throws CandidatoException{

        //CandidatoEntity candidato = ;

       CandidatoEntity candidato = candidatoRepository.findById(idCandidato).stream().findFirst().orElseThrow(()-> new CandidatoException("N existe"));

        candidato.setNome(candidatoCreateDTO.getNome());

        candidato.setCpf(candidatoCreateDTO.getCpf());

        candidato.setCargo(candidatoCreateDTO.getCargo());

        candidato.setCurriculoUrl(candidato.getCurriculoUrl());

        candidato.setDataNascimento(candidatoCreateDTO.getDataNascimento());

        candidato.setSenioridade(candidatoCreateDTO.getSenioridade());

        candidato.setTelefone(candidatoCreateDTO.getTelefone());


        candidatoRepository.save(candidato);
        return converterCandidatoDTO(candidato);
    }

    public void deleteCandidato(Integer idCandidato){
        candidatoRepository.deleteById(idCandidato);
    }

    private CandidatoDadosDTO getDadoCandidato(CandidatoEntity candidato){
        return objectMapper.convertValue(candidato, CandidatoDadosDTO.class);
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
    }

    private CandidatoEntity convertCandidatoEntity(CandidatoDTO candidatoDTO){
        return objectMapper.convertValue(candidatoDTO, CandidatoEntity.class);
    }

    private CandidatoDTO converterCandidatoDTO(CandidatoEntity candidatoEntity){
        return objectMapper.convertValue(candidatoEntity, CandidatoDTO.class);
    }

    private EnderecoEntity convertToEnderecoEntity(EnderecoCreateDTO enderecoCreate){
        return objectMapper.convertValue(enderecoCreate, EnderecoEntity.class);
    }

    private EnderecoDTO converterEnderecoDTO(EnderecoEntity enderecoEntity){
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    private EscolaridadeEntity convertToEscolaridadeEntity(EscolaridadeCreateDTO escolaridadeCreate,
                                                          CandidatoEntity candidato){
        EscolaridadeEntity escolaridadeEntity = objectMapper
                .convertValue(escolaridadeCreate, EscolaridadeEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    }

    private EscolaridadeDTO converterEscolaridadeDTO(EscolaridadeEntity escolaridadeEntity){
        return objectMapper.convertValue(escolaridadeEntity, EscolaridadeDTO.class);
    }

    private ExperienciaEntity convertToExperienciaEntity(ExperienciaCreateDTO experienciaCreateDTO,
                                                        CandidatoEntity candidato){
        ExperienciaEntity escolaridadeEntity = objectMapper
                .convertValue(experienciaCreateDTO, ExperienciaEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    }

    private ExperienciaDTO converterExperienciaDTO(ExperienciaEntity experienciaEntity){
        return objectMapper.convertValue(experienciaEntity, ExperienciaDTO.class);
    }

}
