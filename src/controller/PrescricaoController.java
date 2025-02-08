package controller;

import entities.*;
import repository.PrescricaoRepository;

import java.util.ArrayList;
import java.util.List;

public class PrescricaoController {
    private PrescricaoRepository repository;
    private MedicamentoController medicamentoController;
    private ExameController exameController;

    PrescricaoController(){
        repository = new PrescricaoRepository();
    }

    public Prescricao create(ArrayList<Medicamento> medicamentosPrescritos, ArrayList<Exame> examesPrescritos){
        Prescricao prescricao = new Prescricao(medicamentosPrescritos, examesPrescritos);
        repository.createPrescricao(prescricao);
        return prescricao;
    }

    public Prescricao createFromIds(ArrayList<Integer> medicamentosPrescritosIds, ArrayList<Integer> examesPrescritosIds){
        ArrayList<Medicamento> medicamentosPrescritos = new ArrayList<>();
        for (Integer id : medicamentosPrescritosIds){
            if(medicamentoController.read(id) != null){
                medicamentosPrescritos.add(medicamentoController.read(id));
            }
        }

        ArrayList<Exame> examesPrescritos = new ArrayList<>();
        for (Integer id : examesPrescritosIds){
            if(exameController.read(id) != null){
                examesPrescritos.add(exameController.read(id));
            }
        }

        return create(medicamentosPrescritos, examesPrescritos);
    }

    public Prescricao read(Integer id){
        return repository.readPrescricao(id);
    }

    public void update(Prescricao prescricao){
        repository.updatePrescricao(prescricao);
    }

    public void delete(Prescricao prescricao){
        repository.deletePrescricao(prescricao);
    }

    public void delete(Integer id){
        repository.deletePrescricao(repository.readPrescricao(id));
    }

    public List<Prescricao> listAll(){
        return repository.listAllPrescricoes();
    }
}
