package com.biblioteca.biblioteca.principal.repository;

import com.biblioteca.biblioteca.principal.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LibroRepository extends JpaRepository<Libro,Long> {
    //@Query("SELECT l FROM Libro l WHERE l.titulo=:titulo")

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.autores WHERE l.titulo=:titulo")
    public Libro traerLibro(String titulo);


    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.autores")

    public List<Libro> traerLibros();

    @Query("SELECT l FROM Libro l JOIN FETCH  l.autores a WHERE LOWER(a.nombre) = LOWER(:nombre)")
    List<Libro> buscarPorAutor(String nombre);
    @Query("SELECT l FROM Libro l JOIN FETCH  l.autores a WHERE LOWER(l.titulo) = LOWER(:titulo)")
    Libro buscarPorTitulo(String titulo);
    /*
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE LOWER(a.nombre) = LOWER(:nombre)")
List<Libro> buscarPorAutor(@Param("nombre") String nombre);
    * */
}
