package Services;

import Databases.ApicultorRepository;
import POJO.*;

public class RemoverColmeiasService implements ValidaApicultor, ValidacoesColmeia {

    public String execute(Apicultor apicultorRecebido, String idColmeia) {
        Apicultor apicultor = validarApicultorExistente(apicultorRecebido);

        if (apicultor == null) {
            return "Apicultor não encontrado.";
        }

        Colmeia colmeia = apicultor.getColmeias().stream()
                .filter(c -> c.getId().equals(idColmeia))
                .findFirst()
                .orElse(null);

        if (!verificarIdExistente(colmeia)) {
            return "Colmeia não encontrada.";
        }

        apicultor.getColmeias().remove(colmeia);
        ApicultorRepository.salvarOuAtualizar(apicultor);
        return "Colmeia removida com sucesso!";
    }
}