package com.decsef.mcsvc_cursos.services;

import com.decsef.mcsvc_cursos.models.entity.Curso;
import com.decsef.mcsvc_cursos.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursosService {
    List<Curso>allCursos();
    Optional<Curso> getCursoById(Long id);
    Optional<Curso> getCursoConDetalleUsuarios(Long id);
    Curso saveCurso(Curso curso);
    void deleteCurso(Long id);
    void deleteCursoUsuario(Long id);
    Optional<Usuario> asingnarTransitionalUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearTransitionalUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarTransitionalUsuario(Usuario usuario, Long cursoId);


}
