package com.dbc.curriculo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "escolaridade")
public class EscolaridadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_escolaridade")
    @SequenceGenerator(name = "seq_escolaridade", sequenceName = "seq_escolaridade", allocationSize = 1)
    @Column(name = "id_escolaridade")
    private Integer idEscolaridade;
    
    @Column(name = "instituicao")
    private String instituicao;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "nivel")
    private String nivel;
    
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
        return "EscolaridadeEntity{" +
                "idEscolaridade=" + idEscolaridade +
                ", instituicao='" + instituicao + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nivel='" + nivel + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                '}';
    }
}
