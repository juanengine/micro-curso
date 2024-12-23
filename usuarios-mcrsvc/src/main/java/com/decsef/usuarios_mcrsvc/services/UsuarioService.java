package com.decsef.usuarios_mcrsvc.services;

import com.decsef.usuarios_mcrsvc.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listarTodos();
    Optional<Usuario> buecarUsuarioById(Long id);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
}
