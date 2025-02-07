package repository;

import entities.Medico;
import excptions.CpfJaCadastradoException;
import excptions.EspecialidadeInvalidaException;

import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {
    private List<Medico> medicos = new ArrayList<>();

    public void createMedico(Medico medico) throws CpfJaCadastradoException, EspecialidadeInvalidaException {
        medicos.add(medico);
    }

    public List<Medico> listAllMedicos() {
        return medicos;
    }

    public Medico readMedico(int id) {
        for (Medico m : medicos) {
            if (m.getIdMedico() == id) {
                return m;
            }
        }
        return null;
    }

    public Medico readMedico(String cpf){
        for (Medico m : medicos){
            if (m.getCpf().equals(cpf)){
                return m;
            }
        }
        return null;
    }

    public void updateMedico(Medico medico) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getIdMedico() == medico.getIdMedico()) {
                medicos.set(i, medico);
                return;
            }
        }
    }

    public void deleteMedico(Medico medico) {
        medicos.remove(medico);
    }
}
