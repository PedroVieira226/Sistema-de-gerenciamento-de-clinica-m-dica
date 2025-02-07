package entities;

import java.util.Date;

public class Exame {
    private static Integer actualId = 0;
    private int idExame;
    private String tipoDoExame;
    private Date dataDePrescricao;
    private Date dataDeRealizacao;
    private String resultadoDoExame;
    private Double precoDoExame;

    public Exame(String tipoDoExame, Date dataDePrescricao, Date dataDeRealizacao, String resultadoDoExame, Double precoDoExame) {
        this.idExame = actualId++;
        this.tipoDoExame = tipoDoExame;
        this.dataDePrescricao = dataDePrescricao;
        this.dataDeRealizacao = dataDeRealizacao;
        this.resultadoDoExame = resultadoDoExame;
        this.precoDoExame = precoDoExame;
    }

    public int getIdExame() {
        return idExame;
    }


    public String getTipoDoExame() {
        return tipoDoExame;
    }

    public void setTipoDoExame(String tipoDoExame) {
        this.tipoDoExame = tipoDoExame;
    }

    public Date getDataDePrescricao() {
        return dataDePrescricao;
    }

    public void setDataDePrescricao(Date dataDePrescricao) {
        this.dataDePrescricao = dataDePrescricao;
    }

    public Date getDataDeRealizacao() {
        return dataDeRealizacao;
    }

    public void setDataDeRealizacao(Date dataDeRealizacao) {
        this.dataDeRealizacao = dataDeRealizacao;
    }

    public String getResultadoDoExame() {
        return resultadoDoExame;
    }

    public void setResultadoDoExame(String resultadoDoExame) {
        this.resultadoDoExame = resultadoDoExame;
    }

    public Double getPrecoDoExame() {
        return precoDoExame;
    }

    public void setPrecoDoExame(Double precoDoExame) {
        this.precoDoExame = precoDoExame;
    }
}
