package br.com.alura.LiterAlura;

import br.com.alura.LiterAlura.models.Book;
import br.com.alura.LiterAlura.models.BookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.sql.*;


class BookSearcher {
    public static void searchByTitleAndAddToDatabase(Scanner scanner, Connection connection) {
        System.out.println("Digite o título do livro:");
        String title = scanner.nextLine();

        String baseUrl = "https://gutendex.com/books/";
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String url = baseUrl + "?search=" + encodedTitle;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                BookResponse bookResponse = objectMapper.readValue(response.body(), BookResponse.class);
                List<Book> books = bookResponse.getResults();
                if (!books.isEmpty()) {
                    Book book = books.get(0);
                    System.out.println("Livro Encontrado:");
                    System.out.println("Título: " + book.getTitle());
                    System.out.println("Autor: " + book.getAuthors().get(0).getName());
                    System.out.println("Idioma: " + book.getLanguages().get(0));
                    System.out.println("Número de Downloads: " + book.getDownloadCount());

                    // Adicionando os detalhes do livro ao banco de dados
                    try (PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO books (title, author_name, author_birth_year, author_death_year, language, download_count) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)")) {
                        preparedStatement.setString(1, book.getTitle());
                        preparedStatement.setString(2, book.getAuthors().get(0).getName());
                        preparedStatement.setInt(3, book.getAuthors().get(0).getBirthYear());
                        preparedStatement.setInt(4, book.getAuthors().get(0).getDeathYear());
                        preparedStatement.setString(5, book.getLanguages().get(0));
                        preparedStatement.setInt(6, book.getDownloadCount());

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " livro adicionado ao banco de dados.");
                    } catch (SQLException e) {
                        System.out.println("Erro ao adicionar livro ao banco de dados: " + e.getMessage());
                    }
                } else {
                    System.out.println("Nenhum livro encontrado com o título '" + title + "'.");
                }
            } else {
                System.out.println("Falha ao buscar livro. Código de status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    public static void listBooks(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title FROM books");

            List<String> bookTitles = new ArrayList<>();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }

            if (!bookTitles.isEmpty()) {
                System.out.println("Livros adicionados ao banco de dados:");
                for (String title : bookTitles) {
                    System.out.println(title);
                }
            } else {
                System.out.println("Nenhum livro adicionado ao banco de dados.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os livros: " + e.getMessage());
        }
    }

    public static void listAuthors(Connection connection) {
        String query = "SELECT DISTINCT author_name, author_birth_year, author_death_year FROM books";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Map<String, AuthorInfo> authors = new HashMap<>();
            while (resultSet.next()) {
                String authorName = resultSet.getString("author_name");
                int birthYear = resultSet.getInt("author_birth_year");
                int deathYear = resultSet.getInt("author_death_year");

                AuthorInfo authorInfo = new AuthorInfo(birthYear, deathYear);
                authors.put(authorName, authorInfo);
            }

            System.out.println("Autores registrados:");
            for (Map.Entry<String, AuthorInfo> entry : authors.entrySet()) {
                String authorName = entry.getKey();
                AuthorInfo authorInfo = entry.getValue();

                String birthYear = (authorInfo.getBirthYear() != 0) ? String.valueOf(authorInfo.getBirthYear()) : "Desconhecido";
                String deathYear = (authorInfo.getDeathYear() != 0) ? String.valueOf(authorInfo.getDeathYear()) : "Vivo";

                System.out.println("Nome: " + authorName + "\nNascimento: " + birthYear + "\nMorte: " + deathYear);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        }
    }

    public static void listBooksByLanguage(Connection connection, String language) {
        String query = "SELECT title FROM books WHERE language = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, language);
            ResultSet resultSet = statement.executeQuery();

            List<String> bookTitles = new ArrayList<>();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }

            if (!bookTitles.isEmpty()) {
                System.out.println("Livros em " + language + ":");
                System.out.println("-----------------------------");
                for (String title : bookTitles) {
                    System.out.println(title);
                }
            } else {
                System.out.println("Nenhum livro encontrado em " + language + ".");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os livros em " + language + ": " + e.getMessage());
        }
    }

    private static class AuthorInfo {
        private int birthYear;
        private int deathYear;

        public AuthorInfo(int birthYear, int deathYear) {
            this.birthYear = birthYear;
            this.deathYear = deathYear;
        }

        public int getBirthYear() {
            return birthYear;
        }

        public int getDeathYear() {
            return deathYear;
        }
    }
}