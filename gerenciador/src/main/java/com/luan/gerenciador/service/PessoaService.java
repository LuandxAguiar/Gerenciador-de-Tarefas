package com.luan.gerenciador.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.luan.gerenciador.dto.DepartamentoDTO;
import com.luan.gerenciador.dto.PessoaDTO;
import com.luan.gerenciador.dto.PessoaGastosDTO;
import com.luan.gerenciador.model.Pessoa;
import com.luan.gerenciador.model.Tarefa;
import com.luan.gerenciador.repository.PessoaRepository;
import com.luan.gerenciador.repository.TarefaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;
    public PessoaService(PessoaRepository pessoaRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setDepartamento(pessoaAtualizada.getDepartamento());

        return pessoaRepository.save(pessoa);
    }
    public void deletar(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        pessoaRepository.delete(pessoa);
    }
    //listar pessoa com horas 
    public List<PessoaDTO> listarPessoasComHoras() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> {
                    int totalHoras = pessoa.getTarefas() != null
                            ? pessoa.getTarefas().stream()
                                .mapToInt(t -> t.getDuracao() != null ? t.getDuracao() : 0)
                                .sum()
                            : 0;
                    return new PessoaDTO(pessoa.getNome(), pessoa.getDepartamento(), totalHoras);
                })
                .toList();
    }
    //Gastos de hora
    public List<PessoaGastosDTO> buscarPessoasGastos(String nome, LocalDate inicio, LocalDate fim) {
        return pessoaRepository.findAll().stream()
                .filter(pessoa -> pessoa.getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(pessoa -> {
                    var tarefasFiltradas = pessoa.getTarefas().stream()
                            .filter(tarefa -> tarefa.getPrazo() != null &&
                                    !tarefa.getPrazo().isBefore(inicio) &&
                                    !tarefa.getPrazo().isAfter(fim))
                            .toList();

                    double mediaHoras = tarefasFiltradas.isEmpty()
                            ? 0
                            : tarefasFiltradas.stream()
                                .mapToInt(t -> t.getDuracao() != null ? t.getDuracao() : 0)
                                .average()
                                .orElse(0.0);

                    return new PessoaGastosDTO(pessoa.getNome(), mediaHoras);
                })
                .toList();
    }
    // Lista de Dp
    public List<DepartamentoDTO> listarDepartamentos() {
        var pessoas = pessoaRepository.findAll();
        var tarefas = tarefaRepository.findAll();

        Map<String, Long> pessoasPorDepartamento = pessoas.stream()
                .collect(Collectors.groupingBy(Pessoa::getDepartamento, Collectors.counting()));

        Map<String, Long> tarefasPorDepartamento = tarefas.stream()
                .collect(Collectors.groupingBy(Tarefa::getDepartamento, Collectors.counting()));

        Set<String> departamentos = new HashSet<>();
        departamentos.addAll(pessoasPorDepartamento.keySet());
        departamentos.addAll(tarefasPorDepartamento.keySet());

        return departamentos.stream()
                .map(dep -> new DepartamentoDTO(
                        dep,
                        pessoasPorDepartamento.getOrDefault(dep, 0L),
                        tarefasPorDepartamento.getOrDefault(dep, 0L)
                ))
                .toList();
    }
}
