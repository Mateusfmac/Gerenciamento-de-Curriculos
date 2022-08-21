package com.dbc.curriculo.service;

import com.dbc.curriculo.client.compleo.ApiCompleo;
import com.dbc.curriculo.dto.completoApi.VagaAPIRootListDTO;
import com.dbc.curriculo.dto.completoApi.VagaApiRootDTO;
import com.dbc.curriculo.dto.token.TokenApiCompleo;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.entity.CandidatoEntity;
import com.dbc.curriculo.entity.VagaEntity;
import com.dbc.curriculo.enums.TipoSenioridade;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.DefaultException;
import com.dbc.curriculo.repository.VagaRepository;
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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VagaServiceTest {

    @InjectMocks
    private VagaService vagaService;

    @Mock
    private TokenApiCompleo tokenApiCompleo;

    @Mock
    private ApiCompleo apiCompleo;

    @Mock
    private CandidatoService candidatoService;

    @Mock
    private VagaRepository vagaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TokenDTO tokenDTO;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(vagaService, "objectMapper", objectMapper);

        tokenDTO = new TokenDTO("808b8b778");

    }

    @Test
    public void deveTestarGetVagas(){

        VagaApiRootDTO vagaApiRootDTO = getVagaApiRootDTO();

        Integer pagina = 1;
        Integer quantidade = 2;

        when(apiCompleo.getVagas(tokenApiCompleo.getAuthToken(), pagina, quantidade))
                .thenReturn(vagaApiRootDTO);

        VagaApiRootDTO vagas = vagaService.getVagas(pagina, quantidade);

        assertNotNull(vagas);
        assertEquals(2, vagas.getVagaGeralList().size());
        assertEquals(pagina.intValue(), vagas.getPagina());
        assertEquals(quantidade.intValue(), vagas.getQuantidade());
    }

    @Test
    public void deveTestarVincularCandidatosVagaNova() throws CandidatoException {


        when(vagaRepository.findById(anyInt())).thenReturn(Optional.empty());

        vagaService.vincularCandidatoVaga(1, 1);
        verify(vagaRepository, times(1)).save(any(VagaEntity.class));
    }

    @Test
    public void deveTestarDesvincularCandidatosVaga() throws CandidatoException, DefaultException {

        CandidatoEntity candidato = getCandidatoEntity();
        VagaEntity vagaEntity = getVagaEntity();

        when(candidatoService.findCandidatoById(anyInt()))
                .thenReturn(candidato);
        when(vagaRepository.findById(anyInt())).thenReturn(Optional.of(vagaEntity));
        when(vagaRepository.save(vagaEntity)).thenReturn(any(VagaEntity.class));

        vagaService.removerCandidatoVaga(1, 10);
        verify(vagaRepository, times(1)).save(any(VagaEntity.class));
    }

    @Test(expected = DefaultException.class)
    public void deveTestarExceptionDesvincularCandidatosVaga() throws CandidatoException, DefaultException {

        CandidatoEntity candidato = getCandidatoEntity();

        when(candidatoService.findCandidatoById(anyInt()))
                .thenReturn(candidato);
        when(vagaRepository.findById(anyInt())).thenReturn(Optional.empty());

        vagaService.removerCandidatoVaga(1, 10);
    }


    private VagaApiRootDTO getVagaApiRootDTO(){
        VagaApiRootDTO vaga = new VagaApiRootDTO();
        vaga.setId("#1");
        vaga.setTotal(5);
        vaga.setPaginas(3);
        vaga.setPagina(1);
        vaga.setQuantidade(2);

        vaga.setVagaGeralList(new ArrayList<>());

        vaga.getVagaGeralList()
                .addAll(Arrays.asList(getVagaAPIRootListDTO(), getVagaAPIRootListDTO()));
        return vaga;
    }

    private VagaAPIRootListDTO getVagaAPIRootListDTO(){
        Random ran = new Random();
        VagaAPIRootListDTO vagaAPIRootListDTO = new VagaAPIRootListDTO();
        vagaAPIRootListDTO.setId(ran.nextInt(1, 10));
        vagaAPIRootListDTO.setTitulo("Vaga Junior");
        vagaAPIRootListDTO.setStatus("Em Andamento");
        vagaAPIRootListDTO.setDataAbertura(new Date());
        return vagaAPIRootListDTO;
    }

    private CandidatoEntity getCandidatoEntity() {
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato.setNome("Aurora");
        candidato.setCpf("69805926109");
        candidato.setDataNascimento(LocalDate.parse("1978-07-03"));
        candidato.setTelefone("81927277790");
        candidato.setSenioridade(TipoSenioridade.ESPECIALISTA);
        candidato.setCargo("Desenvolvedora mobile - IOS");
        candidato.setCurriculoUrl("https://github.com");

        return candidato;
    }

    private VagaEntity getVagaEntity(){
        VagaEntity vaga = new VagaEntity();
        vaga.setIdVaga(1);
        vaga.setCandidatoEntities(new HashSet<>());
        vaga.getCandidatoEntities().add(getCandidatoEntity());
        return vaga;
    }

}
