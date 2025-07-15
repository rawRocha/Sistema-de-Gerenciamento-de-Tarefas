package com.treina.recife.sgp.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UserService usuarioService;

    Logger logger = LogManager.getLogger(UsuarioController.class);

    public UsuarioController(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Usuario> users = usuarioService.getUsuarios(pageable);

        if (users.isEmpty()) {
            logger.info("Ainda não há usuários cadastrados.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "usuarioId") long usuarioId) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(usuarioId);

        if (usuario.isEmpty()) {
            logger.warn("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } else {
            logger.info(usuario.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        }
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "usuarioId") long usuarioId) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(usuarioId);

        if (usuario.isEmpty()) {
            logger.warn("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } else {
            usuarioService.deleteUsuario(usuarioId);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
        }
    }

}
