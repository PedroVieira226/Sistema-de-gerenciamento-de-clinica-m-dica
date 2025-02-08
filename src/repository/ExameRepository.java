package repository;

import entities.Exame;

import java.util.ArrayList;
import java.util.List;

public class ExameRepository {
    private static List<Exame> exames = new ArrayList<>();

    /**
     * Adiciona um novo exame ao repositório.
     *
     * @param exame O exame a ser adicionado.
     */
    public void createExame(Exame exame) {
        exames.add(exame);
    }

    /**
     * Retorna uma lista de todos os exames cadastrados.
     *
     * @return Uma lista de exames.
     */
    public List<Exame> listAllExames() {
        return exames;
    }


    /**
     * Busca um exame pelo ID.
     *
     * @param id O ID do exame a ser buscado.
     * @return O exame encontrado, ou null se não existir.
     */
    public Exame readExame(int id) {
        for (Exame e : exames) {
            if (e.getIdExame() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um exame existente.
     *
     * @param exame O exame com os dados atualizados.
     */
    public void updateExame(Exame exame) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).getIdExame() == exame.getIdExame()) {
                exames.set(i, exame);
                return;
            }
        }
    }

    /**
     * Remove um exame do repositório.
     *
     * @param exame O exame a ser removido.
     */
    public void deleteExame(Exame exame) {
        exames.remove(exame);
    }
}
