package com.decsef.mcsvc_cursos.repositories;

import com.decsef.mcsvc_cursos.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursosRepository extends CrudRepository<Curso, Long> {
}
