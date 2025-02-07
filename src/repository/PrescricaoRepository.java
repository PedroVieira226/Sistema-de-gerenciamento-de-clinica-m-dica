package repository;

import entities.Prescricao;

import java.util.ArrayList;
import java.util.List;

public class PrescricaoRepository {
    private List<Prescricao> prescricoes = new ArrayList<>();
    private int idPrescricao = 1;
    /**
     * Adiciona uma nova prescrição ao repositório.
     *
     * @param prescricao A prescrição a ser adicionada.
     */

    public void createPrescricao(Prescricao prescricao) {
        prescricoes.add(prescricao);
        prescricao.setIdPrescricao(idPrescricao);
        idPrescricao++;
    }

    /**
     * Retorna uma lista de todas as prescrições cadastradas.
     *
     * @return Uma lista de prescrições.
     */
    public List<Prescricao> listAllPrescricoes() {
        return prescricoes;
    }

    /**
     * Busca uma prescrição pelo ID.
     *
     * @param id O ID da prescrição a ser buscada.
     * @return A prescrição encontrada, ou null se não existir.
     */
    public Prescricao readPrescricao(int id) {
        for (Prescricao p : prescricoes) {
            if (p.getIdPrescricao() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de uma prescrição existente.
     *
     * @param prescricao A prescrição com os dados atualizados.
     */
    public void updatePrescricao(Prescricao prescricao) {
        for (int i = 0; i < prescricoes.size(); i++) {
            if (prescricoes.get(i).getIdPrescricao() == prescricao.getIdPrescricao()) {
                prescricoes.set(i, prescricao);
                return;
            }
        }
    }

    /**
     * Remove uma prescrição do repositório.
     *
     * @param prescricao A prescrição a ser removida.
     */
    public void deletePrescricao(Prescricao prescricao) {
        prescricoes.remove(prescricao);
    }
}
