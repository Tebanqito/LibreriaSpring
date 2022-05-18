package com.libreria.demo.servicios;

import com.libreria.demo.Errores.ErrorServicio;
import com.libreria.demo.entidades.Autor;
import com.libreria.demo.entidades.Editorial;
import com.libreria.demo.entidades.Libro;
import com.libreria.demo.repositorios.AutorRepositorio;
import com.libreria.demo.repositorios.EditorialRepositorio;
import com.libreria.demo.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Transactional
    public void resgistrarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);

        Libro libro = new Libro();

        // CONTROLO QUE NO HAYA ALGUNA EDITORIAL CON EL MISMO NOMBRE PARA NO CREAR UNA EDITORIAL REPETIDA
        for (Editorial editoriale : editorialRepositorio.findAll()) {
            if (editoriale.getNombre().trim().toLowerCase().equalsIgnoreCase(editorial)) {
                // SI YA HAY UNA EDITORIAL REGISTRADA CON EL MISMO NOMBRE, LE ASIGNO ESA EDITORIAL YA REGISTRADA
                libro.setEditorial(editorialRepositorio.buscarPorNombre(editorial));
            }
        }
        // SINO SE AH ENCONTRADO UNA EDITORIAL CON ESE NOMBRE, ENTONCES SE CREA LA EDITORIAL Y SE LO SETEO AL LIBRO
        if (libro.getEditorial() == null) {
            editorialServicio.registrarEditorial(editorial);
            libro.setEditorial(editorialRepositorio.buscarPorNombre(editorial));
        }

        // CONTROLO QUE NO HAYA ALGUN AUTOR CON EL MISMO NOMBRE PARA NO CREAR UN AUTOR REPETIDO
        for (Autor autore : autorRepositorio.findAll()) {
            if (autore.getNombre().trim().toLowerCase().equalsIgnoreCase(autor)) {
                // SI YA HAY UN AUTOR REGISTRADO CON EL MISMO NOMBRE, LE ASIGNO ESE AUTOR YA REGISTRADO
                libro.setAutor(autorRepositorio.buscarPorNombre(autor));
            }
        }
        // SINO SE AH ENCONTRADO UN AUTOR CON ESE NOMBRE, ENTONCES SE CREA EL AUTOR Y SE LO SETEO AL LIBRO
        if (libro.getAutor() == null) {
            autorServicio.registrarAutor(autor);
            libro.setAutor(autorRepositorio.buscarPorNombre(autor));
        }

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);

        libroRepositorio.save(libro);

        System.out.println("Libro registrado exitosamente.");

    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = new Libro();
            libro = respuesta.get();

            // CONTROLO QUE NO HAYA ALGUNA EDITORIAL CON EL MISMO NOMBRE PARA NO CREAR UNA EDITORIAL REPETIDA
            for (Editorial editoriale : editorialRepositorio.findAll()) {
                if (editoriale.getNombre().trim().toLowerCase().equalsIgnoreCase(editorial)) {
                    // SI YA HAY UNA EDITORIAL REGISTRADA CON EL MISMO NOMBRE, LE ASIGNO ESA EDITORIAL YA REGISTRADA
                    libro.setEditorial(editorialRepositorio.buscarPorNombre(editorial));
                }
            }
            // SINO SE AH ENCONTRADO UNA EDITORIAL CON ESE NOMBRE, ENTONCES SE CREA LA EDITORIAL Y SE LO SETEO AL LIBRO
            if (libro.getEditorial() == null) {
                editorialServicio.registrarEditorial(editorial);
                libro.setEditorial(editorialRepositorio.buscarPorNombre(editorial));
            }

            // CONTROLO QUE NO HAYA ALGUN AUTOR CON EL MISMO NOMBRE PARA NO CREAR UN AUTOR REPETIDO
            for (Autor autore : autorRepositorio.findAll()) {
                if (autore.getNombre().trim().toLowerCase().equalsIgnoreCase(autor)) {
                    // SI YA HAY UN AUTOR REGISTRADO CON EL MISMO NOMBRE, LE ASIGNO ESE AUTOR YA REGISTRADO
                    libro.setAutor(autorRepositorio.buscarPorNombre(autor));
                }
            }
            // SINO SE AH ENCONTRADO UN AUTOR CON ESE NOMBRE, ENTONCES SE CREA EL AUTOR Y SE LO SETEO AL LIBRO
            if (libro.getAutor() == null) {
                autorServicio.registrarAutor(autor);
                libro.setAutor(autorRepositorio.buscarPorNombre(autor));
            }

            libro.setIsbn(isbn);
            libro.setAlta(true);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro solicitdo.");
        }

    }

    @Transactional
    public void darDeBajaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se ancontro el libro solicitado.");
        }

    }

    @Transactional
    public void darDeAltaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se ancontro el libro solicitado.");
        }

    }

    @Transactional
    public ArrayList<Libro> listarLibros() throws ErrorServicio {

        ArrayList<Libro> libros = libroRepositorio.listarLibros();
        if (!libros.isEmpty()) {
            return libros;
        } else {
            throw new ErrorServicio("No se ha encontrado ningun libro.");
        }
    }
    
    @Transactional
    public Libro buscarLibroPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("No se ah encontrado el libro solicitado.");
        }
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {

        if (isbn == null) {
            throw new ErrorServicio("El ISBN no puede ser nulo.");
        }

        if (isbn < 100000000 || 1000000000 < isbn) {
            throw new ErrorServicio("Ah ingresado un ISBN invalido.");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo.");
        }

        for (Libro libro : libroRepositorio.findAll()) {
            if (libro.getTitulo().trim().equalsIgnoreCase(titulo)) {
                throw new ErrorServicio("Ya hay un libro registrado con el mismo titulo.");
            }
        }

        if (anio == null) {
            throw new ErrorServicio("El anio no puede ser nulo.");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no pueden ser nulos.");
        }

        if (ejemplares < 0) {
            throw new ErrorServicio("La cantidad de ejemplares no puede ser menor a cero.");
        }

        if (ejemplaresPrestados < 0 || ejemplaresPrestados == null) {
            throw new ErrorServicio("La cantidad de ejemplares prestados no puede ser nulo o menor a cero.");
        }

        if (ejemplaresRestantes < 0 || ejemplaresRestantes == null) {
            throw new ErrorServicio("La cantidad de ejemplares restantes no puede ser nulo o menor a cero.");
        }

        if (ejemplaresRestantes + ejemplaresPrestados != ejemplares) {
            throw new ErrorServicio("Ah ingresado una cantidad incorrecta entre ejemplares prestados y ejemplares restantes.");
        }
        if (autor == null || autor.isEmpty()) {
            throw new ErrorServicio("El autor no puede ser nulo.");
        }

        if (editorial == null || editorial.isEmpty()) {
            throw new ErrorServicio("La editorial no puede ser nula.");
        }

    }

}
