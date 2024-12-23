package com.decsef.usuarios_mcrsvc.repositories;

import com.decsef.usuarios_mcrsvc.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
}
