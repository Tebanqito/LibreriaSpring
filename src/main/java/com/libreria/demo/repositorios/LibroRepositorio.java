package com.libreria.demo.repositorios;

import com.libreria.demo.entidades.Libro;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT u FROM Libro u")
    public ArrayList<Libro> listarLibros();
    
    @Query("SELECT u FROM Libro u WHERE u.isbn = :isbn")
    public Libro buscarPorIsbn(@Param("isbn") Long isbn);
    
    @Query("SELECT u FROM Libro u WHERE u.titulo LIKE :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);
    // el porcentaje (%) va adelante de los dos puntitos (:) en la query si lo quiero ingresar adelante del parametro
    @Query("SELECT u FROM Libro u WHERE u.autor.nombre LIKE :nombre")
    public ArrayList<Libro> buscarPorAutor(@Param("nombre") String nombre);
    
    @Query("SELECT u FROM Libro u WHERE u.editorial.nombre LIKE :nombre")
    public ArrayList<Libro> buscarPorEditorial(@Param("nombre") String nombre);
    
}
