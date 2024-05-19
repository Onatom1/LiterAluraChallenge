# LiterAlura

LiterAlura é um projeto desenvolvido como parte do desafio proposto pelo curso de Java da Alura. O objetivo deste projeto é criar uma aplicação Java que permite buscar livros por título, listar livros adicionados ao banco de dados, listar autores registrados, listar livros por idioma e adicionar novos livros ao banco de dados.

## Funcionalidades

- **Busca por Título:** Permite ao usuário buscar livros por título através de uma integração com a API Gutendex.
- **Listagem de Livros Adicionados:** Exibe os livros adicionados ao banco de dados PostgreSQL.
- **Listagem de Autores Registrados:** Apresenta os autores registrados no banco de dados juntamente com suas informações de nascimento e morte.
- **Listagem de Livros por Idioma:** Mostra os livros disponíveis no banco de dados filtrados por idioma.
- **Adição de Novos Livros:** Permite adicionar novos livros ao banco de dados com informações como título, autor, data de nascimento do autor, data de morte do autor (se aplicável), idioma e número de downloads.


## Como executar

1. Clone este repositório:
git clone https://github.com/seu-usuario/literalura.git

2. Navegue até o diretório do projeto:
cd literalura

3. Execute o programa:
java -jar literalura.jar


## Configuração do Banco de Dados

Antes de executar a aplicação, certifique-se de configurar corretamente o banco de dados PostgreSQL.

1. Crie um banco de dados no PostgreSQL.
2. Substitua as seguintes linhas no arquivo `Main.java` e `DatabaseManager.java` com as informações do seu banco de dados:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/NOME_DO_BANCO_DE_DADOS_AQUI"; //Substitua pelo nome do banco de dados criado no PostGreSQL
private static final String USER = "postgres";
private static final String PASSWORD = "SUA_SENHA_AQUI"; //Substitua por sua senha do PostGreSQL

Certifique-se de substituir `"NOME_DO_BANCO_DE_DADOS_AQUI"` pelo nome do seu banco de dados PostgreSQL e `"SUA_SENHA_AQUI"` pela sua senha do PostgreSQL.




