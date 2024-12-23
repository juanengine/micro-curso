package com.decsef.usuarios_mcrsvc.controllers;

import com.decsef.usuarios_mcrsvc.models.entity.Usuario;
import com.decsef.usuarios_mcrsvc.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> todosUsuarios(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.buecarUsuarioById(id);
        if ( usuarioOptional.isPresent()){
            return ResponseEntity.ok().body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){

        if(usuarioService.getByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("El usuario ya esta Registrado");
        }

        if(bindingResult.hasErrors()){
            return validarBody(bindingResult);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario))  ;
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult,  @PathVariable Long id){

        if(bindingResult.hasErrors()){
            return validarBody(bindingResult);
        }


        Optional<Usuario> usuarioOptional= usuarioService.buecarUsuarioById(id);
        if (usuarioOptional.isPresent()){
            Usuario usuarioCurrent= usuarioOptional.get();
            usuarioCurrent.setEmail(usuario.getEmail());
            usuarioCurrent.setNombre(usuario.getNombre());
            usuarioCurrent.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuarioCurrent));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Optional<Usuario> usuarioOptional= usuarioService.buecarUsuarioById(id);
        if (usuarioOptional.isPresent()){
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validarBody(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
