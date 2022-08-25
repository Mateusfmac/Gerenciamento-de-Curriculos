package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.PageDTO;
import com.dbc.curriculo.dto.candidato.*;
import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.endereco.EnderecoDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaDTO;
import com.dbc.curriculo.dto.vaga.VagaDTO;
import com.dbc.curriculo.entity.*;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.CandidatoValidarException;
import com.dbc.curriculo.exceptions.S3Exception;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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

    public CandidatoEntity findCandidatoById(Integer idCandidato) throws CandidatoException {
        return candidatoRepository.findById(idCandidato)
                .stream()
                .findFirst()
                .orElseThrow(() -> new CandidatoException("Candidato não encontrado"));
    }

    public PageDTO getCandidatoPagination(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro, Sort.by(Sort.Order.asc("nome")));
        Page<CandidatoEntity> page = candidatoRepository.findAll(pageRequest);
        List<CandidatoDadosDTO> candidatoDadosDTOS =
                page.getContent().stream().map(this::getDadoCandidato).toList();
        return new PageDTO<>(
                page.getTotalElements(),
                page.getTotalPages(),
                pagina, registro,
                candidatoDadosDTOS);
    }

    public CandidatoDTO getCandidatoPorId(Integer idCandidato) throws CandidatoException {
        return candidatoRepository.findById(idCandidato)
                .stream()
                .map(this::getAllDadosCandidato)
                .findFirst()
                .orElseThrow(() -> new CandidatoException("Candidato inexistente"));
    }

    public List<CandidatoDadosDTO> getAllCandidatoDTO() {
        return candidatoRepository.findAll()
                .stream().map(this::getDadoCandidato).toList();
    }


    public CandidatoDadosDTO saveCandidato(CandidatoCreateDTO candidatoCreate,
                                           MultipartFile documento) throws S3Exception,
            CandidatoValidarException, IOException {

        CandidatoEntity candidato = convertToCandidatoEntity(candidatoCreate);

        validarSeCPFOrTelefoneJaEstaoCadastrado(candidato);

        if (candidatoCreate.getEndereco() != null) {
            EnderecoEntity enderecoEntity = convertToEnderecoEntity(candidatoCreate.getEndereco());
            candidato.setEnderecoEntity(enderecoEntity);
        }

        if (candidatoCreate.getEscolaridades() != null) {
            Set<EscolaridadeEntity> escolaridades = candidatoCreate.getEscolaridades().stream().map(
                    esc -> convertToEscolaridadeEntity(esc, candidato)
            ).collect(Collectors.toSet());
            candidato.setEscolaridadeEntities(escolaridades);
        }

        if (candidatoCreate.getExperiencias() != null) {
            Set<ExperienciaEntity> experiencias = candidatoCreate.getExperiencias().stream().map(
                    exp -> convertToExperienciaEntity(exp, candidato)
            ).collect(Collectors.toSet());
            candidato.setExperienciaEntities(experiencias);
        }

        // O serviço de upload de arquivo da amazon será desativado, para não quebrar a
        // aplicação será atribuido um link de um pdf

        /*
        URI amazonUri = amazonS3Service.uploadFile(documento);
        candidato.setCurriculoUrl(amazonUri.toString());
        */

        candidato.setCurriculoUrl("https://bit.ly/3e0vaH2");

        candidatoRepository.save(candidato);

        return getDadoCandidato(candidato);

    }


    public CandidatoDTO updateCandidato(CandidatoUpdateDTO candidatoUpdateDTO) throws CandidatoException {

        CandidatoEntity candidato = findCandidatoById(candidatoUpdateDTO.getIdCandidato());

        candidato.setNome(candidatoUpdateDTO.getNome());
        candidato.setCpf(candidatoUpdateDTO.getCpf());
        candidato.setCargo(candidatoUpdateDTO.getCargo());
        candidato.setCurriculoUrl(candidato.getCurriculoUrl());
        candidato.setDataNascimento(candidatoUpdateDTO.getDataNascimento());
        candidato.setSenioridade(candidatoUpdateDTO.getSenioridade());
        candidato.setTelefone(candidatoUpdateDTO.getTelefone());

        EnderecoEntity enderecoEntity = candidato.getEnderecoEntity();

        enderecoEntity.setCep(candidatoUpdateDTO.getEndereco().getCep());
        enderecoEntity.setBairro(candidatoUpdateDTO.getEndereco().getBairro());
        enderecoEntity.setCidade(candidatoUpdateDTO.getEndereco().getCidade());
        enderecoEntity.setNumero(candidatoUpdateDTO.getEndereco().getNumero());
        enderecoEntity.setEstado(candidatoUpdateDTO.getEndereco().getEstado());
        enderecoEntity.setLogradouro(candidatoUpdateDTO.getEndereco().getLogradouro());

        if (candidatoUpdateDTO.getEscolaridades() != null) {
            candidato.getEscolaridadeEntities().clear();
            List<EscolaridadeEntity> escolaridades = candidatoUpdateDTO.getEscolaridades().stream().map(
                    esc -> convertToEscolaridadeEntity(esc, candidato)
            ).toList();
            candidato.getEscolaridadeEntities().addAll(escolaridades);
        }

        if (candidatoUpdateDTO.getExperiencias() != null) {
            candidato.getExperienciaEntities().clear();
            List<ExperienciaEntity> experiencias = candidatoUpdateDTO.getExperiencias().stream().map(
                    exp -> convertToExperienciaEntity(exp, candidato)
            ).toList();
            candidato.getExperienciaEntities().addAll(experiencias);
        }

        candidatoRepository.save(candidato);
        return getAllDadosCandidato(candidato);
    }

    public String updateDocumentoCandidato(Integer idUsuario, MultipartFile file) throws S3Exception,
            IOException, CandidatoException {
        CandidatoEntity candidato = findCandidatoById(idUsuario);

        // O serviço de upload de arquivo da amazon será desativado, para não quebrar a
        // aplicação será atribuido um link de um pdf qualquer
        /*
        URI uri = amazonS3Service.uploadFile(file);
        candidato.setCurriculoUrl(uri.toString());
        */
        candidato.setCurriculoUrl("https://bit.ly/3QQkJ7J");
        candidatoRepository.save(candidato);
        return candidato.getCurriculoUrl();
    }

    public void deleteCandidato(Integer idCandidato) throws CandidatoException {
        CandidatoEntity candidato = findCandidatoById(idCandidato);
        candidatoRepository.delete(candidato);
    }

    private void validarSeCPFOrTelefoneJaEstaoCadastrado(CandidatoEntity candidato)
            throws CandidatoValidarException {
        Optional<CandidatoEntity> candidatoCPF =
                candidatoRepository.findByCpf(candidato.getCpf());

        Optional<CandidatoEntity> candidatoNumero =
                candidatoRepository.findByTelefone(candidato.getTelefone());

        if (candidatoCPF.isPresent() && candidatoNumero.isPresent()) {
            throw new CandidatoValidarException("CPF já cadastrado e número já cadastrado.");
        } else if (candidatoCPF.isPresent()) {
            throw new CandidatoValidarException("CPF já cadastrado.");
        } else if (candidatoNumero.isPresent()) {
            throw new CandidatoValidarException("Número já cadastrado.");
        }
    }

    private CandidatoDadosDTO getDadoCandidato(CandidatoEntity candidato) {
        CandidatoDadosDTO candidatoDadosDTO =
                objectMapper.convertValue(candidato, CandidatoDadosDTO.class);

        List<VagaDTO> vagas = new ArrayList<>();
        if (candidato.getVagaEntities() != null) {
            vagas = candidato.getVagaEntities()
                    .stream()
                    .map(this::vagaToVagaDTO).toList();
        }

        candidatoDadosDTO.setVagas(vagas);
        return candidatoDadosDTO;
    }

    private CandidatoDTO getAllDadosCandidato(CandidatoEntity candidato) {
        CandidatoDTO candidatoDTO = converterCandidatoDTO(candidato);

        if (candidato.getEnderecoEntity() != null) {
            EnderecoDTO enderecoDTO = converterEnderecoDTO(candidato.getEnderecoEntity());
            candidatoDTO.setEndereco(enderecoDTO);
        }

        if (candidato.getExperienciaEntities() != null) {
            List<ExperienciaDTO> experienciaDTOList =
                    candidato
                            .getExperienciaEntities()
                            .stream()
                            .map(this::converterExperienciaDTO).toList();
            candidatoDTO.setExperiencia(experienciaDTOList);
        }

        if (candidato.getEscolaridadeEntities() != null) {
            List<EscolaridadeDTO> escolaridadeDTOList =
                    candidato
                            .getEscolaridadeEntities()
                            .stream().map(this::converterEscolaridadeDTO).toList();
            candidatoDTO.setEscolaridade(escolaridadeDTOList);
        }

        return candidatoDTO;
    }

    private CandidatoEntity convertToCandidatoEntity(CandidatoCreateDTO candidatoCreate) {
        return objectMapper.convertValue(candidatoCreate, CandidatoEntity.class);
    }

    private CandidatoDTO converterCandidatoDTO(CandidatoEntity candidatoEntity) {
        return objectMapper.convertValue(candidatoEntity, CandidatoDTO.class);
    }

    private EnderecoEntity convertToEnderecoEntity(EnderecoCreateDTO enderecoCreate) {
        return objectMapper.convertValue(enderecoCreate, EnderecoEntity.class);
    }

    private EnderecoDTO converterEnderecoDTO(EnderecoEntity enderecoEntity) {
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    private EscolaridadeEntity convertToEscolaridadeEntity(EscolaridadeCreateDTO escolaridadeCreate,
                                                           CandidatoEntity candidato) {
        EscolaridadeEntity escolaridadeEntity = objectMapper
                .convertValue(escolaridadeCreate, EscolaridadeEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    }

    private EscolaridadeDTO converterEscolaridadeDTO(EscolaridadeEntity escolaridadeEntity) {
        return objectMapper.convertValue(escolaridadeEntity, EscolaridadeDTO.class);
    }

    private ExperienciaEntity convertToExperienciaEntity(ExperienciaCreateDTO experienciaCreateDTO,
                                                         CandidatoEntity candidato) {
        ExperienciaEntity escolaridadeEntity = objectMapper
                .convertValue(experienciaCreateDTO, ExperienciaEntity.class);
        escolaridadeEntity.setCandidatoEntity(candidato);
        return escolaridadeEntity;
    }

    private ExperienciaDTO converterExperienciaDTO(ExperienciaEntity experienciaEntity) {
        return objectMapper.convertValue(experienciaEntity, ExperienciaDTO.class);
    }

    private VagaDTO vagaToVagaDTO(VagaEntity vagaEntity) {
        return objectMapper.convertValue(vagaEntity, VagaDTO.class);
    }
}
