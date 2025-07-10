package com.treina.recife.sgp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Usuario;

public interface UserService {
    Page<Usuario> getUsuarios(Pageable pageable);

    Optional<Usuario> getUsuarioById(long userId);

    Usuario createUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    void deleteUsuario(long userId);

    boolean isEmailAlredyTaken(String email);
}
