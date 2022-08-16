package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.ExperienciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienciaRepository extends JpaRepository<ExperienciaEntity, Integer> {

}