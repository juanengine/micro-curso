package com.decsef.mcsvc_cursos.controllers;


import com.decsef.mcsvc_cursos.models.entity.Curso;
import com.decsef.mcsvc_cursos.models.entity.Usuario;
import com.decsef.mcsvc_cursos.services.CursosService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {


    @Autowired
    private CursosService cursoService;

    @GetMapping()
    public ResponseEntity<List<Curso>>   getAll(){
        return ResponseEntity.ok(cursoService.allCursos()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCursoById(@PathVariable Long id){

        //Optional o = cursoService.getCursoById(id);

        Optional<Curso> o = cursoService.getCursoConDetalleUsuarios(id);

        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return validarBody(bindingResult);
        }
      return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.saveCurso(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurso( @Valid @RequestBody Curso curso, BindingResult bindingResult, @PathVariable Long id){

        if(bindingResult.hasErrors()){
            return validarBody(bindingResult);
        }
        Optional<Curso> o = cursoService.getCursoById(id);

        if(o.isPresent()){
            Curso cursoCurrent = o.get();
            cursoCurrent.setNombre(curso.getNombre());
            ResponseEntity.status(HttpStatus.CREATED).body(cursoService.saveCurso(cursoCurrent));

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id){

        Optional<Curso> o = cursoService.getCursoById(id);

        if(o.isPresent()){

            cursoService.deleteCurso(id);

            ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioById(@PathVariable Long id){

        cursoService.deleteCursoUsuario(id);

        return ResponseEntity.noContent().build();
    }

    //rutas de comunicaciones entre mmmicroservicios

    @PutMapping("/asignar-curso/{cursoId}")
    public ResponseEntity<?> asignaUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;

        try{
             usuarioOptional = cursoService.asingnarTransitionalUsuario(usuario, cursoId);

        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error en la comunicacion o no existe el usuario: " + e.getMessage()));
        }

        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> creaUsuarioEnCurso(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;

        try{
            usuarioOptional = cursoService.crearTransitionalUsuario(usuario, cursoId);

        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error en la comunicacion o no se pudo crear el usuario: " + e.getMessage()));
        }

        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminaUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;

        try{
            usuarioOptional = cursoService.eliminarTransitionalUsuario(usuario, cursoId);

        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error en la comunicacion o no existe el usuario: " + e.getMessage()));
        }

        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
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
