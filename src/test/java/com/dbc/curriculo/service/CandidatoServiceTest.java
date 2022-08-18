package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDadosDTO;
import com.dbc.curriculo.entity.*;
import com.dbc.curriculo.enums.TipoSenioridade;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private AmazonS3Service amazonS3Service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(candidatoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarGetCandidatoPorId() throws CandidatoException {
        CandidatoEntity candidato = getCandidatoAllDados();
        Integer idCandidato = 1;

        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidato));

        CandidatoDTO candidatoDTO = candidatoService.getCandidatoPorId(idCandidato);

        assertNotNull(candidatoDTO);
        assertEquals(1, candidato.getExperienciaEntities().size());
        assertEquals(1, candidato.getEscolaridadeEntities().size());
        assertNotNull(candidatoDTO.getEndereco());

    }

    @Test
    public void deveTestarGetAllCandidatoDTO(){
        CandidatoEntity candidato = getCandidatoAllDados();

        when(candidatoRepository.findAll()).thenReturn(List.of(candidato));

        List<CandidatoDadosDTO> listCandidadosDTO = candidatoService.getAllCandidatoDTO();

        assertEquals(1, listCandidadosDTO.size());

    }

    private CandidatoEntity getCandidatoAllDados(){
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato.setNome("Aurora");
        candidato.setCpf("69805926109");
        candidato.setDataNascimento(LocalDate.parse("1978-07-03"));
        candidato.setTelefone("81927277790");
        candidato.setSenioridade(TipoSenioridade.ESPECIALISTA);
        candidato.setCargo("Desenvolvedora mobile - IOS");
        candidato.setCurriculoUrl("https://github.com");
        candidato.setEnderecoEntity(createEnderecoEntity());
        candidato.setEscolaridadeEntities(Set.of(getEscolaridade(candidato)));
        candidato.setExperienciaEntities(Set.of(getExperiencia(candidato)));
        candidato.setVagaEntities(Set.of(getVagas(candidato)));
        return candidato;
    }

    private EnderecoEntity createEnderecoEntity(){
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setIdEndereco(1);
        enderecoEntity.setNumero(202);
        enderecoEntity.setLogradouro("Rua Paulo de Frontin");
        enderecoEntity.setBairro("Imbiribeira");
        enderecoEntity.setCidade("Recife");
        return enderecoEntity;
    }

    private EscolaridadeEntity getEscolaridade(CandidatoEntity candidato) {
        EscolaridadeEntity escolaridadeEntity = new EscolaridadeEntity();
        escolaridadeEntity.setIdEscolaridade(10);
        escolaridadeEntity.setInstituicao("SENAI");
        escolaridadeEntity.setCandidatoEntity(candidato);
        escolaridadeEntity.setDataInicio(LocalDate.parse("2011-01-27"));
        escolaridadeEntity.setDataFim(LocalDate.parse("2013-12-17"));
        escolaridadeEntity.setDescricao("Curso sobre desenvolvimento mobile");
        escolaridadeEntity.setNivel("Técnico");
        return escolaridadeEntity;
    }

    private ExperienciaEntity getExperiencia(CandidatoEntity candidato){
        ExperienciaEntity experiencia = new ExperienciaEntity();
        experiencia.setIdExperiencia(10);
        experiencia.setCandidatoEntity(candidato);
        experiencia.setCargo("Desenvolvedor mobile.");
        experiencia.setInstituicao("Solução TI");
        experiencia.setDataInicio(LocalDate.parse("2018-01-01"));
        experiencia.setDataFim(LocalDate.parse("2019-12-29"));

        return experiencia;
    }

    private VagaEntity getVagas(CandidatoEntity candidato){
        return new VagaEntity(1, Set.of(candidato));
    }

}
