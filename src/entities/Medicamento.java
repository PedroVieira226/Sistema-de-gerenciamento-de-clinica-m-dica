package entities;

public class Medicamento {
    private static Integer actualId = 0;
    private int idMedicamento;
    private String nome;

    public Medicamento(String nome) {
        this.idMedicamento = actualId++;
        this.nome = nome;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
