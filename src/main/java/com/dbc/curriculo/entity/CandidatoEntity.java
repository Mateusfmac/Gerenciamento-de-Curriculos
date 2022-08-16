package com.dbc.curriculo.entity;

import com.dbc.curriculo.enums.TipoSenioridade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "candidato")
public class CandidatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_candidato")
    @SequenceGenerator(name = "seq_candidato", sequenceName = "seq_candidato", allocationSize = 1)
    @Column(name = "id_candidato")
    private Integer idCandidato;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "cpf")
    private String cpf;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "senioridade")
    private TipoSenioridade senioridade;
    
    @Column(name = "cargo")
    private String cargo;
    
    @Column(name = "curriculo_url")
    private String curriculoUrl;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "candidatoEntity")
    private Set<EscolaridadeEntity> escolaridadeEntities;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "candidatoEntity")
    private Set<ExperienciaEntity> experienciaEntities;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vaga_candidato",
            joinColumns = @JoinColumn(name = "id_candidato"),
            inverseJoinColumns = @JoinColumn(name = "id_vagas")
    )
    private Set<VagaEntity> vagaEntities;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private EnderecoEntity enderecoEntity;
}
