package entities;

import java.util.ArrayList;

public class Prescricao {
    private int idPrescricao;
    private Consulta consulta;
    private ArrayList<Medicamento> medicamentosPrescritos;//associacao do tipo "tem varios"
    private ArrayList<Exame> examesPrescritos;

    public Prescricao( Consulta consulta) {
        this.consulta = consulta;
        this.medicamentosPrescritos = new ArrayList<>();
        this.examesPrescritos = new ArrayList<>();
    }

    public int getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(int id) {
        this.idPrescricao = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public ArrayList<Medicamento> getMedicamentosPrescritos() {
        return medicamentosPrescritos;
    }

    public void setMedicamentosPrescritos(ArrayList<Medicamento> medicamentosPrescritos) {
        this.medicamentosPrescritos = medicamentosPrescritos;
    }

    public ArrayList<Exame> getExamesPrescritos() {
        return examesPrescritos;
    }

    public void setExamesPrescritos(ArrayList<Exame> examesPrescritos) {
        this.examesPrescritos = examesPrescritos;
    }
}
