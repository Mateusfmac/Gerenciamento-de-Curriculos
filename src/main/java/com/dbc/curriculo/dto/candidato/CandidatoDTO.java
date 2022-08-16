package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatoDTO extends CandidatoCreateDTO {

    private Integer idCandidato;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private String telefone;

    private TipoSenioridade senioridade;

    private String cargo;

    private MultipartFile curriculoUrl;

    private EnderecoCreateDTO endereco;

    private List<EscolaridadeDTO> escolaridade;

    private List<ExperienciaDTO> experiencia;

}
