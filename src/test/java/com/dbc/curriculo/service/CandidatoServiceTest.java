package com.dbc.curriculo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.dbc.curriculo.dto.candidato.*;
import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.entity.*;
import com.dbc.curriculo.enums.TipoSenioridade;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.CandidatoValidarException;
import com.dbc.curriculo.exceptions.S3Exception;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private AmazonS3Service amazonS3Service;

    private MockMultipartFile documento;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(candidatoService, "objectMapper", objectMapper);

        documento = new MockMultipartFile(
                "arquivo.pdf",
                "arquivo",
                "application/pdf",
                "{key1: value1}".getBytes());
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
    public void deveTestarGetAllCandidatoDTO() {
        CandidatoEntity candidato = getCandidatoAllDados();

        when(candidatoRepository.findAll()).thenReturn(List.of(candidato));

        List<CandidatoDadosDTO> listCandidadosDTO = candidatoService.getAllCandidatoDTO();

        assertEquals(1, listCandidadosDTO.size());

    }

    @Test(expected = CandidatoValidarException.class)
    public void deveTestarErrorSeCPFouTelefoneJaCadastrado() throws S3Exception, CandidatoException, CandidatoValidarException {

        CandidatoCreateDTO candidatoCreateDTO = getCandidadoCreateDTO();
        CandidatoEntity candidatoEntity = getCandidatoAllDados();

        when(candidatoRepository.findByCpf(anyString())).thenReturn(Optional.of(candidatoEntity));
        when(candidatoRepository.findByTelefone(anyString())).thenReturn(Optional.of(candidatoEntity));

        candidatoService.saveCandidato(candidatoCreateDTO, documento);

    }

    @Test(expected = CandidatoValidarException.class)
    public void deveTestarErrorSeCPFJaCadastradoMasTelefoneNao() throws S3Exception, CandidatoException, CandidatoValidarException {

        CandidatoCreateDTO candidatoCreateDTO = getCandidadoCreateDTO();
        CandidatoEntity candidatoEntity = getCandidatoAllDados();

        when(candidatoRepository.findByCpf(anyString())).thenReturn(Optional.of(candidatoEntity));
        when(candidatoRepository.findByTelefone(anyString())).thenReturn(Optional.empty());

        candidatoService.saveCandidato(candidatoCreateDTO, documento);

    }

    @Test(expected = CandidatoValidarException.class)
    public void deveTestarErrorSeTelefoneJaCadastradoMasCPFNao() throws S3Exception, CandidatoException, CandidatoValidarException {

        CandidatoCreateDTO candidatoCreateDTO = getCandidadoCreateDTO();
        CandidatoEntity candidatoEntity = getCandidatoAllDados();

        when(candidatoRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(candidatoRepository.findByTelefone(anyString())).thenReturn(Optional.of(candidatoEntity));

        candidatoService.saveCandidato(candidatoCreateDTO, documento);

    }

    @Test
    public void deveTestarSaveCandidato() throws S3Exception, CandidatoException,
            MalformedURLException, URISyntaxException, CandidatoValidarException {
        CandidatoCreateDTO candidatoCreateDTO = getCandidadoCreateDTO();
        URL url = new URL(
                "https",
                "stackoverflow.com",
                80, "pages/page1.html");

        when(candidatoRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(candidatoRepository.findByTelefone(anyString())).thenReturn(Optional.empty());

        //doReturn(null).when(amazonS3).putObject(any(), any(), any(), any());
        //when(amazonS3.getUrl(anyString(), anyString())).thenReturn(url);
        when(amazonS3Service.uploadFile(any())).thenReturn(url.toURI());
        candidatoService.saveCandidato(candidatoCreateDTO, documento);

    }

    @Test
    public void deveTestarDeleteComSucesso() throws CandidatoException {
        CandidatoEntity candidato = getCandidatoAllDados();
        Integer idDeletar = 1;

        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidato));
        doNothing().when(candidatoRepository).delete(any(CandidatoEntity.class));

        candidatoService.deleteCandidato(idDeletar);

        verify(candidatoRepository, times(1)).delete(any(CandidatoEntity.class));

    }

    @Test
    public void deveTestarUpdateComSucesso() throws CandidatoException {
        CandidatoEntity candidato = getCandidatoAllDados();
        CandidatoUpdateDTO candidatoUpdateDTO = getCandidatoUpdate();

        candidato.setExperienciaEntities(new HashSet<>());
        candidato.getExperienciaEntities().clear();
        candidato.setEscolaridadeEntities(new HashSet<>());
        candidato.getEscolaridadeEntities().clear();

        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidato));
        when(candidatoRepository.save(any(CandidatoEntity.class))).thenReturn(candidato);

        candidatoService.updateCandidato(candidatoUpdateDTO);

    }

    @Test
    public void deveTestarGetAllCandidatoEntityById() {
    List<CandidatoEntity> candidatoEntities = List.of(getCandidatoAllDados());
    List<CandidatoVagaDTO> candidatoVagaDTOS = List.of(new CandidatoVagaDTO());

    when(candidatoRepository.findAllById(any())).thenReturn(candidatoEntities);

    candidatoService.getAllCandidatoEntityById(candidatoVagaDTOS);
    }

    private CandidatoEntity getCandidatoAllDados() {
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato.setNome("Aurora");
        candidato.setCpf("69805926109");
        candidato.setDataNascimento(LocalDate.parse("1978-07-03"));
        candidato.setTelefone("81927277790");
        candidato.setSenioridade(TipoSenioridade.ESPECIALISTA);
        candidato.setCargo("Desenvolvedora mobile - IOS");
        candidato.setCurriculoUrl("https://github.com");

        candidato.setEnderecoEntity(getEnderecoEntity());

        EscolaridadeEntity escolaridadeEntity = getEscolaridade();
        escolaridadeEntity.setCandidatoEntity(candidato);
        candidato.setEscolaridadeEntities(Set.of(escolaridadeEntity));

        ExperienciaEntity experienciaEntity = getExperiencia();
        experienciaEntity.setCandidatoEntity(candidato);
        candidato.setExperienciaEntities(Set.of(experienciaEntity));

        VagaEntity vagaEntity = getVagas();
        vagaEntity.setCandidatoEntities(Set.of(candidato));
        candidato.setVagaEntities(Set.of(vagaEntity));

        return candidato;
    }

    private EnderecoEntity getEnderecoEntity() {
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setIdEndereco(1);
        enderecoEntity.setCep("13848000");
        enderecoEntity.setNumero(202);
        enderecoEntity.setLogradouro("Rua Paulo de Frontin");
        enderecoEntity.setBairro("Imbiribeira");
        enderecoEntity.setCidade("Recife");
        return enderecoEntity;
    }

    private EscolaridadeEntity getEscolaridade() {
        EscolaridadeEntity escolaridadeEntity = new EscolaridadeEntity();
        escolaridadeEntity.setIdEscolaridade(10);
        escolaridadeEntity.setInstituicao("SENAI");
        escolaridadeEntity.setDataInicio(LocalDate.parse("2011-01-27"));
        escolaridadeEntity.setDataFim(LocalDate.parse("2013-12-17"));
        escolaridadeEntity.setDescricao("Curso sobre desenvolvimento mobile");
        escolaridadeEntity.setNivel("Técnico");
        return escolaridadeEntity;
    }

    private ExperienciaEntity getExperiencia() {
        ExperienciaEntity experiencia = new ExperienciaEntity();
        experiencia.setIdExperiencia(10);

        experiencia.setCargo("Desenvolvedor mobile.");
        experiencia.setInstituicao("Solução TI");
        experiencia.setDataInicio(LocalDate.parse("2018-01-01"));
        experiencia.setDataFim(LocalDate.parse("2019-12-29"));

        return experiencia;
    }

    private VagaEntity getVagas() {
        VagaEntity vaga = new VagaEntity();
        vaga.setIdVaga(1);
        return vaga;
    }

    private CandidatoCreateDTO getCandidadoCreateDTO() {
        CandidatoCreateDTO candidatoCreateDTO = new CandidatoCreateDTO();

        candidatoCreateDTO.setNome("Rafael");
        candidatoCreateDTO.setDataNascimento(LocalDate.parse("1995-10-10"));
        candidatoCreateDTO.setCargo("Desenvolvedor Junior");
        candidatoCreateDTO.setSenioridade(TipoSenioridade.ESPECIALISTA);
        candidatoCreateDTO.setCpf("69805926109");
        candidatoCreateDTO.setTelefone("81927277790");

        EnderecoCreateDTO enderecoCreateDTO = objectMapper
                .convertValue(getEnderecoEntity(), EnderecoCreateDTO.class);
        ExperienciaCreateDTO experienciaCreateDTO = objectMapper
                .convertValue(getExperiencia(), ExperienciaCreateDTO.class);
        EscolaridadeCreateDTO escolaridadeCreateDTO = objectMapper
                .convertValue(getEscolaridade(), EscolaridadeCreateDTO.class);

        candidatoCreateDTO.setEndereco(enderecoCreateDTO);
        candidatoCreateDTO.setExperiencias(List.of(experienciaCreateDTO));
        candidatoCreateDTO.setEscolaridades(List.of(escolaridadeCreateDTO));

        return candidatoCreateDTO;
    }

    private CandidatoUpdateDTO getCandidatoUpdate() {
        CandidatoUpdateDTO candidatoUpdateDTO = new CandidatoUpdateDTO();
        candidatoUpdateDTO.setIdCandidato(1);
        candidatoUpdateDTO.setNome("Rafael");
        candidatoUpdateDTO.setDataNascimento(LocalDate.parse("1995-10-10"));
        candidatoUpdateDTO.setCargo("Desenvolvedor Junior");
        candidatoUpdateDTO.setSenioridade(TipoSenioridade.ESPECIALISTA);
        candidatoUpdateDTO.setCpf("69805926109");
        candidatoUpdateDTO.setTelefone("81927277790");
        EnderecoCreateDTO enderecoCreateDTO = objectMapper
                .convertValue(getEnderecoEntity(), EnderecoCreateDTO.class);
        ExperienciaCreateDTO experienciaCreateDTO = objectMapper
                .convertValue(getExperiencia(), ExperienciaCreateDTO.class);
        EscolaridadeCreateDTO escolaridadeCreateDTO = objectMapper
                .convertValue(getEscolaridade(), EscolaridadeCreateDTO.class);
        candidatoUpdateDTO.setEndereco(enderecoCreateDTO);
        candidatoUpdateDTO.setExperiencias(List.of(experienciaCreateDTO));
        candidatoUpdateDTO.setEscolaridades(List.of(escolaridadeCreateDTO));
        return candidatoUpdateDTO;
    }

}
