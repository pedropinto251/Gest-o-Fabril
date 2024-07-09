package models;

public class RegistarPausa {
    private String diaSemana;
    private String motivo;
    private String codigoMaquina;

    public RegistarPausa(String diaSemana, String motivo, String codigoMaquina) {
        this.diaSemana = diaSemana;
        this.motivo = motivo;
        this.codigoMaquina = codigoMaquina;
    }

    
    /** 
     * @return String
     */
    public String getDiaSemana() {
        return diaSemana;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String novoMotivo) {
        this.motivo = novoMotivo;
    }

    public void setDiaSemana(String novoDiaSemana) {
        this.diaSemana = novoDiaSemana;
    }

    @Override
    public String toString() {
        return "Dia: " + diaSemana + ", Motivo: " + motivo + ", Código da Máquina: " + codigoMaquina;
    }
}
