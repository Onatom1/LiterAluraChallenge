package br.com.alura.LiterAlura.BancoDeDados;

import br.com.alura.LiterAlura.models.Book;
import br.com.alura.LiterAlura.models.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/NOME_DO_BANCO_DE_DADOS_AQUI"; //Substitua pelo nome do banco de dados criado no PostGreSQL
    private static final String USER = "postgres";
    private static final String PASSWORD = "SUA_SENHA_AQUI"; //Substitua por sua senha do PostGreSQL

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author_name, author_birth_year, author_death_year, language, download_count) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            Person author = book.getAuthors().get(0);
            pstmt.setString(2, author.getName());

            if (author.getBirthYear() != null) {
                pstmt.setInt(3, author.getBirthYear());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }

            if (author.getDeathYear() != null) {
                pstmt.setInt(4, author.getDeathYear());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }

            pstmt.setString(5, book.getLanguages().get(0));
            pstmt.setInt(6, book.getDownloadCount());

            pstmt.executeUpdate();
            System.out.println("Livro inserido no banco de dados com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir livro no banco de dados: " + e.getMessage());
        }
    }
}
