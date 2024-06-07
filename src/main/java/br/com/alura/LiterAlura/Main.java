package br.com.alura.LiterAlura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/livros"; // URL do seu banco de dados PostgreSQL
        String user = "postgres";
        String password = "33887534"; // Senha do PostgreSQL

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            boolean running = true;
            while (running) {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Buscar por título e adicionar ao banco de dados");
                System.out.println("2 - Listar livros adicionados ao banco de dados");
                System.out.println("3 - Listar autores registrados");
                System.out.println("4 - Listar livros por idioma");
                System.out.println("0 - Sair");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        BookSearcher.searchByTitleAndAddToDatabase(scanner, connection);
                        break;
                    case 2:
                        BookSearcher.listBooks(connection);
                        break;
                    case 3:
                        BookSearcher.listAuthors(connection);
                        break;
                    case 4:
                        System.out.println("Insira o idioma para realizar a busca:");
                        System.out.println("es - Espanhol");
                        System.out.println("en - Inglês");
                        System.out.println("fr - Francês");
                        System.out.println("pt - Português");
                        String language = scanner.nextLine();
                        BookSearcher.listBooksByLanguage(connection, language);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
