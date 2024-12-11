package com.biblioteca.biblioteca.principal.repository;

import com.biblioteca.biblioteca.principal.models.Autor;
import com.biblioteca.biblioteca.principal.models.Libro;
import com.biblioteca.biblioteca.principal.models.dto.DatosLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioRepository {
    AutorRepository autorRepository;
    LibroRepository libroRepository;
    @Autowired
    public ServicioRepository(AutorRepository autorRepository,LibroRepository libroRepository) {
        this.autorRepository=autorRepository;
        this.libroRepository=libroRepository;
    }
    @Transactional
    public boolean agregarLibroAlAutorBD(Libro libro, Autor autorBD){
        autorBD.setLibro(libro);
        try{
            crearLibro(libro);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Transactional
    public boolean agregarAutorLibroBD(Libro libroBD, Autor autorNuevo){
        libroBD.setAutor(autorNuevo);
        try{
            crearAutor(autorNuevo);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Transactional
    public boolean crearLibroAutor(Libro libro, Autor autor){
        try{
            autor.setLibro(libro);
            crearLibro(libro);
            crearAutor(autor);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Transactional
    public boolean guardarLibroApi(DatosLibro libroApi){
        Set<String> autoresBD = new HashSet<>();
        List<Autor> listaAutoresBD = autorRepository.traerAutores();
        listaAutoresBD.forEach(e->autoresBD.add(e.getNombre()));
        Libro newLibro = new Libro(libroApi);
        Libro libroBD = libroRepository.traerLibro(newLibro.getTitulo());
        if(libroBD==null){
            libroApi.autores().forEach(e->{
                Autor autorCargar;
                if(autoresBD.add(e.nombre())){
                    autorCargar= new Autor(e);
                    crearAutor(autorCargar);
                }else{
                    autorCargar=autorRepository.traerAutor(e.nombre());
                }
                newLibro.setAutor(autorCargar);
                //autorCargar.setLibro(newLibro);

            });
            //newLibro.setAutoresApi(libroApi.autores());
            try{
                libroRepository.save(newLibro);
                System.out.println("Se guardo con exito el libro");
                System.out.println(mostrarLibro(newLibro));
            }catch (Exception e){

            }

        }else{
            System.out.println("libro ya subido ala base de datos");
            System.out.println(mostrarLibro(libroBD));
        }
        return false;
    }
    @Transactional
    public Libro crearLibro(Libro libro){return libroRepository.save(libro);}
    @Transactional
    public Autor crearAutor(Autor autor){return autorRepository.save(autor);}

    public List<Libro> librosBaseDatos(){
        return libroRepository.traerLibros();
    }
    public Libro libroPorTitulo(String nombre){
        return libroRepository.buscarPorTitulo(nombre);
    }
    public List<Libro> librosPorAutor(String nombre){return libroRepository.buscarPorAutor(nombre);}
    public List<Autor> autoresBaseDatos(){
        return autorRepository.traerAutores();
    }
    public Autor buscarAutorBD(String nombre){
        return autorRepository.traerAutor(nombre);
    }
    public List<Libro> traerTodosLibrosBD (){return libroRepository.traerLibros();}




    //mostrar datos
    public String mostrarLibro(Libro libro){
        List<String> autoresNombre = libro.getAutores().stream().map(e->e.getNombre()).collect(Collectors.toList());
        String autores = "";
        for(String a : autoresNombre){
            autores+="\n -"+a;
        }
        String lenguajes ="";
        for(String l : libro.getLenguajes()){
            lenguajes+="\n -"+l;
        }
        String INFO_LIBRO = String.format("--------------------------Libro--------------------------" +
                "\nTitulo: %s" +
                "\nAutor/s: %s" +
                "\nIdioma: %s" +
                "\nNumero de Descargas: %d" +
                "\nId: %d"+
                "\n---------------------------------------------------------", libro.getTitulo(), autores, lenguajes, libro.getDescargas(),libro.getId());
        return INFO_LIBRO;
    }
    public String mostrarLibro(DatosLibro libro){
        List<String> autoresNombre = libro.autores().stream().map(e->e.nombre()).collect(Collectors.toList());
        String autores = "";
        for(String a : autoresNombre){
            autores+="\n -"+a;
        }
        String lenguajes ="";
        for(String l : libro.lenguages()){
            lenguajes+="\n -"+l;
        }
        String INFO_LIBRO = String.format("--------------------------Libro--------------------------" +
                "\nTitulo: %s" +
                "\nAutor/s: %s" +
                "\nIdioma: %s" +
                "\nNumero de Descargas: %d", libro.titulo(), autores, lenguajes, libro.cantDescargas());
        return INFO_LIBRO;
    }
    public String mostrarLibro(DatosLibro libro,int id){
        return "\n.              -----------------------------------                   ."+"\n"+mostrarLibro(libro)+ "\nId para Agregar: "+id ;
    }
    public String mostrarLibro(Libro libro,int id){
        return "\n.              -----------------------------------                   ."+"\n"+mostrarLibro(libro)+ "\nId para Agregar: "+id ;
    }
    public String mostrarAutor(Autor autor) {
        List<String> nombreLibros = new ArrayList();
        autor.getLibros().forEach((e) -> {
            nombreLibros.add(e.getTitulo());
        });
        String listaLibros = "";
        for(String s : nombreLibros){
            listaLibros+="\n- "+ s;
        }
        String INFO_AUTOR = String.format("--------------------------Autor--------------------------" +
                "\nNombre: %s" +
                "\nFecha Nacimiento: %s" +
                "\nFecha Fallecimiento: %s" +
                "\nLista de Libros: %s" +
                "\n---------------------------------------------------------", autor.getNombre(), autor.getFechaNacimiento(), autor.getFechaMuerte(), listaLibros);
        return INFO_AUTOR;
    }
    public String listarLibrosDTO(List<DatosLibro> libros){
        String msjs = "";
        int id = 1;
        for(DatosLibro l : libros){
            msjs+=mostrarLibro(l,id);
            id++;
        }
        return msjs;
    }
    public String listarLibros(List<Libro> libros){
        String msjs = "";
        for(Libro l : libros){
            msjs+=mostrarLibro(l, l.getId().intValue());

        }
        return msjs;
    }








}
