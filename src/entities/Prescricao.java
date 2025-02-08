package entities;

import java.util.ArrayList;

public class Prescricao {
    private static Integer actualId = 0;
    private Integer idPrescricao;
    private ArrayList<Medicamento> medicamentosPrescritos; // Associacao do tipo "tem varios"
    private ArrayList<Exame> examesPrescritos;

    public Prescricao(ArrayList<Medicamento> medicamentosPrescritos, ArrayList<Exame> examesPrescritos) {
        this.idPrescricao = actualId++;
        this.medicamentosPrescritos = medicamentosPrescritos;
        this.examesPrescritos = examesPrescritos;
    }

    public int getIdPrescricao() {
        return idPrescricao;
    }

    public ArrayList<Medicamento> getMedicamentosPrescritos() {
        return medicamentosPrescritos;
    }

    public ArrayList<Exame> getExamesPrescritos() {
        return examesPrescritos;
    }
}
