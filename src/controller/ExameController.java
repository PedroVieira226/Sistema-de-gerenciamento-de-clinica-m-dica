package controller;

import entities.Exame;
import entities.Medico;
import repository.ExameRepository;

import java.util.Date;
import java.util.List;

public class ExameController {
    private ExameRepository repository;

    ExameController(){
        this.repository = new ExameRepository();
    }

    public void create(String tipoDoExame, Date dataDePrescricao, Date dataDeRealizacao, String resultadoDoExame, Double precoDoExame){
        repository.createExame(new Exame(tipoDoExame, dataDePrescricao, dataDeRealizacao, resultadoDoExame, precoDoExame));
    }

    public Exame read(Integer id){
        return repository.readExame(id);
    }

    public void update(Exame exame){
        repository.updateExame(exame);
    }

    public void delete(Exame exame){
        repository.deleteExame(exame);
    }

    public void delete(Integer id){
        repository.deleteExame(repository.readExame(id));
    }

    public List<Exame> listAll(){
        return repository.listAllExames();
    }

}
