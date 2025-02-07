package repository;

import entities.Paciente;
import excptions.CpfJaCadastradoException;

import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private List<Paciente> pacientes = new ArrayList<>();

    public void createPaciente(Paciente paciente) throws CpfJaCadastradoException {
        pacientes.add(paciente);
    }

    public List<Paciente> listAllPacientes() {
        return pacientes;
    }

    public Paciente readPaciente(int id) {
        for (Paciente p : pacientes) {
            if (p.getIdPaciente() == id) {
                return p;
            }
        }
        return null;
    }

    public Paciente readPaciente(String cpf){
        for (Paciente p : pacientes){
            if (p.getCpf().equals(cpf)){
                return p;
            }
        }
        return null;
    }

    public void updatePaciente(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getIdPaciente() == paciente.getIdPaciente()) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }

    public void deletePaciente(Paciente paciente) {
        pacientes.remove(paciente);
    }
}
