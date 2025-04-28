package com.luan.gerenciador.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luan.gerenciador.model.Tarefa;
import com.luan.gerenciador.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.salvar(tarefa);
        return ResponseEntity.ok(novaTarefa);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        List<Tarefa> tarefas = tarefaService.listarTodas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Tarefa>> listarTarefasPendentes() {
        return ResponseEntity.ok(tarefaService.listarTarefasPendentes());
    }
    @PutMapping("/alocar/{tarefaId}/pessoa/{pessoaId}")
    public ResponseEntity<Tarefa> alocarPessoaMesmoDepartamento(
            @PathVariable Long tarefaId,
            @PathVariable Long pessoaId) {

        Tarefa tarefaAtualizada = tarefaService.alocarPessoaMesmoDp(tarefaId, pessoaId);
        return ResponseEntity.ok(tarefaAtualizada);
    }
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Tarefa> finalizarTarefa(@PathVariable Long id) {
        Tarefa tarefaFinalizada = tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok(tarefaFinalizada);
    }
    @PutMapping("/{tarefaId}/atribuir/{pessoaId}")
    public ResponseEntity<Tarefa> atribuirPessoa(
            @PathVariable Long tarefaId,
            @PathVariable Long pessoaId) {

        Tarefa tarefaAtualizada = tarefaService.atribuirPessoa(tarefaId, pessoaId);
        return ResponseEntity.ok(tarefaAtualizada);
    }
}
