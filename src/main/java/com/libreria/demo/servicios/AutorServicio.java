package com.libreria.demo.servicios;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Autor;
import com.libreria.demo.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void registrarAutor(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }

        List<Autor> autores = autorRepositorio.findAll();
        // CONTROLO QUE NO SE REPITA EL NOMBRE DE UN AUTOR YA REGISTRADO
        for (Autor autore : autores) {
            if (autore.getNombre().trim().equalsIgnoreCase(nombre)) {
                throw new ErrorServicio("Ya se ah registrado un autor con el nombre " + nombre + ".");
            }
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);

        System.out.println("Autor registrado con exito.");

    }

    @Transactional
    public void modificarEditorialAutor(String id, String nombre, boolean alta) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }
        if (alta != true && alta != false) {
            throw new ErrorServicio("El alta no puede ser nulo.");
        }

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            List<Autor> autores = autorRepositorio.findAll();
            // CONTROLO QUE NO SE REPITA EL NOMBRE DE UN AUTOR YA REGISTRADO
            for (Autor autore : autores) {
                if (autore.getNombre().trim().equalsIgnoreCase(nombre)) {
                    throw new ErrorServicio("Ya se ah registrado un autor con el nombre " + nombre + ".");
                }
            }

            autor.setNombre(nombre);
            autor.setAlta(alta);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro al autor.");
        }

    }

    @Transactional
    public void darDeBajaAutor(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se ancontro al autor solicitado.");
        }

    }

    @Transactional
    public void darDeAltaAutor(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se ancontro al autor solicitado..");
        }

    }

    @Transactional
    public ArrayList<Autor> listarAutor() throws ErrorServicio {
        return (ArrayList<Autor>) autorRepositorio.findAll();
    }

}
