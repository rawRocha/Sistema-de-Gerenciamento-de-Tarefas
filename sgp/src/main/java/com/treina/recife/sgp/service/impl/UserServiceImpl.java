package com.treina.recife.sgp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.repository.UsuarioRepository;
import com.treina.recife.sgp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> getUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> getUsuarioById(long userId) {
        return usuarioRepository.findById(userId);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(long userId) {
        usuarioRepository.deleteById(userId);
    }

    @Override
    public boolean isEmailAlredyTaken(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean isCpfAlredyTaken(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

}
