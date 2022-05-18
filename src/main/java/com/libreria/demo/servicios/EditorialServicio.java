package com.libreria.demo.servicios;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Editorial;
import com.libreria.demo.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void registrarEditorial(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }

        List<Editorial> editoriales = editorialRepositorio.findAll();
        // CONTROLO QUE NO SE REPITA EL NOMBRE DE UNA EDITORIAL YA REGISTRADA
        for (Editorial editoriale : editoriales) {
            if (editoriale.getNombre().trim().equalsIgnoreCase(nombre)) {
                throw new ErrorServicio("Ya ah ingresado una editorial con el nombre " + nombre + ".");
            }
        }

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);

        System.out.println("Editorial registrada con exito.");

    }

    @Transactional
    public void modificarEditorial(String id, String nombre, boolean alta) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }
        if (alta != true && alta != false) {
            throw new ErrorServicio("El alta no puede ser nulo.");
        }

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            List<Editorial> editoriales = editorialRepositorio.findAll();
            // CONTROLO QUE NO SE REPITA EL NOMBRE DE UNA EDITORIAL YA REGISTRADA
            for (Editorial editoriale : editoriales) {
                if (editoriale.getNombre().trim().equalsIgnoreCase(nombre)) {
                    throw new ErrorServicio("Ya ah ingresado una editorial con el nombre " + nombre + ".");
                }
            }

            editorial.setNombre(nombre);
            editorial.setAlta(alta);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro la editorial solicitada.");
        }

    }

    @Transactional
    public void darDeBajaEditorial(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se ancontro la editorial solicitada.");
        }

    }

    @Transactional
    public void darDeAltaEditorial(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se ancontro la editorial solicitada.");
        }

    }

    @Transactional
    public List<Editorial> listarEditorial() throws ErrorServicio {
        return (List<Editorial>) editorialRepositorio.findAll();
    }

}
