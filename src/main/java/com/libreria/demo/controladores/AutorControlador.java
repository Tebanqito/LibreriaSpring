package com.libreria.demo.controladores;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Autor;
import com.libreria.demo.servicios.AutorServicio;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/administrarAutores")
    public String listarAutores(ModelMap modelo) throws ErrorServicio {
        ArrayList<Autor> autores = autorServicio.listarAutor();
        if(autores.isEmpty()) throw new ErrorServicio("No se ah encontrado ningun autor.");
        modelo.addAttribute("autores", autores);
        return "administrar_autores";
    }
    
    @PostMapping("/administrarAutores")
    public String administrar_autores(ModelMap modelo, @RequestParam String nombre) {

        try {
            autorServicio.registrarAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "administrar_autores";
        }
        modelo.put("titulo", "Bienvenido a Gestion de Libreria.");
        modelo.put("descripcion", "La carga de datos fue hecha de manera satifactoria.");
        return "exito";

    }
    
}
