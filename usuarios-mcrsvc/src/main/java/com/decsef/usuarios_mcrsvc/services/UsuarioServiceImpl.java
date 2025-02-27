package com.decsef.usuarios_mcrsvc.services;

import com.decsef.usuarios_mcrsvc.client.CursoClienteRest;
import com.decsef.usuarios_mcrsvc.models.entity.Usuario;
import com.decsef.usuarios_mcrsvc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClienteRest clienteRest;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buecarUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {

        usuarioRepository.deleteById(id);
        clienteRest.eliminarCursoUsuarioById(id);
    }

    @Override
    public List<Usuario> usuariosByIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);

    }
}
