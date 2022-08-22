package com.dbc.curriculo.service;

import com.dbc.curriculo.dto.login.LoginCredenciaisDTO;
import com.dbc.curriculo.dto.login.LoginDTO;
import com.dbc.curriculo.dto.token.TokenDTO;
import com.dbc.curriculo.entity.LoginEntity;
import com.dbc.curriculo.exceptions.LoginException;
import com.dbc.curriculo.repository.LoginRepository;
import com.dbc.curriculo.security.TokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

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
    public void deveTestarGetUsuarioLogadoComSucesso() throws LoginException {
        LoginEntity loginEntity = getLoginEntity();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(12345678, "senha");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        when(loginRepository.findById(anyInt())).thenReturn(Optional.of(loginEntity));

        LoginDTO loginDTO = loginService.getUsuarioLogado();

        assertNotNull(loginDTO);
        assertEquals(loginEntity.getEmail(), loginDTO.getEmail());
        assertEquals(loginEntity.getIdLogin(), loginDTO.getIdLogin());
    }

    @Test(expected = LoginException.class)
    public void deveTestarGetUsuarioLogadoSemSucessoComException() throws LoginException {
        LoginEntity loginEntity = getLoginEntity();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        loginService.getUsuarioLogado();

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

    @Test(expected = LoginException.class)
    public void deveTestarVeriricarEmailJaCadastradoSemSucesso() throws LoginException {
        Optional<LoginEntity> loginEntity = Optional.of(getLoginEntity());
        String email = "teste1@dbccompany.com.br";
        when(loginService.buscarLoginPorEmail(anyString())).thenReturn(loginEntity);

        loginService.verificarSeEmailJaEstaCadastrado(loginEntity.get().getEmail());
    }

    @Test
    public void deveTestarCreateLoginComSucesso() throws LoginException {
        LoginEntity loginEntity = getLoginEntity();

        when(loginRepository.save(any(LoginEntity.class))).thenReturn(loginEntity);

        LoginDTO loginDTO = loginService.createLoginUser(getLoginCredenciaisDTO());

        assertNotNull(loginDTO);
        assertEquals(loginDTO.getEmail(), loginEntity.getEmail());
    }

    @Test
    public void deveTestarAutenticarToken() {
        LoginCredenciaisDTO loginCredenciaisDTO = getLoginCredenciaisDTO();
        LoginEntity loginEntity = getLoginEntity();
        Authentication authentication = mock(Authentication.class);
        String tokenString = "eyJhbGciOiJIUzI1NiJ9";

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginCredenciaisDTO.getEmail(),
                        loginCredenciaisDTO.getSenha());

        when(authenticationManager.authenticate(authenticationToken))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(loginEntity);
        when(tokenService.getToken(loginEntity)).thenReturn(tokenString);

        TokenDTO tokenDTO =
                loginService.autenticarTokenLogin(
                loginCredenciaisDTO,
                authenticationManager);

        assertNotNull(tokenDTO);
        assertEquals(tokenString,tokenDTO.getToken());


    }

    private LoginEntity getLoginEntity() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setIdLogin(1);
        loginEntity.setSenha("12345678");
        loginEntity.setEmail("teste@dbccompany.com.br");
        loginEntity.setEnable(1);
        return loginEntity;
    }

    private LoginCredenciaisDTO getLoginCredenciaisDTO() {
        LoginCredenciaisDTO loginCredenciaisDTO = new LoginCredenciaisDTO();
        loginCredenciaisDTO.setEmail("teste@dbccompany.com.br");
        loginCredenciaisDTO.setSenha("12345678");
        return loginCredenciaisDTO;
    }

}
