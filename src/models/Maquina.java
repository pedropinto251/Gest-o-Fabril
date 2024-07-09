package models;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import static models.Producao.DIAS_SEMANA;

public class Maquina {

    // array dias da semana
    private static final int MAX_DATAS = 7; // Assumindo uma semana com 7 dias
    private int[] producaoDiaria = new int[MAX_DATAS];
    private int[] desvioProducaoDiario = new int[MAX_DATAS];
    private static RegistarPausa[] pausas;
    private static int numPausas;
    private static final int MAX_PAUSAS = 10;

    private String codigoMaquina;
    private int tarifaDia;
    private int producaoSemana;
    private int desvioProducaoDia;
    private int desvioProducaoSemana;
    private static String responsavel;
    private static boolean visivelParaTodos;

    // Construtor
    public Maquina(String codigoMaquina, int tarifaDia, int producaoSemana, int desvioProducaoDia,
            int desvioProducaoSemana, int[] producaoDiaria, int[] desvioProducaoDiario, String responsavel,
            boolean visivelParaTodos, int maxPausas) {
        this.codigoMaquina = codigoMaquina;
        this.tarifaDia = tarifaDia;
        this.producaoSemana = producaoSemana;
        this.desvioProducaoDia = desvioProducaoDia;
        this.desvioProducaoSemana = desvioProducaoSemana;
        this.producaoDiaria = producaoDiaria;
        this.desvioProducaoDiario = desvioProducaoDiario;
        this.responsavel = responsavel;
        this.visivelParaTodos = visivelParaTodos;
        this.pausas = new RegistarPausa[MAX_PAUSAS];
        this.numPausas = 0;
    }
    
    /** 
     * @return boolean
     */
    // GnS

    public boolean isVisivelParaTodos() {
        return visivelParaTodos;
    }

    public void setVisivelParaTodos(boolean visivelParaTodos) {
        this.visivelParaTodos = visivelParaTodos;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public static boolean usuarioEhResponsavel(User usuarioAtual, Maquina maquina) {
        return usuarioAtual != null && maquina != null
                && maquina.getResponsavel().equalsIgnoreCase(usuarioAtual.getUsername());
    }
    // Método para verificar se o usuário atual é o responsável pela máquina
    // public boolean usuarioEhResponsavel(User usuarioAtual) {
    // return responsavel.equalsIgnoreCase(usuarioAtual.nome);
    // }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public int[] getProducaoDia() {
        return producaoDiaria;
    }

    public void setProducaoDia(int[] producaoDia) {
        this.producaoDiaria = producaoDia;
    }

    public int getProducaoSemana() {
        return producaoSemana;
    }

    public void setProducaoSemana(int producaoSemana) {
        this.producaoSemana = producaoSemana;
    }

    public int getDesvioProducaoDia() {
        return desvioProducaoDia;
    }

    public void setDesvioProducaoDia(int desvioProducaoDia) {
        this.desvioProducaoDia = desvioProducaoDia;
    }

    public int getDesvioProducaoSemana() {
        return desvioProducaoSemana;
    }

    public void setDesvioProducaoSemana(int desvioProducaoSemana) {
        this.desvioProducaoSemana = desvioProducaoSemana;
    }

    public int getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(int tarifaDia) {
        this.tarifaDia = tarifaDia;
    }
    // Puxar String

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Máquina:\n");
        stringBuilder.append("Código: ").append(getCodigoMaquina()).append("\n");
        stringBuilder.append("Tarifa Diária: ").append(getTarifaDia()).append("\n");
        stringBuilder.append("Produção Diária:\n");
        stringBuilder.append("Máquina ").append(getCodigoMaquina()).append(" (Responsável: ").append(responsavel)
                .append("):\n");

