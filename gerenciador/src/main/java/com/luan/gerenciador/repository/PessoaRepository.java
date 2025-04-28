package com.luan.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.gerenciador.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
