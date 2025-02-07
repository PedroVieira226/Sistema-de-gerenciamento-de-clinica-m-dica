package repository;

import entities.Paciente;
import excptions.CpfJaCadastradoException;

import java.util.ArrayList;
import java.util.List;
/**
 * Repositório responsável por gerenciar a persistência de objetos do tipo Paciente.
 */

public class PacienteRepository {
    private List<Paciente> pacientes = new ArrayList<>();

    /**
     * Adiciona um novo paciente ao repositório.
     *
     * @param paciente O paciente a ser adicionado.
     * @throws CpfJaCadastradoException Se já existir um paciente com o mesmo CPF.
     */

    public void createPaciente(Paciente paciente) throws CpfJaCadastradoException {
        // Verifica se já existe um paciente com o mesmo CPF
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(paciente.getCpf())) {
                throw new CpfJaCadastradoException("Já existe um paciente com este CPF.");
            }
        }

        pacientes.add(paciente);
    }

    /**
     * Retorna uma lista de todos os pacientes cadastrados.
     *
     * @return Uma lista de pacientes.
     */

    public List<Paciente> listAllPacientes() {
        return pacientes;
    }

    /**
     * Busca um paciente pelo ID.
     *
     * @param id O ID do paciente a ser buscado.
     * @return O paciente encontrado, ou null se não existir.
     */
    public Paciente readPaciente(int id) {
        for (Paciente p : pacientes) {
            if (p.getIdPaciente() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um paciente existente.
     *
     * @param paciente O paciente com os dados atualizados.
     */

    public void updatePaciente(Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getIdPaciente() == paciente.getIdPaciente()) {
                pacientes.set(i, paciente);
                return;
            }
        }
    }
    /**
     * Remove um paciente do repositório.
     *
     * @param paciente O paciente a ser removido.
     */

    public void deletePaciente(Paciente paciente) {
        pacientes.remove(paciente);
    }
}
