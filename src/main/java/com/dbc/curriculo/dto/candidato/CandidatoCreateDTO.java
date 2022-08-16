package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatoCreateDTO {


    private String nome;


    private String cpf;


    private LocalDate dataNascimento;


    private String telefone;


    private TipoSenioridade senioridade;


    private String cargo;


    private EnderecoCreateDTO endereco;


    private List<EscolaridadeCreateDTO> escolaridades;


    private List<ExperienciaCreateDTO> experiencias;

    private MultipartFile curriculoUrl;

}
