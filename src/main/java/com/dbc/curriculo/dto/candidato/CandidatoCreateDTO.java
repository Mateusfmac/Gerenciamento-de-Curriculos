package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoCreateDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    private LocalDate dataNascimento;

    @NotBlank
    private String telefone;

    @NotNull
    private TipoSenioridade senioridade;

    @NotBlank
    private String cargo;

    @NotEmpty
    private EnderecoCreateDTO endereco;

    @NotEmpty
    private List<EscolaridadeCreateDTO> escolaridades;

    @NotEmpty
    private List<ExperienciaCreateDTO> experiencias;

    @NotBlank
    private String curriculoUrl;

}
