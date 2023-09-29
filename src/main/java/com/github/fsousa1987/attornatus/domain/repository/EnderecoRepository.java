package com.github.fsousa1987.attornatus.domain.repository;

import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    List<EnderecoEntity> findByPessoaId(Long pessoaId);

}
