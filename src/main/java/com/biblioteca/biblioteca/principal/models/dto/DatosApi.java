package com.biblioteca.biblioteca.principal.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosApi(
        @JsonAlias("count")
        int cantLibros,
        @JsonAlias("next")
        String nextPag,
        @JsonAlias("previous")
        String prevPag,
        @JsonAlias("results")
        List<DatosLibro> libros
) {
}
