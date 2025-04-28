package com.luan.gerenciador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartamentoDTO {
    private String departamento;
    private Long totalPessoas;
    private Long totalTarefas;
}
