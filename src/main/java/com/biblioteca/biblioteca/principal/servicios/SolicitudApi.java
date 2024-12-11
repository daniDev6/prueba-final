package com.biblioteca.biblioteca.principal.servicios;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
@Service
public class SolicitudApi {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        String json;
        try{
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            json=response.body();
        }catch (InterruptedException | IOException e){
            System.out.println("Error al hacer el request");
            json = null;
        }
        return json;
    }
}
