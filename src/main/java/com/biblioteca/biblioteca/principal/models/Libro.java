package com.biblioteca.biblioteca.principal.models;

import com.biblioteca.biblioteca.principal.models.dto.DatosAutor;
import com.biblioteca.biblioteca.principal.models.dto.DatosLibro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity(name="Libro")
@Table(name="libros")

@AllArgsConstructor


public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private int descargas;
    private List<String> lenguajes;
    @JoinTable(
            name = "rel_libros_autores",
            joinColumns = @JoinColumn(name = "fk_libro", nullable = false),
            inverseJoinColumns = @JoinColumn(name="fk_autor", nullable = false)
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Autor> autores=new ArrayList<>();

    public Libro() {
    }

    public Libro(DatosLibro e) {
        this.titulo=e.titulo();
        this.descargas=e.cantDescargas();
        this.lenguajes=e.lenguages();
        //this.autores=e.autores().stream().map(f->new Autor(f)).collect(Collectors.toList());
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }
    public void setLenguaje(String lenguaje){
        this.lenguajes.add(lenguaje);
    }

    public void setAutor(Autor autor){

            this.autores.add(autor);
            autor.getLibros().add(this);

    }
    public void setAutoresApi(List<DatosAutor> autores){
        List<Autor> listaAutores = autores.stream().map(e->new Autor(e)).collect(Collectors.toList());
        this.autores=listaAutores;
        this.autores.forEach(e->e.getLibros().add(this));
    }
    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDescargas() {
        return descargas;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {

        return "\nLibro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descargas=" + descargas +
                ", lenguajes=" + lenguajes +

                '}';
    }
}
