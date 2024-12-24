package com.decsef.mcsvc_cursos.controllers;


import com.decsef.mcsvc_cursos.models.entity.Curso;
import com.decsef.mcsvc_cursos.services.CursosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional o = cursoService.getCursoById(id);

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


    private static ResponseEntity<Map<String, String>> validarBody(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
