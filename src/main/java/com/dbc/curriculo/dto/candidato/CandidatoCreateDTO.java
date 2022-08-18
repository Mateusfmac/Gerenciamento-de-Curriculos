package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NonNull
    private LocalDate dataNascimento;

    @NotBlank
    @Size(max = 14, message = "O número deve conter no máximo 14 caracteres")
    private String telefone;

    @NotNull
    private TipoSenioridade senioridade;

    @NotBlank
    private String cargo;

    @NotNull
    private EnderecoCreateDTO endereco;

    private List<EscolaridadeCreateDTO> escolaridades;

    private List<ExperienciaCreateDTO> experiencias;

}
