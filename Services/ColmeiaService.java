package Services;

import Databases.ApicultorRepository;
import POJO.Apicultor;
import POJO.Colmeia;
import POJO.Operaria;
import POJO.Rainha;

public class ColmeiaService {

    public String criarColmeia(int capacidadeAbelhas, int capacidadeMel, Apicultor apicultorRecebido) {
        Apicultor apicultor = ApicultorRepository.buscarPorId(apicultorRecebido.getId());

        if (apicultor == null) {
            apicultor = apicultorRecebido;
        }

        Colmeia novaColmeia = new Colmeia(capacidadeAbelhas, capacidadeMel);
        apicultor.addColmeias(novaColmeia);

        ApicultorRepository.salvarOuAtualizar(apicultor);

        return "Colmeia criada com sucesso!\n" + novaColmeia;
    }

    public String listarColmeiasPorApicultor(Apicultor apicultorRecebido) {
        Apicultor apicultor = ApicultorRepository.buscarPorId(apicultorRecebido.getId());

        if (apicultor == null || apicultor.getColmeias().isEmpty()) {
            return "Nenhuma colmeia encontrada para o apicultor " + apicultorRecebido.getNome();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Colmeias do apicultor ").append(apicultor.getNome()).append(":\n");
        for (Colmeia c : apicultor.getColmeias()) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    public String adicionarAbelhas(int operarias, int rainha, Apicultor apicultorRecebido, String idColmeia) {
        Apicultor apicultor = ApicultorRepository.buscarPorId(apicultorRecebido.getId());

        if (apicultor == null) {
            return "Apicultor não encontrado.";
        }

        Colmeia colmeia = null;
        for (Colmeia c : apicultor.getColmeias()) {
            if (c.getId().equals(idColmeia)) {
                colmeia = c;
                break;
            }
        }

        InspecaoService inspecao = new InspecaoService();

        if (!inspecao.verificarIdExistente(colmeia)) {
            return "Colmeia não encontrada.";
        }

        int capacidadeTotal = colmeia.getCapacidadeAbelhas();
        int abelhasAtuais = colmeia.qtdAtualAbelhas();
        int espacoDisponivel = capacidadeTotal - abelhasAtuais;

        int operariasAdicionadas = 0;
        boolean rainhaAdicionada = false;

        // Tenta adicionar rainha primeiro
        if (rainha > 0 && inspecao.verificarRainha(colmeia) && espacoDisponivel > 0) {
            colmeia.getAbelhas().add(new Rainha("Rainha", 3));
            colmeia.setRainhaExist(true);
            rainhaAdicionada = true;
            espacoDisponivel--;
        }

        // Agora adiciona as operárias, respeitando o espaço restante
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
            resposta.append("⚠️ Nem todas as abelhas foram adicionadas por falta de espaço na colmeia.\n");
        }

        resposta.append("Na colmeia ").append(idColmeia).append(".");

        return resposta.toString();
    }
}