        String[] diasSemana = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };

        int[] producaoDiaria = getProducaoDia();
        for (int dia = 0; dia < producaoDiaria.length; dia++) {
            // Use o array de dias da semana para exibir o nome do dia
            stringBuilder.append(diasSemana[dia]).append(": ").append(producaoDiaria[dia]).append("\n");
        }

        stringBuilder.append("Desvio Diário: ").append(getDesvioProducaoDia()).append("\n");
        stringBuilder.append("Produção Semanal: ").append(getProducaoSemana()).append("\n");
        stringBuilder.append("Desvio Semanal: ").append(getDesvioProducaoSemana()).append("\n");

        return stringBuilder.toString();
    }

    public static void carregarInformacoesMaquinas(String filePath) {
        final int NUMERO_DIAS_SEMANA = 7;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 11) { // Agora são 11 partes, incluindo o responsável
                    String codigo = partes[0].trim();
                    int tarifaDia = Integer.parseInt(partes[1].trim());

                    int[] producaoDiariaArray = new int[NUMERO_DIAS_SEMANA];
                    int[] desvioProducaoDiarioArray = new int[NUMERO_DIAS_SEMANA];

                    for (int i = 0; i < NUMERO_DIAS_SEMANA; i++) {
                        producaoDiariaArray[i] = Integer.parseInt(partes[i + 2].trim());
                    }

                    int desvioProducaoDia = Integer.parseInt(partes[9].trim());
                    int producaoSemana = Integer.parseInt(partes[8].trim());
                    int desvioProducaoSemana = Integer.parseInt(partes[7].trim());

                    String responsavel = partes[10].trim(); // Novo campo responsável

                    Maquina maquina = new Maquina(codigo, tarifaDia, producaoSemana, desvioProducaoDia,
                            desvioProducaoSemana, producaoDiariaArray, desvioProducaoDiarioArray, responsavel,
                            visivelParaTodos, desvioProducaoSemana);
                    adicionarMaquina(maquina);
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static final int MAX_MAQUINAS = 80;
    private static Maquina[] Maquinas = new Maquina[MAX_MAQUINAS];
    private static int totalMaquinas = 0;

    public static void adicionarMaquina(Maquina maquina) {
        if (totalMaquinas < MAX_MAQUINAS) {
            Maquinas[totalMaquinas++] = maquina;
        } else {
            System.out.println("Limite máximo de máquinas atingido.");
        }
    }

    public static Maquina[] getMaquinas() {
        return Maquinas;
    }

    public static int getTotalMaquinas() {
        return totalMaquinas;
    }

    // Teste listar maquina para verificar se a maquina estava a ser removida

    /**
     * Lista todas as máquinas atualmente em memória.
     *
     * Este método percorre o array de máquinas em memória e imprime no console as
     * informações
     * de cada máquina não nula. As informações são exibidas em um formato
     * específico.
     */
    public static void listarMaquinasEmMemoria() {
        // maquina.carregarInformacoesMaquinas("FicheirosDB\\maquinas.txt");

        // Exibir título no console
        System.out.println("------ Máquinas ------");

        // Iterar sobre as máquinas em memória
        for (int i = 0; i < totalMaquinas; i++) {
            if (Maquinas[i] != null) {
                // Exibir informações da máquina no console
                System.out.println(Maquinas[i]);
                System.out.println("----------------------");
            }
        }
    }

    public static void capProdDiariaMaquina() { // atualizar a capacidade produtiva (tarifa diária) com o código da
                                                // máquina (4. ponto 2)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Código da Máquina:");
        String codigoMaquina = scanner.next();

        Maquina maquina = EncontrarMaquinaPorCodigo(codigoMaquina);

        if (maquina != null) {
            System.out.println("Defina o valor de produção diária da máquina:");
            int prodDiaria = scanner.nextInt();
            scanner.nextLine();

            maquina.setTarifaDia(prodDiaria);
            System.out.println("Capacidade produtiva diária atualizada com sucesso para a máquina " + codigoMaquina);
        } else {
            System.out.println("Máquina não encontrada com o código: " + codigoMaquina);
        }
    }

    public static void atualizarCapacidadeProdutivaDiaria() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o código da máquina (ou 000 para terminar):");
        String codigoMaquinaAtualizacao = scanner.nextLine();

        while (!codigoMaquinaAtualizacao.equals("000")) {
            Maquina maquina = EncontrarMaquinaPorCodigo(codigoMaquinaAtualizacao);

            if (maquina != null) {
                int novaCapacidade = 0;
                boolean inputValido = false;

                while (!inputValido) {
                    try {
                        System.out.println("Insira o novo valor para a capacidade produtiva diária:");
                        novaCapacidade = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        inputValido = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Por favor, insira um valor válido (número inteiro).");
                        scanner.nextLine(); // Limpar o buffer do scanner
                    }
                }

                System.out.println(
                        "Insira o dia da semana para a atualização:Segunda, Terca, Quarta, Quinta, Sexta, Sabado, Domingo");
                String diaSemana = scanner.nextLine();

                int indexDiaSemana = Arrays.asList(DIAS_SEMANA).indexOf(diaSemana);

                if (indexDiaSemana >= 0 && indexDiaSemana < MAX_DATAS) {
                    maquina.producaoDiaria[indexDiaSemana] = novaCapacidade;
                    System.out.println("Capacidade produtiva diária atualizada com sucesso para a máquina "
                            + maquina.codigoMaquina);
                } else {
                    System.out.println("Dia da semana inválido.");
                }
            } else {
                System.out.println("Máquina não encontrada com o código: " + codigoMaquinaAtualizacao);
            }

            System.out.println("Insira o código da máquina (ou 000 para terminar):");
            codigoMaquinaAtualizacao = scanner.nextLine();
        }
    }

    public static void eliminarMaquinaPorCodigo(String codigo) { // tarefa 4. ponto 3
        // System.out.println("Codigo da Maquina");
        for (int i = 0; i < totalMaquinas; i++) {
            if (Maquinas[i] != null && Maquinas[i].getCodigoMaquina().equals(codigo)) {
                for (int j = i; j < totalMaquinas - 1; j++) {
                    Maquinas[j] = Maquinas[j + 1];
                }
                Maquinas[totalMaquinas - 1] = null;
                totalMaquinas--;
                System.out.println("Máquina removida com sucesso.");
                return;
            }
        }
        System.out.println("Máquina não encontrada com o código: " + codigo);
    }

 /**
 * Realiza a pesquisa de uma máquina com base no código inserido pelo usuário.
 * Exibe as informações detalhadas da máquina, incluindo código, produção diária,
 * produção semanal, desvio de produção diário e desvio de produção semanal.
 * Caso a máquina não seja encontrada, exibe uma mensagem indicando a ausência.
 * Este método é parte da tarefa 4, ponto 1.
 */
    public static void PesquisarMaquina() { // tarefa 4. ponto 1
        Scanner sc = new Scanner(System.in);

        System.out.println("*********************************************");
        System.out.println("*****   PESQUISAR MÁQUINA POR CÓDIGO    *****");
        System.out.println("*********************************************");
        System.out.println("*****  Introduza o código da máquina:   *****");
        String codigoMaquina = sc.nextLine();

        Maquina maquinaEncontrada = EncontrarMaquinaPorCodigo(codigoMaquina);

        if (maquinaEncontrada != null) {
            System.out.println("Informações da Máquina:");
            System.out.println("Código: " + maquinaEncontrada.getCodigoMaquina());

            // Exibindo produção diária
            int[] producaoDiaria = maquinaEncontrada.getProducaoDia();
            System.out.println("Produção Diária:");
            for (int dia = 0; dia < producaoDiaria.length; dia++) {
                System.out.println("Dia " + (dia + 1) + ": " + producaoDiaria[dia]);
            }

            System.out.println("Produção Semanal: " + maquinaEncontrada.getProducaoSemana());
            System.out.println("Desvio Produção Diário: " + maquinaEncontrada.getDesvioProducaoDia());
            System.out.println("Desvio Produção Semanal: " + maquinaEncontrada.getDesvioProducaoSemana());
        } else {
            System.out.println("Máquina não encontrada. Verifique o número inserido.");
        }
    }

    // Função auxiliar para encontrar uma máquina por número

    public static Maquina EncontrarMaquinaPorCodigo(String codigoMaquina) {
        for (int i = 0; i < totalMaquinas; i++) {
            if (Maquinas[i].getCodigoMaquina().equals(codigoMaquina)) {
                return Maquinas[i];
            }
        }
        return null;
    }

    // Método para ordenar as máquinas consoante a produção semanal por ordem
    // decrescente

    public static void ProdutividadeSemanalDecrescente() { // tarefa 5. ponto 2
        Maquina[] maquinas = getMaquinas();
        int n = getTotalMaquinas();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (maquinas[j + 1] != null && maquinas[j].getProducaoSemana() < maquinas[j + 1].getProducaoSemana()) {
                    // Trocar as máquinas de posição se a máquina atual for menor que a próxima
                    Maquina temp = maquinas[j];
                    maquinas[j] = maquinas[j + 1];
                    maquinas[j + 1] = temp;

                    // Adicionar prints para depuração
                    System.out.println("Troca: " + maquinas[j].getProducaoSemana() + " <-> "
                            + maquinas[j + 1].getProducaoSemana());
                    // Imprimir o estado atual da ordem
                    System.out.print("Estado atual: ");
                    for (Maquina maquina : maquinas) {
                        if (maquina != null) {
                            System.out.print(maquina.getProducaoSemana() + " - " + maquina.getCodigoMaquina() + " |");
                        }
                    }
                    System.out.println();
                }
            }
        }

        // Imprimir a lista ordenada
        System.out.println("Máquinas ordenadas por produtividade semanal (decrescente):");
        for (Maquina maquina : maquinas) {
            if (maquina != null) {
                System.out.println(
                        "Produção Semanal: " + maquina.getProducaoSemana() + "(" + maquina.codigoMaquina + ")");
                System.out.println(); // Adicionar uma linha em branco entre as máquinas
            }
        }
    }


