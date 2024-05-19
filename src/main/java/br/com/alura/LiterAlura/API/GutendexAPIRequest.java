package br.com.alura.LiterAlura.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GutendexAPIRequest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String baseUrl = "https://api.gutendex.com/books";
        String title = "great%20expectations";


        String url = String.format("%s?search=%s", baseUrl, title);


        HttpClient client = HttpClient.newHttpClient();


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        int statusCode = response.statusCode();
        System.out.println("Status Code: " + statusCode);


        String responseBody = response.body();
        System.out.println("Response Body:");
        System.out.println(responseBody);
    }
}
