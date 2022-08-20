package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.CandidatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {

    Optional<CandidatoEntity> findByCpf(String cpf);

    Optional<CandidatoEntity> findByTelefone(String telefone);

}
