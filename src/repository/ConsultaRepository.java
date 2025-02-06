package repository;

import entities.Consulta;
import excptions.EspecialidadeInvalidaException;
import excptions.HorarioIndisponivelException;
import excptions.PacientePossuiConsultaNoMesmoDIa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private int idConsulta = 1;

    public void createConsulta(Consulta consulta){
        if(consulta.getMedicoDaConsulta().getEspecialidade() != consulta.getTipoConsulta()){
            throw new EspecialidadeInvalidaException("Médico não possui a especialidade necessária" +
                    "para a consulta!");
        }
        if(medicoPossuiConsultaNoHoario(consulta, consultas)){
            throw new HorarioIndisponivelException("Médico não esta disponível neste horário!");
        }
        if(pacientePossuiConsultaNoDia(consulta, consultas)){
            throw new PacientePossuiConsultaNoMesmoDIa("O paciente ja possui uma consulta nesse dia!");
        }


        consultas.add(consulta);
        consulta.setIdConsulta(idConsulta);
        idConsulta++;
    }

    public List<Consulta> listAllConsultas() {
        return consultas;
    }

    public Consulta readConsulta(int id) {
        for (Consulta c : consultas) {
            if (c.getIdConsulta() == id) {
                return c;
            }
        }
        return null;
    }

    public void updateConsulta(Consulta consulta) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getIdConsulta() == consulta.getIdConsulta()) {
                consultas.set(i, consulta);
                return;
            }
        }
    }

    public void deleteConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }

    public Boolean medicoPossuiConsultaNoHoario(Consulta consulta,
                                                ArrayList<Consulta> consultas) {
        for(Consulta c : consultas){
            if(c.getDataDaConsulta() == consulta.getDataDaConsulta()
                    && c.getHorarioDeInicio() == consulta.getHorarioDeInicio()){
                return true;
            }
        }return false;
    }
    public Boolean pacientePossuiConsultaNoDia(Consulta consulta,
                                               ArrayList<Consulta> consultas){
        for(Consulta c : consultas){
            if(c.getDataDaConsulta() == consulta.getDataDaConsulta()){
                return true;
            }
        }return false;}

}



