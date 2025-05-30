package Services;

import Databases.ApicultorRepository;
import POJO.*;

public class AdicionarAbelhasService implements ValidaApicultor, Validacoes {

    public String execute(int operarias, int rainha, Apicultor apicultorRecebido, String idColmeia) {
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

        int espacoDisponivel = colmeia.getCapacidadeAbelhas() - colmeia.qtdAtualAbelhas();
        int operariasAdicionadas = 0;
        boolean rainhaAdicionada = false;

        if (rainha > 0 && verificarRainha(colmeia) && espacoDisponivel > 0) {
            colmeia.getAbelhas().add(new Rainha("Rainha", 3));
            colmeia.setRainhaExist(true);
            rainhaAdicionada = true;
            espacoDisponivel--;
        }

        for (int i = 0; i < operarias && espacoDisponivel > 0; i++) {
            colmeia.getAbelhas().add(new Operaria("Operária", 1));
            operariasAdicionadas++;
            espacoDisponivel--;
        }

        ApicultorRepository.salvarOuAtualizar(apicultor);

        StringBuilder resposta = new StringBuilder("Resultado da operação:\n");

        if (operariasAdicionadas > 0) {
            resposta.append("- ").append(operariasAdicionadas).append(" abelha(s) operária(s) adicionada(s).\n");
        }

        if (rainhaAdicionada) {
            resposta.append("- 1 abelha rainha adicionada.\n");
        }

        if (operariasAdicionadas < operarias || (!rainhaAdicionada && rainha > 0)) {
            resposta.append("Nem todas as abelhas foram adicionadas por falta de espaço na colmeia.\n");
        }

        resposta.append("Na colmeia ").append(idColmeia).append(".");

        return resposta.toString();
    }
}