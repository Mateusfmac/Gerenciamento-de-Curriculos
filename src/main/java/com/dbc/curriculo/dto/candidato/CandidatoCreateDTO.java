package com.dbc.curriculo.dto.candidato;

import com.dbc.curriculo.dto.endereco.EnderecoCreateDTO;
import com.dbc.curriculo.dto.escolaridade.EscolaridadeCreateDTO;
import com.dbc.curriculo.dto.experiencia.ExperienciaCreateDTO;
import com.dbc.curriculo.dto.vaga.VagaCreateDTO;
import com.dbc.curriculo.enums.TipoSenioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoCreateDTO {
    
    private String nome;
    
    private String cpf;
    
    private LocalDate dataNascimento;
    
    private String telefone;
    
    private TipoSenioridade senioridade;
    
    private String cargo;
   
    private String curriculoUrl;
    
    private EnderecoCreateDTO enderecoEntity;
    
    private List<EscolaridadeCreateDTO> escolaridadeEntities;
    
    private List<ExperienciaCreateDTO> experienciaEntities;
    
    private List<VagaCreateDTO> vagaEntities;
}