/**
 * Ordena o array de máquinas em ordem alfabética com base nos códigos das máquinas.
 * Este método utiliza o algoritmo de ordenação Bubble Sort para realizar a ordenação.
 * As máquinas são ordenadas diretamente no array original.
 * Este método é parte da tarefa 5, ponto 1.
 */
    public static void ordemAlfabeticaPorCodigo() { // tarefa 5. ponto 1
        Maquina[] maquinas = getMaquinas();
        int n = getTotalMaquinas();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (maquinas[j + 1] != null
                        && maquinas[j].getCodigoMaquina().compareTo(maquinas[j + 1].getCodigoMaquina()) > 0) {
                    // Trocar as máquinas de posição se a máquina atual for maior que a próxima
                    Maquina temp = maquinas[j];
                    maquinas[j] = maquinas[j + 1];
                    maquinas[j + 1] = temp;

                    // Adicionar prints para depuração
                    System.out.println(
                            "Troca: " + maquinas[j].getCodigoMaquina() + " <-> " + maquinas[j + 1].getCodigoMaquina());
                    // Imprimir o estado atual da ordem
                    System.out.print("Estado atual: ");
                    for (Maquina maquina : maquinas) {
                        if (maquina != null) {
                            System.out.print(maquina.getCodigoMaquina() + " ");
                        }
                    }
                    System.out.println();
                }
            }
        }

        // Imprimir a lista ordenada
        System.out.println("Máquinas ordenadas por código da máquina (ordem alfabética):");
        for (Maquina maquina : maquinas) {
            if (maquina != null) {
                System.out.println("Código da Máquina: " + maquina.getCodigoMaquina());
                System.out.println("Capacidade Produtiva Diária: " + maquina.getTarifaDia());
                System.out.println(); // Adicionar uma linha em branco entre as máquinas
            }
        }
    }

    // gravar maquinas com baixa produçao, inferior a 30%
