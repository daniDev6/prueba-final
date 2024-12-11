package com.biblioteca.biblioteca.principal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BibliotecaController {
    @GetMapping()
    public String holaMundo(){
        return "Hola mundo";
    }
    @GetMapping("/hola")
    public String holaMundo2(){
        return "Hola mundo2";
    }
}
