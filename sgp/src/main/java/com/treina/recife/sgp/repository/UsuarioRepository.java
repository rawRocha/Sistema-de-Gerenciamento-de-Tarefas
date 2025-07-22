package com.treina.recife.sgp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findBycpfAndEmail(String cpf, String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