/**
 * Este método grava em um arquivo as informações das máquinas cuja produção semanal
 * é inferior a 30% da produção esperada, com base na tarifa diária.
 * As informações gravadas incluem o código da máquina, a produção semanal atual
 * e a produção semanal esperada.
 * O arquivo de saída é "FicheirosDB\\producao_Inferior30.txt".
 *
 * Nota: Certifique-se de ter as permissões necessárias para gravar no diretório
 * especificado e de lidar com exceções de E/S (IOException) adequadamente.
 * 
 * 
 * De diferete talvez  em vez de concatenar strings manualmente, usaria String.format para tornar o código mais limpo e legível.
 *
 * @throws IOException Se ocorrer um erro de entrada/saída ao gravar no arquivo.
 */
    public static void gravarMaquinasComBaixaProducao() {
        try (BufferedWriter baixaProducao = new BufferedWriter(
                new FileWriter("FicheirosDB\\producao_Inferior30.txt"))) {
            double percentagemLimite = 0.3;

            for (Maquina maquina : Maquinas) {
                if (maquina != null) {
                    double producaoSemanalEsperada = maquina.getTarifaDia() * 7 * (1 - percentagemLimite);

                    if (maquina.getProducaoSemana() < producaoSemanalEsperada) {
                        baixaProducao.write("Código da Máquina: " + maquina.getCodigoMaquina());
                        baixaProducao.newLine();
                        baixaProducao.write("Produção Semanal: " + maquina.getProducaoSemana());
                        baixaProducao.newLine();
                        baixaProducao.write("Produção Semanal Esperada: " + producaoSemanalEsperada);
                        baixaProducao.newLine();
                        baixaProducao.newLine(); // Adicionar uma linha em branco entre as máquinas
                    }
                }
            }

            System.out.println("Máquinas com baixa produção gravadas em maquinas_com_baixa_producao.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // tarefa 4 aluno 4--Pedro

    /**
     * Salva informações básicas sobre máquinas em um arquivo.
     *
     * Este método escreve no arquivo fornecido as informações básicas sobre cada
     * máquina,
     * incluindo o código da máquina e sua capacidade produtiva diária.
     * Nao mudaria nada visto que foi o proprio aluno 3 que o fez para poder
     * realizar a tarefa 12
     * 
     * @param nomeArquivo O nome do arquivo no qual as informações serão salvas.
     * @throws IOException Se ocorrer um erro de E/S ao tentar salvar as informações
     *                     no arquivo.
     */
    public static void salvarInformacaoBase(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {

            // Escrever cabeçalho no arquivo
            writer.write("Código da Máquina, Capacidade Produtiva Diária");
            writer.newLine();

            // Iterar sobre as máquinas e escrever seus dados
            for (Maquina maquina : Maquinas) {
                if (maquina != null) {
                    // Converter a capacidade diária para uma string
                    String capacidadeDiariaString = Arrays.toString(maquina.getProducaoDia());

                    // Escrever os dados da máquina no arquivo
                    writer.write(maquina.getCodigoMaquina() + ", " + capacidadeDiariaString);
                    writer.newLine(); // Nova linha para a próxima entrada
                }
            }

            // Exibir mensagem de sucesso no console
            System.out.println("Informação base salva com sucesso em " + nomeArquivo);
        } catch (IOException e) {
            // Tratar exceção de E/S exibindo uma mensagem de erro no console
            System.err.println("Erro ao salvar informação base: " + e.getMessage());
        }
    }

    private static final String[] DIAS_SEMANA = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado",
            "Domingo" };

    // Pausas

    /**
 * Adiciona uma pausa à máquina. A pausa é registrada com um dia da semana e um motivo.
 * Este método verifica se ainda há espaço no array de pausas antes de adicionar uma nova pausa.
 *
 * @param diaSemana O dia da semana em que a pausa ocorreu.
 * @param motivo O motivo da pausa.
 */
    public void adicionarPausa(String diaSemana, String motivo) {
        if (numPausas < pausas.length) {
            RegistarPausa pausa = new RegistarPausa(diaSemana, motivo, this.codigoMaquina);
            pausas[numPausas] = pausa;
            numPausas++;
        } else {
            System.out.println("Limite de pausas atingido. Não é possível adicionar mais pausas.");
        }
    }

    /**
 * Remove uma pausa associada à máquina com base no código da máquina.
 * Este método percorre o array de pausas, localiza a pausa com o código da máquina fornecido
 * e a remove do array.
 *
 * @param codigoMaquina O código da máquina para a qual a pausa deve ser removida.
 */
    public void removerPausaPorCodigo(String codigoMaquina) {
        for (int i = 0; i < numPausas; i++) {
            // if (pausas[i].getCodigoMaquina().equals(codigoMaquina)) {
            // Remova a pausa encontrada
            for (int j = i; j < numPausas - 1; j++) {
                pausas[j] = pausas[j + 1];
            }
            pausas[numPausas - 1] = null; // Último elemento é nulo
            numPausas--;
            return;
        }
    }
    // System.out.println("Pausa não encontrada para a máquina com código: " +
    // codigoMaquina);
    // }

    public void verPausas() {
        if (numPausas == 0) {
            System.out.println("A máquina não possui pausas registradas.");
        } else {
            System.out.println("Pausas da máquina " + codigoMaquina + ":");
            for (int i = 0; i < numPausas; i++) {
                System.out.println("Dia da semana: " + pausas[i].getDiaSemana() +
                        ", Motivo: " + pausas[i].getMotivo());
            }
        }
    }

    private boolean temPausaNoDia(String diaSemana) {
        // Implemente a lógica para verificar se há pausa neste dia
        // Pode percorrer o array de pausas ou usar uma estrutura de dados adequada
        // Retorna true se houver pausa, false caso contrário
        // Exemplo simplificado:
        for (RegistarPausa pausa : pausas) {
            if (pausa.getDiaSemana().equals(diaSemana)) {
                return true;
            }
        }
        return false;
    }

    public boolean temPausaNoDiaV(String diaSemana) {
        for (RegistarPausa pausa : pausas) {
            if (pausa != null && pausa.getDiaSemana().equalsIgnoreCase(diaSemana)) {
                return true;
            }
        }
        return false;
    }

    public static void exibirDisponibilidadeDasMaquinas() {
        System.out.println("Disponibilidade das Máquinas por Dia da Semana:");
        System.out.print("Código Máquina\t");

        // Imprimir cabeçalho com os dias da semana
        for (String diaSemana : DIAS_SEMANA) {
            System.out.print(diaSemana + "\t");
        }

        System.out.println(); // Nova linha após o cabeçalho

        // Iterar sobre as máquinas
        for (Maquina maquina : Maquinas) {
            if (maquina != null) {
                System.out.print(maquina.getCodigoMaquina() + "\t\t");

                // Iterar sobre os dias da semana
                for (String diaSemana : DIAS_SEMANA) {
                    // Verificar se há pausa para o dia
                    boolean temPausa = maquina.temPausaNoDiaV(diaSemana);

                    // Se houver pausa, imprima "X"
                    System.out.print(temPausa ? "X\t" : "V\t");
                }

                System.out.println(); // Nova linha após cada máquina
            }
        }
    }

    // tarefa 8 aluno 3

    public static void alterarMotivoPausaPorCodigoEData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o código da máquina:");
        String codigoMaquina = scanner.nextLine();

        // Encontrar a máquina no array
        Maquina maquinaParaAlterar = null;
        for (Maquina maquina : Maquinas) {
            if (maquina != null && maquina.getCodigoMaquina().equals(codigoMaquina)) {
                maquinaParaAlterar = maquina;
                break;
            }
        }

        if (maquinaParaAlterar != null) {
            System.out.println("Digite o dia da semana da pausa que deseja alterar o motivo:");
            String diaSemana = scanner.nextLine();

            // Encontrar a pausa na máquina
            RegistarPausa pausaParaAlterar = null;
            for (RegistarPausa pausa : maquinaParaAlterar.pausas) {
                if (pausa != null && pausa.getDiaSemana().equalsIgnoreCase(diaSemana)) {
                    pausaParaAlterar = pausa;
                    break;
                }
            }

            if (pausaParaAlterar != null) {
                System.out.println("Digite o novo motivo para a pausa no dia " + diaSemana + ":");
                String novoMotivo = scanner.nextLine();

                // Atualizar o motivo da pausa
                pausaParaAlterar.setMotivo(novoMotivo);
                System.out.println("Motivo da pausa atualizado com sucesso.");
            } else {
                System.out.println("Pausa não encontrada para o dia " + diaSemana);
            }
        } else {
            System.out.println("Máquina não encontrada com o código " + codigoMaquina);
        }
    }

    public static void alterarDiaPausaPorCodigoEData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o código da máquina:");
        String codigoMaquina = scanner.nextLine();

        // Encontrar a máquina no array
        Maquina maquinaParaAlterar = null;
        for (Maquina maquina : Maquinas) {
            if (maquina != null && maquina.getCodigoMaquina().equals(codigoMaquina)) {
                maquinaParaAlterar = maquina;
                break;
            }
        }

        if (maquinaParaAlterar != null) {
            System.out.println("Digite o dia da semana da pausa que deseja alterar:");
            String diaSemanaAntigo = scanner.nextLine();

            // Encontrar a pausa na máquina
            RegistarPausa pausaParaAlterar = null;
            for (RegistarPausa pausa : maquinaParaAlterar.pausas) {
                if (pausa != null && pausa.getDiaSemana().equalsIgnoreCase(diaSemanaAntigo)) {
                    pausaParaAlterar = pausa;
                    break;
                }
            }

            if (pausaParaAlterar != null) {
                System.out.println("Digite o novo dia da semana para a pausa:");
                String novoDiaSemana = scanner.nextLine();

                // Atualizar o dia da pausa
                pausaParaAlterar.setDiaSemana(novoDiaSemana);
                System.out.println("Dia da pausa atualizado com sucesso.");
            } else {
                System.out.println("Pausa não encontrada para o dia " + diaSemanaAntigo);
            }
        } else {
            System.out.println("Máquina não encontrada com o código " + codigoMaquina);
        }
    }

    // gravar pausas para ficheiro tarefa 10

    /**
 * Grava as informações de pausas em um arquivo de texto chamado "pausas.txt" na pasta "FicheirosDB".
 * Certifica-se de que a pasta existe; se não existir, cria-a.
 * Para cada registro de pausa não nulo, escreve as informações no arquivo.
 * Este método utiliza um PrintWriter para gravar as informações no arquivo.
 * O caminho completo para o arquivo é "FicheirosDB/pausas.txt".
 */
    public static void gravarPausasEmArquivo() {
        // Caminho para a pasta FicheirosDB
        String pastaFicheirosDB = "FicheirosDB";

        // Certifique-se de que a pasta existe; crie-a se necessário
        Path caminhoPastaFicheirosDB = Paths.get(pastaFicheirosDB);
        if (!caminhoPastaFicheirosDB.toFile().exists()) {
            caminhoPastaFicheirosDB.toFile().mkdir();
        }

        // Caminho completo para o arquivo "pausas.txt"
        String nomeArquivo = "FicheirosDB/pausas.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (RegistarPausa pausa : pausas) {
                if (pausa != null) {
                    writer.println(pausa.toString()); // Supondo que você implementou o método toString() em
                                                      // RegistarPausa
                }
            }
            System.out.println("Pausas gravadas com sucesso no arquivo " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gravar pausas no arquivo: " + e.getMessage());
        }
    }

    // tarefa 10 aluno 2

    public static void carregarPausasDeArquivo(String caminhoArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                // Divida a linha em partes usando um separador, por exemplo, vírgula
                String[] partes = linha.split(",");

                // Crie uma instância de RegistarPausa com base nas partes
                String diaSemana = partes[0].trim();
                String motivo = partes[1].trim();
                String codigoMaquina = partes[2].trim();

                RegistarPausa pausa = new RegistarPausa(diaSemana, motivo, codigoMaquina);

                // Adicione a pausa à máquina
                Maquina.adicionarPausaArquivo(pausa);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void adicionarPausaArquivo(RegistarPausa pausa) {
        if (numPausas < pausas.length) {
            pausas[numPausas] = pausa;
            numPausas++;
        } else {
            System.out.println("Limite de pausas atingido. Não é possível adicionar mais pausas.");
        }
    }

}
