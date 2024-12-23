package com.decsef.mcsvc_cursos.services;

import com.decsef.mcsvc_cursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursosService {
    List<Curso>allCursos();
    Optional<Curso> getCursoById(Long id);
    Curso saveCurso(Curso curso);
    void deleteCurso(Long id);

}
