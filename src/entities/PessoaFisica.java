package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class PessoaFisica {//classe abstrata que possui atributos que as classes Paciente e Médico tem em comum(Herança)
    private String nome;
    private String cpf;
    private Date dataDeNascimento;
    private ArrayList<Object> historico;

    public PessoaFisica(String nome, String cpf, Date dataDeNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.historico = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public ArrayList<Object> getHistorico() {
        return historico;
    }
}
