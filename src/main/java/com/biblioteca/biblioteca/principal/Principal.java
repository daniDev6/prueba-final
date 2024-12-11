package com.biblioteca.biblioteca.principal;


import com.biblioteca.biblioteca.principal.models.Autor;
import com.biblioteca.biblioteca.principal.models.Libro;
import com.biblioteca.biblioteca.principal.models.dto.DatosApi;

import com.biblioteca.biblioteca.principal.models.dto.DatosLibro;

import com.biblioteca.biblioteca.principal.repository.ServicioRepository;
import com.biblioteca.biblioteca.principal.servicios.Conversor;
import com.biblioteca.biblioteca.principal.servicios.SolicitudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Component
public class Principal {

    SolicitudApi solicitudApi = new SolicitudApi();
    @Autowired
    ServicioRepository servicioRepository;

    Conversor conversor = new Conversor();
    String URL_BASE = "https://gutendex.com/books/?";
    String URL_Titulos = URL_BASE + "search=";
    String   titulo2Autores= "The Federalist Papers";
    private String nombreLibroPrueba ="Middlemarch";
    private String nombreAutorPrueba ="Eliot, George";
    Scanner sc = new Scanner(System.in, "UTF-8");
    private String menuPrincipal = """
            1 - Buscar libro en base de datos
            2 - Buscar libro en Api
            3 - Crear Libro en base de datos
            4 - Mostrar Libros en la Base de Datos
            5 - Mostrar autores en Base de Datos
            0 - Salir
    """;
    private String subMenu= """
            1 - Buscar por Autor
            2 - Buscar por Titulo
            3 - Buscar por Idioma 
            """;
    private String subMenuCrearLibro="""
    \n1) Agregar un libro de la Api Gutendex
    \n2) Crear un libro con informacion Propia
    """;


    public void inicio(){
        System.out.println("Iniciando app");
        int opcion =-1;
        boolean continuar = true;
        while (continuar){
                opcion = ingreseInt(menuPrincipal);
            switch (opcion){
                case 0:
                    System.out.println("Gracias por usar la app");
                    continuar=false;
                    break;
                case 1:
                    buscarLibroBaseDatos();
                    break;
                case 2:
                    librosApiPorTitulo();
                    break;
                case 3:
                    crearLibroBD();
                    break;
                case 4:
                    mostrarLibroBaseDatos();
                    break;
                case 5:
                    mostrarAutoresBD();
                    break;
                default:
                    System.out.println("Fuera de rango");
                    break;
            }
            if(opcion!=0){
                System.out.println("Presione 0 para salir de la APP");
            }

        }
        System.out.println("Termino");
    }


    //1) buscar libro en base de datos
    public void buscarLibroBaseDatos(){
        System.out.println("Ingrese una opcion valida");
        System.out.println(subMenu);
        int opcion = -1;
        boolean condicion =true;
        while(condicion){
            opcion=ingreseInt("ingrese una opcion valida");
            switch (opcion){
                case 1:
                    libroPorAutor(1);
                    condicion=false;
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
                    condicion=true;
                    break;
            }
        }
    }
    //2)buscar libro en api
    public void librosApiPorTitulo(){
        String titulo = ingreseString("ingrese un titulo");
        String codificado=codificarTitulo(titulo);
        Libro libroBD =servicioRepository.libroPorTitulo(titulo);
        if(libroBD==null){
            String json = solicitudApi.obtenerDatos(URL_Titulos+codificado);
            DatosApi datosApi = conversor.convertirClase(json,DatosApi.class);
            List<DatosLibro> librosApi = filtrarLibrosRepetidos(datosApi.libros());
            System.out.println(servicioRepository.listarLibrosDTO(librosApi));
            System.out.println("Que libros desea guardar?");
            while(true){
                int indice = ingreseInt("Ingrese del 1 al " + (librosApi.size()));
                if(indice>librosApi.size() | indice<0){
                    System.out.println("Fuera de rango");
                }else{
                    servicioRepository.guardarLibroApi(librosApi.get(indice-1));
                    break;
                }
            }
        }else{
            System.out.println("Ya existe en la base de datos");
            System.out.println(servicioRepository.mostrarLibro(libroBD));
        }
    }
    //3) Crear libro en base de datos
    public void crearLibroBD(){
        int opcion = ingreseInt(subMenuCrearLibro);
        switch (opcion) {
            case 1:
                librosApiPorTitulo();
                break;
            case 2:
                crearLibroAutor();
                break;
            default:
                break;
        }
    }
    //4) mostrar libros de la base de datos
    public void mostrarLibroBaseDatos(){
        System.out.println("Estos son los libros en la base de datos");
        List<Libro> libros = servicioRepository.traerTodosLibrosBD();
        libros.stream().forEach(e-> System.out.println(servicioRepository.mostrarLibro(e)));
    }
    //5) mostrar autores de la base de datos
    public void mostrarAutoresBD(){
        System.out.println("Estos son los libros de la base de datos");
        List<Autor> autors = servicioRepository.autoresBaseDatos();
        autors.forEach(e-> System.out.println(servicioRepository.mostrarAutor(e)));
    }






