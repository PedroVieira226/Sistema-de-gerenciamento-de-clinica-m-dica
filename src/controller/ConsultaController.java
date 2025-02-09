package controller;

import entities.*;
import enums.StatusConsulta;
import excptions.EspecialidadeInvalidaException;
import excptions.HorarioIndisponivelException;
import excptions.PacientePossuiConsultaNoMesmoDIa;
import repository.ConsultaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaController {
    private ConsultaRepository repository;
    private MedicoController medicoController;
    private PacienteController pacienteController;
    private PrescricaoController prescricaoController;

    public ConsultaController(){
        repository = new ConsultaRepository();
    }

    private boolean medicoNaoPossuiEspecialidade(String tipoConsulta, String medicoCpf){
        return !tipoConsulta.equals(medicoController.read(medicoCpf).getEspecialidade());
    }

    private boolean medicoPossuiConsultaConflitante(LocalDateTime horarioInicio, String cpfMedico){
        for (Consulta consulta : repository.listAllConsultas()){
            if (consulta.getMedicoDaConsulta().getCpf().equals(cpfMedico) && consulta.getHorarioDeInicio() == horarioInicio){
                return true;
            }
        }
        return false;
    }

    private boolean pacientePossuiConsultaNoDia(String pacienteCpf){
        for (Consulta consulta : repository.listAllConsultas()){
            if (consulta.getPacienteDaConsulta().getCpf().equals(pacienteCpf)){
                return true;
            }
        }
        return false;
    }

    public void create(String tipoConsulta, Date dataConsulta, LocalDateTime horarioInicio, LocalDateTime duracao,
                       String pacienteCpf, String cpfMedico, double valorConsulta, StatusConsulta statusConsulta,
                       ArrayList<Integer> medicamentosPrescritosIds, ArrayList<Integer> examesPrescritosIds){

        if(!medicoController.medicoExiste(cpfMedico) || !pacienteController.pacienteExiste(pacienteCpf)){
            if (medicoNaoPossuiEspecialidade(tipoConsulta, cpfMedico)){
                throw new EspecialidadeInvalidaException("Médico não possui a especialidade necessária para a consulta!");
            }
            if (medicoPossuiConsultaConflitante(horarioInicio, cpfMedico)){
                throw new HorarioIndisponivelException("Médico não esta disponível neste horário!");
            }
            if (pacientePossuiConsultaNoDia(pacienteCpf)){
                throw new PacientePossuiConsultaNoMesmoDIa("O paciente ja possui uma consulta nesse dia!");
            }

            repository.createConsulta(new Consulta(tipoConsulta, dataConsulta, horarioInicio, duracao, pacienteController.read(pacienteCpf),
                    medicoController.read(pacienteCpf), valorConsulta, prescricaoController.createFromIds(medicamentosPrescritosIds, examesPrescritosIds), statusConsulta));
        } else{
            throw new IllegalArgumentException("Os ID(s) de médico ou paciente informados, não correspondem a de objetos existentes.");
        }
    }

    public Consulta read(int id){
        return repository.readConsulta(id);
    }

    public void update(Consulta consulta){
        repository.updateConsulta(consulta);
    }

    public void delete(Consulta consulta){
        repository.deleteConsulta(consulta);
    }

    public void delete(int id){
        repository.deleteConsulta(repository.readConsulta(id));
    }


    public List<Consulta> listAll(){
        return repository.listAllConsultas();
    }

}
