package entities;

import java.util.Date;

public class Paciente extends PessoaFisica{
    private Integer idPaciente;

    public Paciente(String nome, String cpf, Date dataDeNascimento, Integer idPaciente) {
        super(nome, cpf, dataDeNascimento);
        this.idPaciente = idPaciente;
    }
}
