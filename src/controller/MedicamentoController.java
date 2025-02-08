package controller;

import entities.Medicamento;
import repository.MedicamentoRepository;

public class MedicamentoController {
    private MedicamentoRepository repository;

    MedicamentoController(){
        repository = new MedicamentoRepository();
    }

    public void create(String nome){
        repository.createMedicamento(new Medicamento(nome));
    }

    public void update(Medicamento medicamento){
        repository.updateMedicamento(medicamento);
    }

    public Medicamento read(Integer id){
        return repository.readMedicamento(id);
    }

    public void delete(Medicamento medicamento){
        repository.deleteMedicamento(medicamento);
    }

    public void delete(Integer id){
        repository.deleteMedicamento(repository.readMedicamento(id));
    }
}
