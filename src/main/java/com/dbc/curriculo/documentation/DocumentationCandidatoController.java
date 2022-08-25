package com.dbc.curriculo.documentation;

import com.dbc.curriculo.anotations.Response;
import com.dbc.curriculo.dto.PageDTO;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoUpdateDTO;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.CandidatoValidarException;
import com.dbc.curriculo.exceptions.S3Exception;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

public interface DocumentationCandidatoController {

    @Operation(
            summary = "Busca uma lista de candidatos..",
            description = "Esse endpoint retorna todos os candidatos salvo no banco de dados."
    )
    @Response
    public ResponseEntity<List<CandidatoDadosDTO>> getListCandidato();

    @Operation(
            summary = "Busca um candidato por ID",
            description = "Esse endpoint retorna um candidato específico do banco de dados." +
                    " Caso não exista retornará um error."

    )
    @Response
    public ResponseEntity<CandidatoDTO> getCandidato(
            @PathVariable("idCandidato") Integer idCandidato) throws CandidatoException;

    @Operation(
            summary = "Retorna uma lista de candidato paginada."
    )
    @Response
    public ResponseEntity<PageDTO<CandidatoDadosDTO>> paginacaoCandidato
            (@RequestParam(required = false, defaultValue = "1") Integer pagina ,
             @RequestParam(required = false, defaultValue = "20") Integer qtRegistro);


    @Operation(
            summary = "Salva um candidato no banco de dados.",
            description = "Esse endpoint salva um candidato no banco de dados. " +
                    "O CPF e Telefone devem ser únicos, as listas de experiências e escolaridades podem ser vazias"

    )
    @Response
    public ResponseEntity<CandidatoDadosDTO> saveCandidato(
            @Valid @RequestPart("candidato") CandidatoCreateDTO candidato,
            @Valid @NotNull @RequestPart("documento") MultipartFile documento
    )
            throws S3Exception, IOException, CandidatoValidarException;

    @Operation(
            summary = "Atualizar dados candidato.",
            description = "Esse endpoint atualiza os dados de um determinado candidato. " +
                    "Caso não sejam passados experiências e escolaridades, será removido " +
                    "os já existentes, deixando assim o usuário sem escolaridade e experiência."

    )
    @Response
    public ResponseEntity<CandidatoDTO> updateCandidato(@Valid @RequestBody CandidatoUpdateDTO candidatoUpdateDTO) throws CandidatoException;

    @Operation(
            summary = "Atualizar curriculo candidato.",
            description = "Esse endpoint atualiza o curriculo do candidato."

    )
    @Response
    public ResponseEntity<String> updateCandidatoDocumento(
            @PathVariable Integer idCandidato,
            @Valid @NotNull @RequestPart MultipartFile documento) throws CandidatoException, S3Exception, IOException;


    @Operation(
            summary = "Remover candidato.",
            description = "Esse endpoint remove um candidato do banco de dados."
    )
    @Response
    public void delete(@PathVariable Integer idUsuario) throws CandidatoException;

}
