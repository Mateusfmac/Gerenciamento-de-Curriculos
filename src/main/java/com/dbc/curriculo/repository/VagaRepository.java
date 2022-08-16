package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

}