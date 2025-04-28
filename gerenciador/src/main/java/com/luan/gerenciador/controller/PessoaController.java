package com.luan.gerenciador.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luan.gerenciador.dto.DepartamentoDTO;
import com.luan.gerenciador.dto.PessoaDTO;
import com.luan.gerenciador.dto.PessoaGastosDTO;
import com.luan.gerenciador.model.Pessoa;
import com.luan.gerenciador.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.salvar(pessoa);
        return ResponseEntity.ok(novaPessoa);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaService.atualizar(id, pessoaAtualizada);
        return ResponseEntity.ok(pessoa);
    }
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoasComHoras() {
        return ResponseEntity.ok(pessoaService.listarPessoasComHoras());
    }

	@GetMapping("/gastos")
	public ResponseEntity<List<PessoaGastosDTO>> buscarPessoasGastos(@RequestParam String nome,
			@RequestParam String inicio, @RequestParam String fim) {
		var inicioDate = LocalDate.parse(inicio);
		var fimDate = LocalDate.parse(fim);
		return ResponseEntity.ok(pessoaService.buscarPessoasGastos(nome, inicioDate, fimDate));
	}
	@GetMapping("/departamentos")
	public ResponseEntity<List<DepartamentoDTO>> listarDepartamentos() {
	    return ResponseEntity.ok(pessoaService.listarDepartamentos());
	}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
}
