package com.luan.gerenciador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luan.gerenciador.model.Tarefa;
import com.luan.gerenciador.repository.PessoaRepository;
import com.luan.gerenciador.repository.TarefaRepository;

@Service
public class TarefaService {
	
	@Autowired
	private final TarefaRepository tarefaRepository;
	@Autowired
	private final PessoaRepository pessoaRepository; 

	public TarefaService(TarefaRepository tarefaRepository, PessoaRepository pessoaRepository) {
		this.tarefaRepository = tarefaRepository;
		this.pessoaRepository = pessoaRepository;

	}

	public Tarefa salvar(Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}

	public List<Tarefa> listarTodas() {
		return tarefaRepository.findAll();
	}

	public Optional<Tarefa> buscarPorId(Long id) {
		return tarefaRepository.findById(id);
	}

	public Tarefa finalizarTarefa(Long id) {
		Tarefa tarefa = tarefaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));

		tarefa.setFinalizado(true);
		return tarefaRepository.save(tarefa);
	}
	public Tarefa atribuirPessoa(Long tarefaId, Long pessoaId) {
		Tarefa tarefa = tarefaRepository.findById(tarefaId)
				.orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + tarefaId));

		var pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id: " + pessoaId));

		tarefa.setPessoaAlocada(pessoa);
		return tarefaRepository.save(tarefa);
	}
	//Alocar pessoa do mesmo DP
	public Tarefa alocarPessoaMesmoDp(Long tarefaId, Long pessoaId) {
	    Tarefa tarefa = tarefaRepository.findById(tarefaId)
	            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + tarefaId));

	    var pessoa = pessoaRepository.findById(pessoaId)
	            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id: " + pessoaId));

	    if (!tarefa.getDepartamento().equalsIgnoreCase(pessoa.getDepartamento())) {
	        throw new RuntimeException("Pessoa e Tarefa são de departamentos diferentes!");
	    }

	    tarefa.setPessoaAlocada(pessoa);
	    return tarefaRepository.save(tarefa);
	}
	//Pendentes 
	public List<Tarefa> listarTarefasPendentes() {
	    return tarefaRepository.findAll().stream()
	            .filter(t -> t.getPessoaAlocada() == null)
	            .sorted((t1, t2) -> t1.getPrazo().compareTo(t2.getPrazo()))
	            .limit(3)
	            .toList();
	}
}
