package com.biblioteca.biblioteca.principal.models;

import com.biblioteca.biblioteca.principal.models.dto.DatosAutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity(name="Autor")
@Table(name="autores")

@AllArgsConstructor

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String fechaNacimiento;
    private String fechaMuerte;
    @ManyToMany(mappedBy = "autores",fetch = FetchType.LAZY)
    private List<Libro> libros=new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }





    public Autor() {
    }

    public void setLibro(Libro libro){
            this.libros.add(libro);
            libro.getAutores().add(this);
    }
    public Autor(DatosAutor e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        this.nombre=e.nombre();
        this.fechaNacimiento=e.fechaNacimiento();
        this.fechaMuerte =e.fechaMuerte();

        /*try{
            this.fechaMuerte =LocalDate.parse(e.fechaMuerte()+ "-01-01",formatter);
        }catch (DateTimeParseException | NullPointerException | IllegalArgumentException |
                UnsupportedTemporalTypeException s){
            this.fechaMuerte=LocalDate.of(Integer.parseInt(e.fechaNacimiento()),1,1);
        }*/
    }



    public Long getId() {
        return id;
    }

    public void setLibros(Libro libro, Autor autor) {
        List<Libro> listaLibros=traerLibros(autor);
        libro.setAutor(autor);
        listaLibros.add(libro);
        this.libros=listaLibros;
    }
    public List<Libro> traerLibros(Autor autor){
        return autor.libros==null?new ArrayList<>():autor.libros;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "\nAutor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaMuerte=" + fechaMuerte +
                '}';
    }
}
