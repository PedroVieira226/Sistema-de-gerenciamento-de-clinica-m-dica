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

    public void createConsulta(Consulta consulta){
        consultas.add(consulta);
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
}



