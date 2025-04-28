package com.luan.gerenciador.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luan.gerenciador.model.Pessoa;
import com.luan.gerenciador.repository.PessoaRepository;

class PessoaServiceTest {

	@Mock
	private PessoaRepository pessoaRepository;

	@InjectMocks
	private PessoaService pessoaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveSalvarPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Luan");
		pessoa.setDepartamento("TI");
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		Pessoa salva = pessoaService.salvar(pessoa);
		assertThat(salva.getNome()).isEqualTo("Luan");
		assertThat(salva.getDepartamento()).isEqualTo("TI");
		verify(pessoaRepository, times(1)).save(pessoa);
	}

	@Test
	void deveListarPessoas() {
		// Arrange
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Luan");
		pessoa.setDepartamento("TI");
		when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(pessoa));
		List<Pessoa> pessoas = pessoaService.listarTodas();
		assertThat(pessoas).hasSize(1);
		assertThat(pessoas.get(0).getNome()).isEqualTo("Luan");
		verify(pessoaRepository, times(1)).findAll();
	}

	@Test
	void deveAtualizarPessoa() {
		// Arrange
		Pessoa pessoaExistente = new Pessoa();
		pessoaExistente.setId(1L);
		pessoaExistente.setNome("Luan");
		pessoaExistente.setDepartamento("TI");
		Pessoa pessoaAtualizada = new Pessoa();
		pessoaAtualizada.setNome("Luan Alterado");
		pessoaAtualizada.setDepartamento("RH");
		when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaExistente));
		when(pessoaRepository.save(any())).thenReturn(pessoaExistente);
		Pessoa pessoa = pessoaService.atualizar(1L, pessoaAtualizada);
		assertThat(pessoa.getNome()).isEqualTo("Luan Alterado");
		assertThat(pessoa.getDepartamento()).isEqualTo("RH");
		verify(pessoaRepository, times(1)).save(pessoaExistente);
	}

	@Test
	void deveDeletarPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(1L);
		when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
		pessoaService.deletar(1L);
		verify(pessoaRepository, times(1)).delete(pessoa);
	}
}
