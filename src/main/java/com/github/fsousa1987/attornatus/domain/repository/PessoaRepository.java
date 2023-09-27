package com.github.fsousa1987.attornatus.domain.repository;

import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
}
