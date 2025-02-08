package repository;

import entities.Prescricao;

import java.util.ArrayList;
import java.util.List;

public class PrescricaoRepository {
    private static List<Prescricao> prescricoes = new ArrayList<>();

    public void createPrescricao(Prescricao prescricao) {
        prescricoes.add(prescricao);
    }

    public List<Prescricao> listAllPrescricoes() {
        return prescricoes;
    }

    public Prescricao readPrescricao(int id) {
        for (Prescricao p : prescricoes) {
            if (p.getIdPrescricao() == id) {
                return p;
            }
        }
        return null;
    }

    public void updatePrescricao(Prescricao prescricao) {
        for (int i = 0; i < prescricoes.size(); i++) {
            if (prescricoes.get(i).getIdPrescricao() == prescricao.getIdPrescricao()) {
                prescricoes.set(i, prescricao);
                return;
            }
        }
    }

    public void deletePrescricao(Prescricao prescricao) {
        prescricoes.remove(prescricao);
    }
}
