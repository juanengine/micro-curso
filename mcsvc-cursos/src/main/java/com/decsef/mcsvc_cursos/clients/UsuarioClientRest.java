package com.decsef.mcsvc_cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "usuarios-mcrsvc", url = "localhost:8001")
public interface UsuarioClientRest {

}
