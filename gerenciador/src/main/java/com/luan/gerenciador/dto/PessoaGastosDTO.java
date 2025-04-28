package com.luan.gerenciador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PessoaGastosDTO {
    private String nome;
    private Double mediaHorasPorTarefa;
}