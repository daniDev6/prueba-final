package com.biblioteca.biblioteca.principal.repository;

import com.biblioteca.biblioteca.principal.models.Autor;

import com.biblioteca.biblioteca.principal.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AutorRepository  extends JpaRepository<Autor,Long> {

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.nombre=:nombre")
    public Autor traerAutor(String nombre);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    public List<Autor> traerAutores();



}
