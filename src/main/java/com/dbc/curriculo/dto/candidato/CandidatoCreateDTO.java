package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CandidatoCreateDTO {

    @Schema(example = "Mario")
    @NotBlank
    private String nome;

    @NotBlank
    @Schema(example = "87932766052", description = "O CPF deve ser válido, buscar na 4Devs para teste.")
    @CPF(message = "O CPF deve ser válido.")
    private String cpf;

    @NotNull
    @Past(message = "data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @Pattern(regexp = "\\d{11}", message = "O telefone deve ser composto pelo DDD seguido do número, sem simbolos. " +
            "Ex. DDDDDDDDDDD.")
    @NotBlank
    @Size(max = 11, min = 11, message = "O número deve conter 11 dígitos.")
    private String telefone;

    @NotNull
    private TipoSenioridade senioridade;

    @NotBlank
    private String cargo;

    @NotNull
    @Valid
    private EnderecoCreateDTO endereco;

    @Valid
    private List<EscolaridadeCreateDTO> escolaridades;

    @Valid
    private List<ExperienciaCreateDTO> experiencias;

}
