package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.CandidatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<CandidatoEntity, Integer> {

}
