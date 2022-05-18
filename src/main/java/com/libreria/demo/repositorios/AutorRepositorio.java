package com.libreria.demo.repositorios;

import com.libreria.demo.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    
    @Query("SELECT u FROM Autor u WHERE u.nombre LIKE :nombre")
    public Autor buscarPorNombre(@Param("nombre") String nombre);
    
}
