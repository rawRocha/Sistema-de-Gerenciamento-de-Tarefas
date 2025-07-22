package com.treina.recife.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.treina.recife.sgp.constants.StatusUsuario;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {
    private long userId;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private StatusUsuario status;
}
