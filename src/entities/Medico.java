package entities;

import java.util.Date;

public class Medico extends PessoaFisica{
    private static Integer actualId = 0;

    private Integer idMedico;
    private String crm;
    private String especialidade;


    public Medico(String nome, String cpf, Date dataDeNascimento, String crm, String especialidade) {
        super(nome, cpf, dataDeNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
        this.idMedico = actualId++;
    }

    @Override
    public void adicionarConsultaAoHistorico(Consulta consulta) {
        getHistorico().add(consulta);
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
