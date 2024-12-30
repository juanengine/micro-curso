package com.decsef.mcsvc_cursos.services;

import com.decsef.mcsvc_cursos.clients.UsuarioClientRest;
import com.decsef.mcsvc_cursos.models.entity.Curso;
import com.decsef.mcsvc_cursos.models.entity.CursoUsuario;
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

    @Autowired
    private UsuarioClientRest clientRest;

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
    @Transactional(readOnly = true)
    public Optional<Curso> getCursoConDetalleUsuarios(Long id) {

        Optional<Curso> cursoOptional = cursosRepository.findById(id);

        if (cursoOptional.isPresent()){

            Curso curso = cursoOptional.get();

            if(!curso.getCursoUsuarios().isEmpty()){

                List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();

                List<Usuario> usuarios = clientRest.usuariosPorCurso(ids);

                curso.setUsuarios(usuarios);
            }

            return Optional.of(curso);

        }
        return Optional.empty();
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
    @Transactional
    public void deleteCursoUsuario(Long id) {
        cursosRepository.deleteCursoUsuarioById(id);
    }

    //Servicios de comunicacion con microservicio usuarios

    @Override
    @Transactional
    public Optional<Usuario> asingnarTransitionalUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursosRepository.findById(cursoId);

        if (cursoOptional.isPresent()){

            Usuario usuarioMicroSv = clientRest.infoUsuario(usuario.getId());

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();

            cursoUsuario.setUsuarioId(usuarioMicroSv.getId());

            curso.addCursoUsuario(cursoUsuario);

            cursosRepository.save(curso);

            return Optional.of(usuarioMicroSv);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearTransitionalUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursosRepository.findById(cursoId);

        if (cursoOptional.isPresent()){

            Usuario usuarioMicroSv = clientRest.crearUsuario(usuario);

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();

            cursoUsuario.setUsuarioId(usuarioMicroSv.getId());

            curso.addCursoUsuario(cursoUsuario);

            cursosRepository.save(curso);

            return Optional.of(usuarioMicroSv);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarTransitionalUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursosRepository.findById(cursoId);

        if (cursoOptional.isPresent()){

            Usuario usuarioMicroSv = clientRest.infoUsuario(usuario.getId());

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();

            cursoUsuario.setUsuarioId(usuarioMicroSv.getId());

            curso.removeCursoUsuario(cursoUsuario);

            cursosRepository.save(curso);

            return Optional.of(usuarioMicroSv);

        }
        return Optional.empty();
    }
}
