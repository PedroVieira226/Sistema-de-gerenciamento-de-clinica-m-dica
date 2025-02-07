package repository;

import entities.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {
    private List<Medicamento> medicamentos = new ArrayList<>();

    public void createMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

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

    public void updateMedicamento(Medicamento medicamento) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getIdMedicamento() == medicamento.getIdMedicamento()) {
                medicamentos.set(i, medicamento);
                return;
            }
        }
    }

    public void deleteMedicamento(Medicamento medicamento) {
        medicamentos.remove(medicamento);
    }
}
