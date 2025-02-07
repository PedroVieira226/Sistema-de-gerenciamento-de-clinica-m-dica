package repository;

import entities.Exame;

import java.util.ArrayList;
import java.util.List;

public class ExameRepository {
    private List<Exame> exames = new ArrayList<>();

    public void createExame(Exame exame) {
        exames.add(exame);
    }

    public List<Exame> listAllExames() {
        return exames;
    }

    public Exame readExame(int id) {
        for (Exame e : exames) {
            if (e.getIdExame() == id) {
                return e;
            }
        }
        return null;
    }

    public void updateExame(Exame exame) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).getIdExame() == exame.getIdExame()) {
                exames.set(i, exame);
                return;
            }
        }
    }

    public void deleteExame(Exame exame) {
        exames.remove(exame);
    }
}
