package br.com.alura.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {
    private List<Book> results;

    // Getters e Setters
    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }
}
