package com.treina.recife.sgp.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.dto.ProjetoDto;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.UserService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UserService usuarioService;

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(ProjetoController.class);

    public ProjetoController(ProjetoService projetoService, UserService usuarioService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Projeto>> getProjetos(
            @PageableDefault(sort = "projectId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Projeto> projetos = projetoService.getProjetos(pageable);

        if (projetos.isEmpty()) {
            logger.info("Lista de projetos está vazia.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjetoById(@PathVariable(value = "projectId") long projectId) {
        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        } else {
            logger.info(projeto.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(projeto.get());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProjeto(@RequestBody ProjetoDto projetoDto) {

        Projeto novoProjeto = new Projeto();

        novoProjeto.setNome(projetoDto.getNome());
        novoProjeto.setDescricao(projetoDto.getDescricao());
        novoProjeto.setDataInicio(projetoDto.getDataInicio());
        novoProjeto.setDataConclusao(projetoDto.getDataConclusao());
        novoProjeto.setStatus(projetoDto.getStatus());

        projetoService.createProjeto(novoProjeto);

        logger.info("Projeto {} cadastrado com sucesso!", novoProjeto.getProjectId());

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "projectId") long projectId) {
        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        } else {
            projetoService.deleteProjeto(projectId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Projeto deletado com sucesso!");
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProjeto(@PathVariable(value = "projectId") long projectId,
            @RequestBody ProjetoDto projetoDto) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("projeto não encontrado");
        } else {
            Projeto projetoAtualizado = projeto.get();

            projetoAtualizado.setNome(projetoDto.getNome());
            projetoAtualizado.setDescricao(projetoDto.getDescricao());
            projetoAtualizado.setDataInicio(projetoDto.getDataInicio());
            projetoAtualizado.setDataConclusao(projetoDto.getDataConclusao());
            projetoAtualizado.setStatus(projetoDto.getStatus());

            projetoService.updateProjeto(projetoAtualizado);

            logger.info("Projeto {} atualizado com sucesso!", projetoAtualizado.getProjectId());

            return ResponseEntity.status(HttpStatus.CREATED).body(projetoAtualizado);
        }
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "projectId") long projectId,
            @RequestBody Map<String, String> body) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }

        String statusBody = body.get("status");

        if (statusBody == null) {
            return ResponseEntity.badRequest().body("Status é obrigatório.");
        }

        StatusProjeto novoStatus;

        try {
            novoStatus = StatusProjeto.valueOf(statusBody.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Status inválidos, valores permitidos: ATIVO, CONCLUIDO, CANCELADO");
        }

        Projeto projetoAtualizado = projeto.get();
        projetoAtualizado.setStatus(novoStatus);

        projetoService.updateProjeto(projetoAtualizado);
        logger.info("Projeto atualizado com sucesso!");

        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
    }

    @PatchMapping("/{projectId}/responsavel")
    public ResponseEntity<Object> atualizarResponsavel(@PathVariable(value = "projectId") long projectId,
            @RequestBody UsuarioDto usuarioDto) {

        Optional<Projeto> projetoOptional = projetoService.getProjetoById(projectId);

        if (projetoOptional.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }

        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(usuarioDto.getUserId());

        if (usuarioOptional.isEmpty()) {
            logger.warn("Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Projeto projeto = projetoOptional.get();
        Usuario usuario = usuarioOptional.get();

        projeto.setResponsavel(usuario);

        Projeto projetoAtualizado = projetoService.updateProjeto(projeto);

        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
    }
}
