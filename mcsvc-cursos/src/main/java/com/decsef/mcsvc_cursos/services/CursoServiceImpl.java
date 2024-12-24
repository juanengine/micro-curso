package com.decsef.mcsvc_cursos.services;

import com.decsef.mcsvc_cursos.models.entity.Curso;
import com.decsef.mcsvc_cursos.models.entity.Usuario;
import com.decsef.mcsvc_cursos.repositories.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursosService {

    @Autowired
    private CursosRepository cursosRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> allCursos() {
        return (List<Curso>) cursosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> getCursoById(Long id) {
        return cursosRepository.findById(id);
    }

    @Override
    public Curso saveCurso(Curso curso) {
        return cursosRepository.save(curso);
    }

    @Override
    public void deleteCurso(Long id) {

        cursosRepository.deleteById(id);

    }

    @Override
    public Optional<Usuario> asingnarTransitionalUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> crearTransitionalUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> eliminarTransitionalUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }
}
