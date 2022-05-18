package com.libreria.demo.repositorios;

import com.libreria.demo.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
    @Query("SELECT u FROM Editorial u WHERE u.nombre LIKE :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String nombre);
    
}
