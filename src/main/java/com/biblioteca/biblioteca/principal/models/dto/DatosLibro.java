package com.biblioteca.biblioteca.principal.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro (
        @JsonAlias("id")
        int id,
        @JsonAlias("title")
        String titulo,
        @JsonAlias("authors")
        List<DatosAutor> autores,
        @JsonAlias("languages")
        List<String> lenguages,
        @JsonAlias("download_count")
        int cantDescargas
){
}
