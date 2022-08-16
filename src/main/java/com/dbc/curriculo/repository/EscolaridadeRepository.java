package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.EscolaridadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaridadeRepository extends JpaRepository<EscolaridadeEntity, Integer> {

}