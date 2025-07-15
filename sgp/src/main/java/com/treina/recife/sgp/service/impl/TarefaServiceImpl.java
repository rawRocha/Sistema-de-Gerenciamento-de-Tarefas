package com.treina.recife.sgp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.repository.TarefaRepository;
import com.treina.recife.sgp.service.TarefaService;

public class TarefaServiceImpl implements TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Override
    public Page<Tarefa> getTarefas(Pageable pageable) {
        return tarefaRepository.findAll(pageable);
    }

    @Override
    public Optional<Tarefa> getTarefaById(long taskId) {
        return tarefaRepository.findById(taskId);
    }

    @Override
    public Tarefa createTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public void deleteTarefaById(long taskId) {
        tarefaRepository.deleteById(taskId);
    }

    @Override
    public Tarefa updateTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public List<Tarefa> findByProjetoProjectId(long projectId) {
        return tarefaRepository.findByProjetoProjectId(projectId);
    }

}
