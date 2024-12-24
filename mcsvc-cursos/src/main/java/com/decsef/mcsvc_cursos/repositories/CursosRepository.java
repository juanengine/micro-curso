package com.decsef.mcsvc_cursos.repositories;

import com.decsef.mcsvc_cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursosRepository extends CrudRepository<Curso, Long> {
}
