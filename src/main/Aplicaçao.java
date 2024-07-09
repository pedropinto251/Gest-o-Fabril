package main;

import java.util.Scanner;
import models.Maquina;
import models.RegistoUser;
import models.Producao;
import static models.Login.realizarLoginOuRegistro;

public class Aplicaçao {

    public static RegistoUser registoUser;
    static Scanner sc = new Scanner(System.in);

    public void Iniciar() {

        Maquina.carregarInformacoesMaquinas("FicheirosDB\\maquinas.txt");
        registoUser = new RegistoUser();

        System.out.println("*********************************************");
        System.out.println("***** Bem-Vindo ao Gestor de Produção!  *****");

        realizarLoginOuRegistro();
    }

    // Função que mostra MenuPrincipal

    public static void MenuPrincipalAdmin() {

        int opcao;
        do {

            System.out.println("*********************************************");
            System.out.println("*****          MENU PRINCIPAL           *****");
            System.out.println("*********************************************");
            System.out.println("*****       Selecione uma opção:        *****");
            System.out.println("*********************************************");
            System.out.println("*****          1 - Produções            *****");
            System.out.println("*****          2 - Maquinas             *****");
            System.out.println("*****          3 - About Us             *****");
            System.out.println("*****          4 - Fechar               *****");
            System.out.println("*********************************************");

            opcao = sc.nextInt();
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    ApresentarMenuProducao();
                    break;
                case 2:
                    ApresentarMenuMaquina();
                    break;
                case 3:
                    System.out.println("*********************************************");
                    System.out.println("*****            About Us               *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****          aPROpos^^^2024           *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****              LP1                  *****");
                    break;
                case 4:
                    System.out.println("*********************************************");
                    System.out.println("*****              Goodbye!             *****");
                    System.out.println("*********************************************");
                    Maquina.salvarInformacaoBase("FicheirosDB\\informacao_base.txt");
                    Maquina.gravarPausasEmArquivo();
                    System.exit(0);
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 4);
    }

    // Funcao para apresentar o menu de Produções

    public static void ApresentarMenuProducao() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****          MENU PRODUÇÕES           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****     1 - Ver Produções             *****");
            System.out.println("*****     2 - Editar Produção           *****");
            System.out.println("*****     3 - Adicionar Produção Dia    *****");
            System.out.println("*****     4 - Eliminar Produção         *****");
            System.out.println("*****     5 - Ver Maior/Menor Produção  *****");
            System.out.println("*****     6 - Ver Produções (-30%)      *****");
            System.out.println("*****     7 - Adicionar pausa           *****");
            System.out.println("*****     8 - Ver pausa                 *****");
            System.out.println("*****     9 -  Alterar motivo pausa     *****");
            System.out.println("*****     10 - Ver disponiblidade        *****");
            System.out.println("*****     11 - Alterar dia pausa        *****");
            System.out.println("*****     12 - Voltar ao menu anterior  *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    Producao.listarProducao();
                    break;
                case 2:
                    // EditarProducaoCompleta();
                    Maquina.atualizarCapacidadeProdutivaDiaria();
                    break;
                case 3:
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Qual o dia da semana?");

                    Producao.atualizarProdutividadePorDia(scanner);
                    break;
                case 4:
                    // EliminarProducao();
                    break;

                case 5:
                    Producao.determinarMelhorEMenorDiaProducaoPorMaquina();
                    break;

                case 6:
                    Maquina.gravarMaquinasComBaixaProducao();
                    break;

                case 10:
                    Maquina.exibirDisponibilidadeDasMaquinas();
                    break;
                case 12:
                    MenuPrincipalAdmin();
                    break;

                case 7:

                    final int MAX_MACHINES = 80;
                    Maquina[] maquinasSelecionadas = new Maquina[MAX_MACHINES];
                    int numMaquinasSelecionadas = 0;

                    boolean continuar = true;
                    while (continuar && numMaquinasSelecionadas < MAX_MACHINES) {

                        System.out.println("Digite o código da máquina (ou 'sair' para parar):");
                        String codigoMaquina = sc.nextLine();

                        if (codigoMaquina.equalsIgnoreCase("sair")) {
                            continuar = false;
                        } else {

                            Maquina maquinaExistente = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);

                            if (maquinaExistente == null) {
                                System.out.println("Máquina não encontrada.");
                            } else {
                                maquinasSelecionadas[numMaquinasSelecionadas] = maquinaExistente;
                                numMaquinasSelecionadas++;
                                System.out.println("Máquina adicionada à lista.");
                            }
                        }
                    }

