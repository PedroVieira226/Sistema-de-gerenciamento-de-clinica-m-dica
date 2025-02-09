package controller;

import entities.Medico;
import entities.Paciente;
import excptions.CpfJaCadastradoException;
import repository.PacienteRepository;

import java.util.Date;
import java.util.List;

public class PacienteController {
    private PacienteRepository repository;

    public PacienteController(){
        repository = new PacienteRepository();
    }

    public boolean pacienteExiste(String cpf){
        for (Paciente paciente : repository.listAllPacientes()){
            if (cpf.equals(paciente.getCpf())){
                return true;
            }
        }
        return false;
    }

    public void create(String nome, String cpf, Date dataDeNascimento){
        if (pacienteExiste(cpf)){
            throw new CpfJaCadastradoException("Já existe um paciente com este CPF.");
        }
        repository.createPaciente(new Paciente(nome, cpf, dataDeNascimento));
    }
    public List<Paciente> listAll(){
        return repository.listAllPacientes();
    }

    public Paciente read(int id){
        return repository.readPaciente(id);
    }

    public Paciente read(String cpf){
        return repository.readPaciente(cpf);
    }

    public void update(Paciente paciente){
        repository.updatePaciente(paciente);
    }

    public void delete(Paciente paciente){
        repository.deletePaciente(paciente);
    }

    public void delete(int id){
        repository.deletePaciente(repository.readPaciente(id));
    }
}
