package repository;

import entities.Medicamento;

import java.util.ArrayList;
import java.util.List;
/**
 * Adiciona um novo medicamento ao repositório.
 *
 * @param medicamento O medicamento a ser adicionado.
 */

public class MedicamentoRepository {
    private static List<Medicamento> medicamentos = new ArrayList<>();

    /**
     * Retorna uma lista de todos os medicamentos cadastrados.
     *
     * @return Uma lista de medicamentos.
     */
    public void createMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }


    /**
     * Busca um medicamento pelo ID.
     *
     * @param id O ID do medicamento a ser buscado.
     * @return O medicamento encontrado, ou null se não existir.
     */
    public List<Medicamento> listAllMedicamentos() {
        return medicamentos;
    }

    public Medicamento readMedicamento(int id) {
        for (Medicamento m : medicamentos) {
            if (m.getIdMedicamento() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um medicamento existente.
     *
     * @param medicamento O medicamento com os dados atualizados.
     */
    public void updateMedicamento(Medicamento medicamento) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getIdMedicamento() == medicamento.getIdMedicamento()) {
                medicamentos.set(i, medicamento);
                return;
            }
        }
    }

    /**
     * Remove um medicamento do repositório.
     *
     * @param medicamento O medicamento a ser removido.
     */
    public void deleteMedicamento(Medicamento medicamento) {
        medicamentos.remove(medicamento);
    }
}
