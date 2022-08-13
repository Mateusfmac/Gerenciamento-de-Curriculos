package com.dbc.curriculo.repository;

import com.dbc.curriculo.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
    Optional<LoginEntity> findByEmail(String email);
}
