package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Producao {

    private String codigoMaquina;
    private int producaoDia;
    private String diaSemana;
    private int producaoSemana;
    private int desvioProducaoDia;
    private int desvioProducaoSemana;

    public Producao(String codigoMaquina, int producaoDia, String diaSemana, int producaoSemana,
            int desvioProducaoDia, int desvioProducaoSemana) {
        this.codigoMaquina = codigoMaquina;
        this.producaoDia = producaoDia;
        this.diaSemana = diaSemana;
        this.producaoSemana = producaoSemana;
        this.desvioProducaoDia = desvioProducaoDia;
        this.desvioProducaoSemana = desvioProducaoSemana;
    }

    
    /** 
     * @return String
     */
    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public int getProducaoDia() {
        return producaoDia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public int getProducaoSemana() {
        return producaoSemana;
    }

    public int getDesvioProducaoDia() {
        return desvioProducaoDia;
    }

    public int getDesvioProducaoSemana() {
        return desvioProducaoSemana;
    }

    @Override
    public String toString() {
        return "Maquina: /n"
                + "Codigo: "
                + getCodigoMaquina()
                + "\n Produção Diária: "
                + getProducaoDia()
                + "\n Desvio Diário: "
                + getDesvioProducaoDia()
                + "\n Produção Semanal: "
                + getProducaoSemana()
                + "\n Desvio Semanal: "
                + getDesvioProducaoSemana()
                + " \n diaSemana: "
                + getDiaSemana();
    }

    public static final String[] DIAS_SEMANA = { "segunda", "terca", "quarta", "quinta", "sexta", "sabado", "domingo" };

    public static void atualizarProdutividadePorDia(Scanner scanner) {
        Maquina[] Maquinas = Maquina.getMaquinas();

        try {
            // Perguntar qual dia da semana deseja atualizar
            System.out.println("Em qual dia da semana deseja atualizar a produtividade?");
            System.out.println("Escolha entre: segunda, terça, quarta, quinta, sexta, sábado, domingo");

            String diaSemana = scanner.nextLine().toLowerCase();

            // Verificar se o dia da semana fornecido é válido
            if (Arrays.asList(DIAS_SEMANA).contains(diaSemana)) {
                // Construir o caminho do arquivo com base no dia da semana
                String filePath = "FicheirosDB\\" + diaSemana + ".txt";

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        String[] partes = linha.split(",");
                        if (partes.length == 2) {
                            String codigo = partes[0].trim();
                            int produtividadeDia = Integer.parseInt(partes[1].trim());

                            // Encontrar a máquina pelo código
                            for (Maquina maquina : Maquinas) {
                                if (maquina != null && maquina.getCodigoMaquina().equals(codigo)) {
                                    // Atualizar a produtividade para o dia específico
                                    maquina.getProducaoDia()[Arrays.asList(DIAS_SEMANA)
                                            .indexOf(diaSemana)] = produtividadeDia;
                                    System.out.println("Produtividade atualizada para a máquina " + codigo + " no dia "
                                            + diaSemana);
                                    break; // Uma vez que encontrou a máquina, não precisa continuar procurando
                                }
                            }
                        } else {
                            System.out.println("Formato inválido na linha: " + linha);
                        }
                    }
                }
            } else {
                System.out.println("Dia da semana inválido.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Teste listar produção para verificar se as alterações estavam a ser feitas

    public static void listarProducao() {
        Maquina[] Maquinas = Maquina.getMaquinas();
        int totalMaquinas = Maquina.getTotalMaquinas();

        // Defina um array de strings para os dias da semana
        String[] diasSemana = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };

        System.out.println("------ Produção ------");
        for (int i = 0; i < totalMaquinas; i++) {
            System.out.println("Máquina: " + Maquinas[i].getCodigoMaquina());
            System.out.println("Produção Diária:");

            int[] producaoDiaria = Maquinas[i].getProducaoDia();
            for (int dia = 0; dia < producaoDiaria.length; dia++) {
                // Use o array de dias da semana para exibir o nome do dia
                System.out.println(diasSemana[dia] + ": " + producaoDiaria[dia]);
            }

            System.out.println("Produção Semanal: " + Maquinas[i].getProducaoSemana());
            System.out.println("Desvio Diário: " + Maquinas[i].getDesvioProducaoDia());
            System.out.println("Desvio Semanal: " + Maquinas[i].getDesvioProducaoSemana());
            System.out.println("----------------------");
        }
    }

    public static void determinarMelhorEMenorDiaProducaoPorMaquina() {
        Maquina[] maquinas = Maquina.getMaquinas();

        // Array de dias da semana
        String[] DIAS_SEMANA = { "segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo" };

        for (Maquina maquina : maquinas) {
            if (maquina != null) {
                int[] producaoDia = maquina.getProducaoDia();

                // Encontrar o dia com o máximo de horas de produção para a máquina atual
                int indiceMaximo = encontrarIndiceMaximo(producaoDia);
                String diaMaximo = DIAS_SEMANA[indiceMaximo];
                int maximoHoras = producaoDia[indiceMaximo];

                // Encontrar o dia com o mínimo de horas de produção para a máquina atual
                int indiceMinimo = encontrarIndiceMinimo(producaoDia);
                String diaMinimo = DIAS_SEMANA[indiceMinimo];
                int minimoHoras = producaoDia[indiceMinimo];

                // Exibe os resultados para a máquina atual
                System.out.println("Máquina: " + maquina.getCodigoMaquina());
                System.out.println(
                        "Dia com o maior total de horas de produção: " + diaMaximo + " (" + maximoHoras + " horas)");
                System.out.println(
                        "Dia com o menor total de horas de produção: " + diaMinimo + " (" + minimoHoras + " horas)");
                System.out.println("-------------------------");
            }
        }
    }

    private static int encontrarIndiceMaximo(int[] array) {
        int indiceMaximo = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[indiceMaximo]) {
                indiceMaximo = i;
            }
        }
        return indiceMaximo;
    }

    private static int encontrarIndiceMinimo(int[] array) {
        int indiceMinimo = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[indiceMinimo]) {
                indiceMinimo = i;
            }
        }
        return indiceMinimo;
    }

}
