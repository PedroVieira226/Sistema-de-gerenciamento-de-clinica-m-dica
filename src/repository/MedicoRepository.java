package repository;

import entities.Medico;
import excptions.CpfJaCadastradoException;
import excptions.EspecialidadeInvalidaException;

import java.util.ArrayList;
import java.util.List;
/**
 * Repositório responsável por gerenciar a persistência de objetos do tipo Medico.
 */
public class MedicoRepository {
    private List<Medico> medicos = new ArrayList<>();

    /**
     * Adiciona um novo médico ao repositório.
     *
     * @param medico O médico a ser adicionado.
     * @throws CpfJaCadastradoException Se já existir um médico com o mesmo CPF.
     * @throws EspecialidadeInvalidaException Se a especialidade do médico for inválida.
     */

    public void createMedico(Medico medico) throws CpfJaCadastradoException, EspecialidadeInvalidaException {
        // Verifica se já existe um médico com o mesmo CPF
        for (Medico m : medicos) {
            if (m.getCpf().equals(medico.getCpf())) {
                throw new CpfJaCadastradoException("Já existe um médico com este CPF.");
            }
        }

        medicos.add(medico);
    }

    /**
     * Retorna uma lista de todos os médicos cadastrados.
     *
     * @return Uma lista de médicos.
     */

    public List<Medico> listAllMedicos() {
        return medicos;
    }

    /**
     * Busca um médico pelo ID.
     *
     * @param id O ID do médico a ser buscado.
     * @return O médico encontrado, ou null se não existir.
     */

    public Medico readMedico(int id) {
        for (Medico m : medicos) {
            if (m.getIdMedico() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um médico existente.
     *
     * @param medico O médico com os dados atualizados.
     */

    public void updateMedico(Medico medico) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getIdMedico() == medico.getIdMedico()) {
                medicos.set(i, medico);
                return;
            }
        }
    }

    /**
     * Remove um médico do repositório.
     *
     * @param medico O médico a ser removido.
     */

    public void deleteMedico(Medico medico) {
        medicos.remove(medico);
    }
}
