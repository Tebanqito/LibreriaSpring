package com.libreria.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/editarLibro")
    public String editarLibro() {
        return "editarLibro";
    }

    @GetMapping("/administrarLibros")
    public String administrar_libros() {
        return "administrar_libros";
    }

    @GetMapping("/administrarEditoriales")
    public String administrar_editoriales() {
        return "administrar_editoriales";
    }

    @GetMapping("/administrarAutores")
    public String administrar_autores() {
        return "administrar_autores";
    }

}
