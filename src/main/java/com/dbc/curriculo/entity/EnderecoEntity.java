package com.dbc.curriculo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "endereco")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    @SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1)
    @Column(name = "id_endereco")
    private Integer idEndereco;
    
    @Column(name = "numero")
    private Integer numero;
    
    @Column(name = "logradouro")
    private String logradouro;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cep")
    private String cep;

    @Column(name = "estado")
    private String estado;
    
    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "enderecoEntity")
    private CandidatoEntity candidatoEntity;

    @Override
    public String toString() {
        return "EnderecoEntity{" +
                "idEndereco=" + idEndereco +
                ", numero=" + numero +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                ", candidatoEntity=" + candidatoEntity +
                '}';
    }
}
