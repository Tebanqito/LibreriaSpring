package com.libreria.demo.controladores;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Libro;
import com.libreria.demo.servicios.LibroServicio;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/administrarLibros")
    public String listarLibros(ModelMap modelo) throws ErrorServicio {
        ArrayList<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "administrar_libros";
    }
    
    @PostMapping("/administrarLibros")
    public String administrar_libros(ModelMap modelo, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String autor, @RequestParam String editorial) {

        try {
            libroServicio.resgistrarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplares prestados", ejemplaresPrestados);
            modelo.put("ejemplares retantes", ejemplaresRestantes);
            modelo.put("autor", autor);
            modelo.put("editorial", editorial);
            return "administrar_libros";
        }
        modelo.put("titulo", "Bienvenido a Gestion de Libreria.");
        modelo.put("descripcion", "La carga de datos fue hecha de manera satifactoria.");
        return "exito";

    }
    
    @GetMapping("/editarLibro/{id}")
    public String editarLibro(ModelMap modelo, @PathVariable String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {
        Libro libro = libroServicio.buscarLibroPorId(id);
        modelo.put("libro", libro);
        return "editarLibro";
    }
    
    @PostMapping("/editarLibro/{id}")
    public String editar_Libro(ModelMap modelo, @PathVariable String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {
        try {
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "editarLibro";
        }
        modelo.put("descripcion", "Se ah actualizado correctamente");
        return "exito";
    }
    
}
