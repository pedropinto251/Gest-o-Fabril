package models;

import java.util.InputMismatchException;
import java.util.Scanner;
import static main.Aplicaçao.*;

public class Login {

    public static void realizarLoginOuRegistro() {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 1 && opcao != 2) {
            try {
                System.out.println("*********************************************");
                System.out.println("***           Entrar / Registar         *****");
                System.out.println("*********************************************");
                System.out.println("***     1 - Entrar com conta existente    ***");
                System.out.println("***     2 - Criar nova conta              ***");
                System.out.println("*********************************************");
                // Verificar se a entrada é um número
                if (sc.hasNextInt()) {
                    opcao = sc.nextInt();

                    switch (opcao) {
                        case 1:
                            realizarLogin();
                            break;
                        case 2:
                            realizarRegistro();
                            break;
                        default:
                            System.out.println("*********************************************");
                            System.out.println("***            Opção Inválida           *****");
                            System.out.println("*********************************************");
                    }
                } else {
                    // Limpar o buffer do sc se a entrada não for um número

                    System.out.println("*********************************************");
                    System.out.println("***          Opção Inválida             *****");
                    System.out.println("*********************************************");
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("*********************************************");
                System.out.println("***  Por favor, insira um número válido.  ***");
                System.out.println("*********************************************");
                sc.nextLine(); // Limpar o buffer do sc
            }
        }

        // Fechar o sc no final
        sc.close();
    }

    public static void realizarLogin() {
        Scanner sc = new Scanner(System.in);

        System.out.println("*********************************************");
        System.out.println("***  Bem-vindo!!! Por favor, faça login:  ***");
        System.out.println("*********************************************");
        System.out.println("*********************************************");
        System.out.print("User: ");
        String username = sc.nextLine();
        System.out.println("*********************************************");
        System.out.print("Senha: ");
        String password = sc.nextLine();

        User usuarioAutenticado = registoUser.autenticarUser(username, password);

        if (usuarioAutenticado != null) {
            System.out.println("*********************************************");
            System.out.println("Login bem-sucedido! Tipo de user: " + usuarioAutenticado.getTipoUsuario());
            // Chame o método apropriado dependendo do tipo de usuário (Administrador,
            // Consultor, Externo)
            realizarAcoes(usuarioAutenticado);
        } else {
            System.out.println("*********************************************");
            System.out.println("* Login falhou. Verifique suas credenciais. *");
            System.out.println("*********************************************");
        }

    }

    
    /** 
     * @param usuario
     */
    public static void realizarAcoes(User usuario) {
        // Lógica para diferentes ações com base no tipo de usuário
        switch (usuario.getTipoUsuario()) {
            case ADMINISTRADOR:
                System.out.println("You're the Big Boss");
                MenuPrincipalAdmin();

                break;
            case CONSULTOR:
                MenuPrincipalConsultor();

                break;
            case EXTERNO:
                MenuPrincipalExterno();
                break;
            default:
                System.out.println("Tipo de user não reconhecido.");
        }
    }

    public static void realizarRegistro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********************************************");
        System.out.println("*      Bem-vindo ao processo de registro    *");
        System.out.println(" Por favor, forneça as seguintes informações:");
        System.out.println("*********************************************");
        System.out.print("User: ");
        String username = scanner.nextLine();
        System.out.println("*********************************************");

        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.println("*********************************************");

        // Aqui você permite que o usuário escolha o tipo de usuário
        System.out.println("***      Escolha o tipo de utilizador    ***:");
        System.out.println("*********************************************");
        System.out.println("***         1. Administrador              ***");
        System.out.println("***         2. Consultor                  ***");
        System.out.println("***         3. Externo                    ***");
        System.out.println("*********************************************");

        int escolhaTipoUsuario = scanner.nextInt();

        User.TipoUser tipoUser;

        // Converte a escolha do usuário para o enum correspondente
        switch (escolhaTipoUsuario) {
            case 1:
                tipoUser = User.TipoUser.ADMINISTRADOR;
                break;
            case 2:
                tipoUser = User.TipoUser.CONSULTOR;
                break;
            case 3:
                tipoUser = User.TipoUser.EXTERNO;
                break;
            default:
                System.out.println("Escolha inválida. Usuário será registrado como EXTERNO por padrão.");

                tipoUser = User.TipoUser.EXTERNO;
        }

        // Agora você pode chamar o método registrarUsuario
        registoUser.registrarUser(username, password, tipoUser);
        realizarLoginOuRegistro();
    }

}
