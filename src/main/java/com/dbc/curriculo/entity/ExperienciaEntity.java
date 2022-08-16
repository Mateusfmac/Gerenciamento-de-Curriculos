package com.dbc.curriculo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "experiencia")
public class ExperienciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_experiencia")
    @SequenceGenerator(name = "seq_experiencia", sequenceName = "seq_experiencia", allocationSize = 1)
    @Column(name = "id_experiencia")
    private Integer idExperiencia;
    
    @Column(name = "instituicao")
    private String instituicao;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "cargo")
    private String cargo;
    
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim")
    private LocalDate dataFim;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_candidato", referencedColumnName = "id_candidato")
    private CandidatoEntity candidatoEntity;

    @Override
    public String toString() {
        return "ExperienciaEntity{" +
                "idExperiencia=" + idExperiencia +
                ", instituicao='" + instituicao + '\'' +
                ", descricao='" + descricao + '\'' +
                ", cargo='" + cargo + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                '}';
    }
}
