package repository;

import entities.Consulta;
import excptions.EspecialidadeInvalidaException;
import excptions.HorarioIndisponivelException;
import excptions.PacientePossuiConsultaNoMesmoDIa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável por gerenciar a persistência de objetos do tipo Consulta.
 */

public class ConsultaRepository {
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private int idConsulta = 1;

    /**
     * Adiciona uma nova consulta ao repositório.
     *
     * @param consulta A consulta a ser adicionada.
     * @throws EspecialidadeInvalidaException Se o médico não possuir a especialidade necessária.
     * @throws HorarioIndisponivelException Se o horário já estiver ocupado.
     * @throws PacientePossuiConsultaNoMesmoDia Se o paciente já tiver uma consulta no mesmo dia.
     */

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


    /**
     * Retorna uma lista de todas as consultas cadastradas.
     *
     * @return Uma lista de consultas.
     */

    public List<Consulta> listAllConsultas() {
        return consultas;
    }

    /**
     * Busca uma consulta pelo ID.
     *
     * @param id O ID da consulta a ser buscada.
     * @return A consulta encontrada, ou null se não existir.
     */

    public Consulta readConsulta(int id) {
        for (Consulta c : consultas) {
            if (c.getIdConsulta() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de uma consulta existente.
     *
     * @param consulta A consulta com os dados atualizados.
     */

    public void updateConsulta(Consulta consulta) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getIdConsulta() == consulta.getIdConsulta()) {
                consultas.set(i, consulta);
                return;
            }
        }
    }

    /**
     * Remove uma consulta do repositório.
     *
     * @param consulta A consulta a ser removida.
     */

    public void deleteConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }

    /**
     * Verifica se o médico já possui uma consulta no mesmo horário.
     *
     * @param consulta A consulta a ser verificada.
     * @param consultas A lista de consultas cadastradas.
     * @return true se o médico já tiver uma consulta no mesmo horário, false caso contrário.
     */

    public Boolean medicoPossuiConsultaNoHoario(Consulta consulta,
                                                ArrayList<Consulta> consultas) {
        for(Consulta c : consultas){
            if(c.getDataDaConsulta() == consulta.getDataDaConsulta()
                    && c.getHorarioDeInicio() == consulta.getHorarioDeInicio()){
                return true;
            }
        }return false;
    }

    /**
     * Verifica se o paciente já possui uma consulta no mesmo dia.
     *
     * @param consulta A consulta a ser verificada.
     * @param consultas A lista de consultas cadastradas.
     * @return true se o paciente já tiver uma consulta no mesmo dia, false caso contrário.
     */

    public Boolean pacientePossuiConsultaNoDia(Consulta consulta,
                                               ArrayList<Consulta> consultas){
        for(Consulta c : consultas){
            if(c.getDataDaConsulta() == consulta.getDataDaConsulta()){
                return true;
            }
        }return false;}

}



