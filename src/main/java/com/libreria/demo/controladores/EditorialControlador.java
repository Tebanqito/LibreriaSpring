package com.libreria.demo.controladores;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Editorial;
import com.libreria.demo.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/administrarEditoriales")
    public String listarEditoriales(ModelMap modelo) throws ErrorServicio {
        List<Editorial> editoriales = editorialServicio.listarEditorial();
        if(editoriales.isEmpty()) throw new ErrorServicio("No se ah encontrado ninguna editorial.");
        modelo.addAttribute("editoriales", editoriales);
        return "administrar_editoriales";
    }
    
    @PostMapping("/administrarEditoriales")
    public String administrar_editoriales(ModelMap modelo, @RequestParam String nombre) {

        try {
            editorialServicio.registrarEditorial(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "administrar_editoriales";
        }
        modelo.put("titulo", "Bienvenido a Gestion de Libreria.");
        modelo.put("descripcion", "La carga de datos fue hecha de manera satifactoria.");
        return "exito";

    }
    
}
