package com.dbc.curriculo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vaga")
public class VagaEntity {
    @Id
    @Column(name = "id_vagas")
    private Integer idVaga;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vaga_candidato",
            joinColumns = @JoinColumn(name = "id_vagas"),
            inverseJoinColumns = @JoinColumn(name = "id_candidato")
    )
    private Set<CandidatoEntity> candidatoEntities;
}
