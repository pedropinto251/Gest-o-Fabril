package main;

import models.Maquina;
import models.RegistoUser;

public class Runner {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        Maquina.carregarInformacoesMaquinas("FicheirosDB\\maquinas.txt");
        Maquina.carregarPausasDeArquivo("FicheirosDB\\pausas.txt");
        Aplicaçao run = new Aplicaçao();
        RegistoUser registoUser = new RegistoUser();
        run.Iniciar();
    }
}
