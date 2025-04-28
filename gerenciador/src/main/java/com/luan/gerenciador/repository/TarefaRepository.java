package com.luan.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.gerenciador.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
