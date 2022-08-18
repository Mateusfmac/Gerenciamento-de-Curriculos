package com.dbc.curriculo.documentation;

import com.dbc.curriculo.anotations.MagiaResponse;
import com.dbc.curriculo.dto.candidato.CandidatoCreateDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDTO;
import com.dbc.curriculo.dto.candidato.CandidatoDadosDTO;
import com.dbc.curriculo.dto.candidato.CandidatoUpdateDTO;
import com.dbc.curriculo.exceptions.CandidatoException;
import com.dbc.curriculo.exceptions.S3Exception;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DocumentationCandidatoController {

    @Operation(
            summary = "Busca uma lista de candidatos..",
            description = "Esse endpoint retorna todos os candidatos salvo no banco de dados."
    )
    @MagiaResponse
    public ResponseEntity<List<CandidatoDadosDTO>> getListCandidato();

    @Operation(
            summary = "Busca um candidato por ID",
            description = "Esse endpoint retorna um candidato específico do banco de dados." +
                    " Caso não exista retornará um error."

    )
    @MagiaResponse
    public ResponseEntity<CandidatoDTO> getCandidato(
            @PathVariable("idCandidato") Integer idCandidato) throws CandidatoException;

    @Operation(
            summary = "Salva um candidato no banco de dados.",
            description = "Esse endpoint salva um candidato no banco de dados. " +
                    "O CPF e Telefone devem ser únicos, as listas de experiências e escolaridades podem ser vazias"

    )
    @MagiaResponse
    public ResponseEntity<CandidatoDadosDTO> save(
            @Valid @RequestPart("candidato") CandidatoCreateDTO candidato,
            @Valid @NotNull @RequestPart("documento") MultipartFile documento)
            throws S3Exception, CandidatoException;

    @Operation(
            summary = "Atualizar dados candidato.",
            description = "Esse endpoint atualiza os dados de um determinado candidato. " +
                    "Caso não sejam passados experiências e escolaridades, será removido " +
                    "os já existentes, deixando assim o usuário sem escolaridade e experiência."

    )
    @MagiaResponse
    public ResponseEntity<CandidatoDTO> updateCandidato(@Valid @RequestBody CandidatoUpdateDTO candidatoUpdateDTO) throws CandidatoException;

    @Operation(
            summary = "Remover candidato.",
            description = "Esse endpoint remove um candidato do banco de dados."
    )
    @MagiaResponse
    public void delete(@PathVariable Integer idUsuario);

}
