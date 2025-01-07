package com.decsef.mcsvc_cursos.clients;

import com.decsef.mcsvc_cursos.models.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "usuarios-mcrsvc", url = "usuarios-mcrsvc:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario infoUsuario(@PathVariable Long id);

    @PostMapping("/")
    Usuario crearUsuario(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-by-curso")
    List<Usuario> usuariosPorCurso(@RequestParam Iterable<Long> ids);

}
