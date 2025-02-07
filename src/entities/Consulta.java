package entities;

import enums.StatusConsulta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Consulta {
    private static Integer actualId = 0;
    private int idConsulta;
    private String tipoConsulta;
    private Date dataDaConsulta;
    private LocalDateTime horarioDeInicio;
    private LocalDateTime duracaoDaConsulta;
    private Paciente pacienteDaConsulta;//associação de classe com a classe paiente, relação "tem um"
    private Medico medicoDaConsulta;
    private Double valorConsulta;
    private Prescricao prescricaoDaConsulta;
    private StatusConsulta statusConsultaDaConsulta;

    public Consulta(String tipoConsulta, Date dataDaConsulta, LocalDateTime horarioDeInicio,
                    LocalDateTime duracaoDaConsulta, Paciente pacienteDaConsulta, Medico medicoDaConsulta, Double valorConsulta, Prescricao prescricaoDaConsulta, StatusConsulta statusConsultaDaConsulta) {
        this.tipoConsulta = tipoConsulta;
        this.dataDaConsulta = dataDaConsulta;
        this.horarioDeInicio = horarioDeInicio;
        this.duracaoDaConsulta = duracaoDaConsulta;
        this.pacienteDaConsulta = pacienteDaConsulta;
        this.medicoDaConsulta = medicoDaConsulta;
        this.valorConsulta = valorConsulta;
        this.prescricaoDaConsulta = prescricaoDaConsulta;
        this.statusConsultaDaConsulta = statusConsultaDaConsulta;
        this.idConsulta = actualId++;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Date getDataDaConsulta() {
        return dataDaConsulta;
    }

    public void setDataDaConsulta(Date dataDaConsulta) {
        this.dataDaConsulta = dataDaConsulta;
    }

    public LocalDateTime getHorarioDeInicio() {
        return horarioDeInicio;
    }

    public void setHorarioDeInicio(LocalDateTime horarioDeInicio) {
        this.horarioDeInicio = horarioDeInicio;
    }

    public LocalDateTime getDuracaoDaConsulta() {
        return duracaoDaConsulta;
    }

    public void setDuracaoDaConsulta(LocalDateTime duracaoDaConsulta) {
        this.duracaoDaConsulta = duracaoDaConsulta;
    }

    public Paciente getPacienteDaConsulta() {
        return pacienteDaConsulta;
    }

    public void setPacienteDaConsulta(Paciente pacienteDaConsulta) {
        this.pacienteDaConsulta = pacienteDaConsulta;
    }

    public Medico getMedicoDaConsulta() {
        return medicoDaConsulta;
    }

    public void setMedicoDaConsulta(Medico medicoDaConsulta) {
        this.medicoDaConsulta = medicoDaConsulta;
    }

    public Double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public Prescricao getPrescricaoDaConsulta() {
        return prescricaoDaConsulta;
    }

    public void setPrescricaoDaConsulta(Prescricao prescricaoDaConsulta) {
        this.prescricaoDaConsulta = prescricaoDaConsulta;
    }

    public StatusConsulta getStatusConsultaDaConsulta() {
        return statusConsultaDaConsulta;
    }

    public void setStatusConsultaDaConsulta(StatusConsulta statusConsultaDaConsulta) {
        this.statusConsultaDaConsulta = statusConsultaDaConsulta;
    }
}
