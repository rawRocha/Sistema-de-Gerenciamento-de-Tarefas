package com.treina.recife.sgp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Projeto;

public interface ProjetoService {
    Page<Projeto> getProjetos(Pageable pageable);

    Optional<Projeto> getProjetoById(long projectId);

    Projeto createProjeto(Projeto projeto);

    Projeto updateProjeto(Projeto projeto);

    void deleteProjeto(long projectId);
}
