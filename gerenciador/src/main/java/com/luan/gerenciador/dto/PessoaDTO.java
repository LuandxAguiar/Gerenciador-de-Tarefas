package com.luan.gerenciador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PessoaDTO {
    private String nome;
    private String departamento;
    private Integer totalHorasGastas;
}
