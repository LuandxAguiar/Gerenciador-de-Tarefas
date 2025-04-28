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
import com.luan.gerenciador.model.Tarefa;
import com.luan.gerenciador.repository.PessoaRepository;
import com.luan.gerenciador.repository.TarefaRepository;

class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTarefa() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Corrigir Bugs");
        tarefa.setDepartamento("TI");

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        // Act
        Tarefa salva = tarefaService.salvar(tarefa);

        // Assert
        assertThat(salva.getTitulo()).isEqualTo("Corrigir Bugs");
        assertThat(salva.getDepartamento()).isEqualTo("TI");
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveListarTarefas() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Corrigir Bugs");

        when(tarefaRepository.findAll()).thenReturn(Collections.singletonList(tarefa));

        // Act
        List<Tarefa> tarefas = tarefaService.listarTodas();

        // Assert
        assertThat(tarefas).hasSize(1);
        assertThat(tarefas.get(0).getTitulo()).isEqualTo("Corrigir Bugs");
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void deveFinalizarTarefa() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setFinalizado(false);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        // Act
        Tarefa finalizada = tarefaService.finalizarTarefa(1L);

        // Assert
        assertThat(finalizada.getFinalizado()).isTrue();
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveAlocarPessoaNoMesmoDepartamento() {
        // Arrange
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDepartamento("TI");

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setDepartamento("TI");

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        // Act
        Tarefa tarefaAlocada = tarefaService.alocarPessoaMesmoDp(1L, 1L);

        // Assert
        assertThat(tarefaAlocada.getPessoaAlocada()).isEqualTo(pessoa);
        verify(tarefaRepository, times(1)).save(tarefa);
    }
}
