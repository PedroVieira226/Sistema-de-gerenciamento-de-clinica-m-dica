package controller;

import entities.Medicamento;
import entities.Medico;
import excptions.CpfJaCadastradoException;
import repository.MedicoRepository;

import java.util.Date;
import java.util.List;

public class MedicoController {
    private MedicoRepository repository;

    public MedicoController(){
        this.repository = new MedicoRepository();
    }

    public boolean medicoExiste(String cpf){
        for (Medico medico : repository.listAllMedicos()){
            if (cpf.equals(medico.getCpf())){
                return true;
            }
        }
        return false;
    }

    public void create(String nome, String cpf, Date dataDeNascimento, String crm, String especialidade){
        if (medicoExiste(cpf)){
            throw new CpfJaCadastradoException("Já existe um médico com este CPF.");
        }
        repository.createMedico(new Medico(nome, cpf, dataDeNascimento, crm, especialidade));
    }
    public List<Medico> listAll(){
        return repository.listAllMedicos();
    }

    public Medico read(int id){
        return repository.readMedico(id);
    }

    public Medico read(String cpf){
        return repository.readMedico(cpf);
    }

    public void update(Medico medico){
        repository.updateMedico(medico);
    }

    public void delete(Medico medico){
        repository.deleteMedico(medico);
    }

    public void delete(int id){
        repository.deleteMedico(repository.readMedico(id));
    }
}