    public String codificarTitulo(String nombre){
        String codificado = URLEncoder.encode(nombre);
        return codificado.replace("+","%20");

    }
    public List<DatosLibro> filtrarLibrosRepetidos(List<DatosLibro> lista){
        List<DatosLibro> listaRes =new ArrayList<>();
        Set<String> titulos=new HashSet<>();
        for(DatosLibro l : lista){
            if(titulos.add(l.titulo())){
                listaRes.add(l);
            }
        }
        return listaRes;

    }
    //Para in y out datos

    public String ingreseString(String msj) {
        String res = null;
        while (res==""|res == null) {
            try {
                System.out.println(msj);
                res = sc.nextLine();
            } catch (IllegalStateException | NoSuchElementException e) {
                System.out.println("Error al agregar el dato: " + e);
                sc.nextLine();
            }
        }
        return res;
    }
    public int ingreseInt(String msj){
        int res=-1;
        while(true){
            try{
                System.out.println(msj);
                res=sc.nextInt();
                sc.nextLine();
                break;
            }catch (InputMismatchException e){
                sc.nextLine();
                System.out.println("debe ingresar un numero entero");
            }
        }
        return res;
    }



    public void libroPorAutor(int opcion){
        String nombre = ingreseString("ingrese el nombre del autor/titulo");
        List<Libro> libros = new ArrayList<>();
        if(opcion==1){
            libros = servicioRepository.librosPorAutor(nombre);
        }
        if(libros.isEmpty()){
            System.out.println("Sin libro de ese autor");
        }else{
            libros.stream().forEach(e-> System.out.println(servicioRepository.mostrarLibro(e)));
        }
    }
    public void crearLibroAutor(){
        Libro libro = crearLibro();
        Autor autor = crearAutor();
        servicioRepository.crearLibroAutor(libro,autor);
    }
    public Libro crearLibro(){
        String titulo = ingreseString("Ingrese un titulo");
        System.out.println("Cantidad de lenguajes: ");
        List<String> lenguajes = new ArrayList<>();
        int nro=ingreseInt("Ingrese la cantidad de lenguajes");
        for(int i=1; i<nro;i++){
            lenguajes.add(ingreseString("Ingrese un lenguaje"));
        }
        int descargas = ingreseInt("Ingrese la cantidad de descargas");
        Libro newLibro = new Libro();
        newLibro.setTitulo(titulo);
        newLibro.setLenguajes(lenguajes);
        newLibro.setDescargas(descargas);
        return newLibro;
    }
    public Autor crearAutor(){
        String nombre = ingreseString("Ingrese el nombre del Autor:");
        String fechaNacimiento = ingreseString("Ingrese fecha de nacimiento");
        String fechaMuerto = ingreseString("Ingrese fecha de muerte");
        Autor newAutor = new Autor();
        newAutor.setNombre(nombre);
        newAutor.setFechaNacimiento(fechaNacimiento);
        newAutor.setFechaMuerte(fechaMuerto);

        return newAutor;

    }
}