                    // Solicite ao usuário os dados para adicionar uma pausa
                    System.out.println("Digite o dia da semana da pausa:");
                    String diaSemana = sc.nextLine();

                    System.out.println("Digite o motivo da pausa:");
                    String motivo = sc.nextLine();

                    // Adiciona a pausa em todas as máquinas selecionadas
                    for (int i = 0; i < numMaquinasSelecionadas; i++) {
                        maquinasSelecionadas[i].adicionarPausa(diaSemana, motivo);
                    }

                    System.out.println("Pausa adicionada em todas as máquinas.");

                    break;
                case 8:


                    System.out.println("Digite o código da máquina:");
                    String codigoMaquina1 = sc.nextLine();

                    Maquina maquinaExistente1 = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina1);

                    if (maquinaExistente1 == null) {
                        System.out.println("Máquina não encontrada.");
                    } else {
                        maquinaExistente1.verPausas();
                    }

                    break;
                case 9:

                    Maquina.alterarMotivoPausaPorCodigoEData();

                    break;
                case 11:
                    Maquina.alterarDiaPausaPorCodigoEData();
                    break;

                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }

        } while (opcao != 9);

    }

    // Funcao para apresentar o menu das maquinas

    static public void ApresentarMenuMaquina() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****           MENU MÁQUINAS           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****      1 - Ver Máquinas             *****");
            System.out.println("*****      2 - Alt. Tarifa Diária       *****");
            System.out.println("*****      3 - Adicionar Máquina        *****");
            System.out.println("*****      4 - Eliminar Máquina         *****");
            System.out.println("*****      5 - Pesquisar por Máquina    *****");
            System.out.println("*****      6 - Ordem Alfabética         *****");
            System.out.println("*****      7 - Ordem Decrescente        *****");
            System.out.println("*****      8 - Alt. Prod. Diária        *****");
            System.out.println("*****      9 - Voltar ao Menu Anterior  *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {

                case 1:
                    Maquina.listarMaquinasEmMemoria(); // lista todas as máquinas
                    break;
                case 2:
                    // atualizar capacidade produtiva diária de uma máquina com o código
                    Maquina.capProdDiariaMaquina();
                    break;

                // em baixo encontra-se uma parte do código que pode ainda vir ser usado

                /*
                 * System.out.println("Codigo da Maquina:");
                 * Scanner scanner1 = new Scanner(System.in);
                 * String codigoMaquina = scanner1.next();
                 * Maquina maquina = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);
                 * 
                 * if (maquina != null) {
                 * maquina.atualizarCapacidadeProdutivaDiaria(scanner1);
                 * } else {
                 * System.out.println("Máquina não encontrada com o código: " + codigoMaquina);
                 * }
                 * break;
                 */
                case 3:
                    // AdicionarMaquina();
                    break;
                case 4:
                    System.out.println("Código da Máquina:");
                    Scanner scanner = new Scanner(System.in);
                    Maquina.eliminarMaquinaPorCodigo(scanner.next());
                    break;

                case 5:
                    Maquina.PesquisarMaquina();
                    break;
                case 6:

                    Maquina.ordemAlfabeticaPorCodigo();
                    break;
                case 7:
                    // método implementado para organizar a produção semanal por ordem decrescente
                    Maquina.ProdutividadeSemanalDecrescente();
                    break;
                case 8:
                    Maquina.atualizarCapacidadeProdutivaDiaria();
                    break;
                case 9:
                    MenuPrincipalAdmin();
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Válida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 7);
    }

    // Menu Consultor

    public static void MenuPrincipalConsultor() {

        int opcao;
        do {

            System.out.println("*********************************************");
            System.out.println("*****          MENU PRINCIPAL           *****");
            System.out.println("*********************************************");
            System.out.println("*****       Selecione uma opção:        *****");
            System.out.println("*********************************************");
            System.out.println("*****          1 - Produções            *****");
            System.out.println("*****          2 - Maquinas             *****");
            System.out.println("*****          3 - About Us             *****");
            System.out.println("*****          4 - Fechar               *****");
            System.out.println("*********************************************");

            opcao = sc.nextInt();
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    ApresentarMenuProducaoConsultor();
                    break;
                case 2:
                    ApresentarMenuMaquinaConsultor();
                    break;
                case 3:
                    System.out.println("*********************************************");
                    System.out.println("*****            About Us               *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****          aPROpos^^^2024           *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****              LP1                  *****");
                    break;
                case 4:
                    System.out.println("*********************************************");
                    System.out.println("*****              Goodbye!             *****");
                    System.out.println("*********************************************");
                    Maquina.salvarInformacaoBase("FicheirosDB\\informacao_base.txt");
                    Maquina.gravarPausasEmArquivo();
                    System.exit(0);
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 4);
    }

    // Funcao para apresentar o menu de Produções

    public static void ApresentarMenuProducaoConsultor() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****          MENU PRODUÇÕES           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****     1 - Ver Produções             *****");
            System.out.println("*****     2 - Editar Produção           *****");
            System.out.println("*****     3 - Adicionar Produção Dia    *****");
            System.out.println("*****     4 - Ver Maior/Menor Produção  *****");
            System.out.println("*****     5 - Ver Produções (-30%)      *****");
            System.out.println("*****     6 - Adicionar pausa           *****");
            System.out.println("*****     7 - Ver pausa                 *****");
            System.out.println("*****     8 -  Alterar motivo pausa     *****");
            System.out.println("*****     9 - Ver disponiblidade        *****");
            System.out.println("*****     10 - Alterar dia pausa        *****");
            System.out.println("*****     11 - Voltar ao menu anterior   *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    // MenuProducaoListar();
                    Producao.listarProducao();
                    break;
                case 2:
                    // EditarProducaoCompleta();
                    break;
                case 3:
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Qual o dia da semana?");

                    Producao.atualizarProdutividadePorDia(scanner);
                    break;

                case 4:
                    Producao.determinarMelhorEMenorDiaProducaoPorMaquina();
                    break;

                case 5:
                    Maquina.gravarMaquinasComBaixaProducao();
                    break;

                case 6:

                    final int MAX_MACHINES = 80;
                    Maquina[] maquinasSelecionadas = new Maquina[MAX_MACHINES];
                    int numMaquinasSelecionadas = 0;

                    boolean continuar = true;
                    while (continuar && numMaquinasSelecionadas < MAX_MACHINES) {

                        System.out.println("Digite o código da máquina (ou 'sair' para parar):");
                        String codigoMaquina = sc.nextLine();

                        if (codigoMaquina.equalsIgnoreCase("sair")) {
                            continuar = false;
                        } else {

                            Maquina maquinaExistente = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);

                            if (maquinaExistente == null) {
                                System.out.println("Máquina não encontrada.");
                            } else {
                                maquinasSelecionadas[numMaquinasSelecionadas] = maquinaExistente;
                                numMaquinasSelecionadas++;
                                System.out.println("Máquina adicionada à lista.");
                            }
                        }
                    }

                    // Solicite ao usuário os dados para adicionar uma pausa
                    System.out.println("Digite o dia da semana da pausa:");
                    String diaSemana = sc.nextLine();

                    System.out.println("Digite o motivo da pausa:");
                    String motivo = sc.nextLine();

                    // Adiciona a pausa em todas as máquinas selecionadas
                    for (int i = 0; i < numMaquinasSelecionadas; i++) {
                        maquinasSelecionadas[i].adicionarPausa(diaSemana, motivo);
                    }

                    System.out.println("Pausa adicionada em todas as máquinas.");

                    break;
                case 7:
                    System.out.println("Digite o código da máquina:");
                    String codigoMaquina1 = sc.nextLine();

                    Maquina maquinaExistente1 = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina1);

                    if (maquinaExistente1 == null) {
                        System.out.println("Máquina não encontrada.");
                    } else {
                        maquinaExistente1.verPausas();
                    }

                    break;
                case 8:

                    Maquina.alterarMotivoPausaPorCodigoEData();

                    break;
                case 9:
                    Maquina.exibirDisponibilidadeDasMaquinas();
                    break;
                case 10:
                    Maquina.alterarDiaPausaPorCodigoEData();
                    break;
                case 11:
                    MenuPrincipalConsultor();
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 6);
    }

    // Funcao para apresentar o menu das maquinas

    static public void ApresentarMenuMaquinaConsultor() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****           MENU MÁQUINAS           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****      1 - Ver Máquinas             *****");
            System.out.println("*****      2 - Alt. Tarifa Diária       *****");
            System.out.println("*****      3 - Pesquisar por Máquina    *****");
            System.out.println("*****      4 - Ordem Alfabética         *****");
            System.out.println("*****      5 - Ordem Decrescente        *****");
            System.out.println("*****      6 - Alt. Prod. Diária        *****");
            System.out.println("*****      7 - Voltar ao Menu Anterior  *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {

                case 1:
                    // teste listar maquina em memoria
                    Maquina.listarMaquinasEmMemoria(); // lista todas as máquinas
                    break;
                case 2:
                    // atualizar capacidade produtiva diária de uma máquina com o código

                    Maquina.capProdDiariaMaquina();
                    break;

                // em baixo encontra-se uma parte do código que pode ainda vir ser usado

                /*
                 * System.out.println("Codigo da Maquina:");
                 * Scanner scanner1 = new Scanner(System.in);
                 * String codigoMaquina = scanner1.next();
                 * Maquina maquina = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);
                 * 
                 * if (maquina != null) {
                 * maquina.atualizarCapacidadeProdutivaDiaria(scanner1);
                 * } else {
                 * System.out.println("Máquina não encontrada com o código: " + codigoMaquina);
                 * }
                 * break;
                 */

                case 3:
                    Maquina.PesquisarMaquina();
                    break;
                case 4:

                    Maquina.ordemAlfabeticaPorCodigo();
                    break;
                case 5:
                    // método implementado para organizar a produção semanal por ordem decrescente
                    Maquina.ProdutividadeSemanalDecrescente();
                    break;
                case 6:
                    Maquina.atualizarCapacidadeProdutivaDiaria();
                    break;
                case 7:

                    MenuPrincipalConsultor();
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Válida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 7);
    }

    // Menu Externo

    public static void MenuPrincipalExterno() {

        int opcao;
        do {

            System.out.println("*********************************************");
            System.out.println("*****          MENU PRINCIPAL           *****");
            System.out.println("*********************************************");
            System.out.println("*****       Selecione uma opção:        *****");
            System.out.println("*********************************************");
            System.out.println("*****          1 - Produções            *****");
            System.out.println("*****          2 - Maquinas             *****");
            System.out.println("*****          3 - About Us             *****");
            System.out.println("*****          4 - Fechar               *****");
            System.out.println("*********************************************");

            opcao = sc.nextInt();
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    ApresentarMenuProducaoExterno();
                    break;
                case 2:
                    ApresentarMenuMaquinaExterno();
                    break;
                case 3:
                    System.out.println("*********************************************");
                    System.out.println("*****            About Us               *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****          aPROpos^^^2024           *****");
                    System.out.println("*****                                   *****");
                    System.out.println("*****              LP1                  *****");
                    break;
                case 4:
                    System.out.println("*********************************************");
                    System.out.println("*****              Goodbye!             *****");
                    System.out.println("*********************************************");
                    Maquina.salvarInformacaoBase("FicheirosDB\\informacao_base.txt");
                    Maquina.gravarPausasEmArquivo();
                    System.exit(0);
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 4);
    }

    // Funcao para apresentar o menu de Produções

    public static void ApresentarMenuProducaoExterno() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****          MENU PRODUÇÕES           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****     1 - Ver Produções             *****");
            System.out.println("*****     2 - Ver Maior/Menor Produção  *****");
            System.out.println("*****     3 - Ver Produções (-30%)      *****");
            System.out.println("*****     4 - Ver pausa                 *****");
            System.out.println("*****     5 - Ver disponiblidade        *****");
            System.out.println("*****     6 - Voltar ao menu anterior   *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {
                case 1:
                    // MenuProducaoListar();
                    Producao.listarProducao();
                    break;

                case 2:
                    Producao.determinarMelhorEMenorDiaProducaoPorMaquina();
                    break;

                case 3:
                    Maquina.gravarMaquinasComBaixaProducao();
                    break;
                case 4:

                    final int MAX_MACHINES = 80;
                    Maquina[] maquinasSelecionadas = new Maquina[MAX_MACHINES];
                    int numMaquinasSelecionadas = 0;

                    boolean continuar = true;
                    while (continuar && numMaquinasSelecionadas < MAX_MACHINES) {

                        System.out.println("Digite o código da máquina (ou 'sair' para parar):");
                        String codigoMaquina = sc.nextLine();

                        if (codigoMaquina.equalsIgnoreCase("sair")) {
                            continuar = false;
                        } else {

                            Maquina maquinaExistente = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);

                            if (maquinaExistente == null) {
                                System.out.println("Máquina não encontrada.");
                            } else {
                                maquinasSelecionadas[numMaquinasSelecionadas] = maquinaExistente;
                                numMaquinasSelecionadas++;
                                System.out.println("Máquina adicionada à lista.");
                            }
                        }
                    }

                    // Solicite ao usuário os dados para adicionar uma pausa
                    System.out.println("Digite o dia da semana da pausa:");
                    String diaSemana = sc.nextLine();

                    System.out.println("Digite o motivo da pausa:");
                    String motivo = sc.nextLine();

                    // Adiciona a pausa em todas as máquinas selecionadas
                    for (int i = 0; i < numMaquinasSelecionadas; i++) {
                        maquinasSelecionadas[i].adicionarPausa(diaSemana, motivo);
                    }

                    System.out.println("Pausa adicionada em todas as máquinas.");

                    break;
                case 5:

                    Maquina.alterarMotivoPausaPorCodigoEData();

                    break;
                case 6:
                    MenuPrincipalExterno();
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Valida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 5);
    }

    // Funcao para apresentar o menu das maquinas

    static public void ApresentarMenuMaquinaExterno() {

        int opcao;
        System.out.println("*********************************************");
        System.out.println("*****           MENU MÁQUINAS           *****");
        System.out.println("*********************************************");

        do {
            System.out.println("*********************************************");
            System.out.println("*****      1 - Ver Máquinas             *****");
            System.out.println("*****      2 - Pesquisar por Máquina    *****");
            System.out.println("*****      3 - Ordem Alfabética         *****");
            System.out.println("*****      4 - Ordem Decrescente        *****");
            System.out.println("*****      5 - Voltar ao Menu Anterior  *****");
            System.out.println("*********************************************");

            opcao = (sc.nextInt());
            sc.nextLine();

            // chamar a funcao especifica a que o utilizador escolher

            switch (opcao) {

                case 1:
                    // teste listar maquina em memoria
                    Maquina.listarMaquinasEmMemoria(); // lista todas as máquinas
                    break;

                // em baixo encontra-se uma parte do código que pode ainda vir ser usado

                /*
                 * System.out.println("Codigo da Maquina:");
                 * Scanner scanner1 = new Scanner(System.in);
                 * String codigoMaquina = scanner1.next();
                 * Maquina maquina = Maquina.EncontrarMaquinaPorCodigo(codigoMaquina);
                 * 
                 * if (maquina != null) {
                 * maquina.atualizarCapacidadeProdutivaDiaria(scanner1);
                 * } else {
                 * System.out.println("Máquina não encontrada com o código: " + codigoMaquina);
                 * }
                 * break;
                 */

                case 2:
                    Maquina.PesquisarMaquina();
                    break;
                case 3:

                    Maquina.ordemAlfabeticaPorCodigo();
                    break;
                case 4:
                    // método implementado para organizar a produção semanal por ordem decrescente
                    Maquina.ProdutividadeSemanalDecrescente();
                    break;
                case 5:

                    MenuPrincipalExterno();
                    break;
                default:
                    System.out.println("*********************************************");
                    System.out.println("*****    Insira uma Opção Válida!       *****");
                    System.out.println("*********************************************");
                    break;
            }
        } while (opcao != 5);
    }
}
