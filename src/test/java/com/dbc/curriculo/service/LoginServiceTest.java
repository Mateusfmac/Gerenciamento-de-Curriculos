package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.entity.LoginEntity;
import com.dbc.curriculo.exceptions.LoginException;
import com.dbc.curriculo.repository.LoginRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private LoginRepository loginRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(loginService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarGetIdUsuarioLogadoComSucesso() throws LoginException {
        LoginEntity loginEntity =getLoginEntity();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(123, "senha");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(loginRepository.findById(anyInt())).thenReturn(Optional.of(loginEntity));

        LoginDTO loginDTO = loginService.getUsuarioLogado();

        assertNotNull(loginDTO);
        assertEquals(loginEntity.getEmail(), loginDTO.getEmail());
        assertEquals(loginEntity.getIdLogin(), loginDTO.getIdLogin());
    }

    @Test
    public void deveTestarBuscarLoginPorEmail() {
       LoginEntity loginEntity = getLoginEntity();


        when(loginRepository.findByEmail(anyString())).thenReturn(Optional.of(loginEntity));

        Optional<LoginEntity> loginEntityOptional = loginService.buscarLoginPorEmail(loginEntity.getEmail());

        assertNotNull(loginEntityOptional);
        assertEquals(loginEntity.getEmail(), loginEntityOptional.get().getEmail());
        assertEquals(loginEntity.getIdLogin(), loginEntityOptional.get().getIdLogin());
    }

    public static LoginEntity getLoginEntity() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setIdLogin(1);
        loginEntity.setSenha("123");
        loginEntity.setEmail("teste@dbccompany.com.br");
        loginEntity.setEnable(1);
        return loginEntity;
    }
}
