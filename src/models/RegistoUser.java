package models;

import java.io.*;

/**
 * A classe RegistoUser é responsável por gerir e autenticação de usuários.
 * Os usuários são armazenados em um array e carregados/salvos a partir de um arquivo de texto.
 * Esta classe mantém uma lista de usuários, permitindo o registro, autenticação e persistência
 * das informações de usuário.
 */
public class RegistoUser {
    private static final String ARQUIVO_USUARIOS = "FicheirosDB\\usuarios.txt";
    private static final int MAX_Users = 100;
    private static User[] utilizador;
    private static int totalUsers
            ;
    private static User userAutenticado;

     /**
     * Construtor da classe RegistoUser. Inicializa o array de usuários e carrega os usuários a partir do arquivo.
     */
    public RegistoUser() {
        utilizador = new User[MAX_Users];
        totalUsers = 0;
        carregarUsers();
    }

     /**
     * Carrega os usuários a partir do arquivo de texto especificado.
     * Cada linha no arquivo deve ter o formato "username, senha, tipoUser".
     * Se ocorrerem erros de formato, são exibidas mensagens no console.
     */
    private void carregarUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String username = partes[0].trim();
                    String senha = partes[1].trim();
                    User.TipoUser tipoUser = User.TipoUser.valueOf(partes[2].trim());
                    utilizador[totalUsers++] = new User(username, senha, tipoUser);
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
/**
     * Salva os usuários no arquivo de texto especificado.
     * Cada linha no arquivo tem o formato "username, senha, tipoUser".
     * Em caso de erro de E/S, é exibido o rastreamento da pilha no console.
     */
    private void salvarUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (int i = 0; i < totalUsers; i++) {
                User user = utilizador[i];
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getTipoUsuario());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    /** 
     * @param username
     * @param senha
     * @param tipoUser
/**
     * Registra um novo user com o nome de user, senha e tipo de user fornecidos.
     * Os usuários registrados são adicionados ao array de usuários e salvos no arquivo.
     *
     * @param username O nome de user do novo user.
     * @param senha A senha do novo user.
     * @param tipoUser O tipo de user do novo user (ADMINISTRADOR, CONSULTOR, EXTERNO).
     */
    public void registrarUser(String username, String senha, User.TipoUser tipoUser) {
        if (totalUsers < MAX_Users) {
            utilizador[totalUsers++] = new User(username, senha, tipoUser);
            salvarUsers();
        } else {
            System.out.println("Limite máximo de utilizadores atingido.");
        }
    }
/**
     * Autentica um user com base no nome de usuário e senha fornecidos.
     * Percorre a lista de user e retorna o user autenticado se encontrado.
     *
     * @param username O nome de user para autenticação.
     * @param senha A senha para autenticação.
     * @return O user autenticado ou null se não for encontrado.
     */
    public static User autenticarUser(String username, String senha) {
        for (int i = 0; i < totalUsers; i++) {
            User usuario = utilizador[i];
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }
/**
     * Enumeração que define os tipos de usuário possíveis.
     */
    public enum TipoUser {
        ADMINISTRADOR, CONSULTOR, EXTERNO
    }
}
