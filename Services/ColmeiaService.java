package Services;

import Databases.ApicultorRepository;
import POJO.Apicultor;
import POJO.Colmeia;
import POJO.Operaria;
import POJO.Rainha;

import java.util.List;

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

        if (colmeia == null) {
            return "Colmeia não encontrada.";
        }

        for (int i = 0; i < operarias; i++) {
            Operaria operaria = new Operaria("Operária", 1);
            colmeia.getAbelhas().add(operaria);
        }

        for (int i = 0; i < rainha; i++) {
            Rainha novaRainha = new Rainha("Rainha", 3);
            colmeia.getAbelhas().add(novaRainha);
            colmeia.setRainhaExist(true);
        }

        ApicultorRepository.salvarOuAtualizar(apicultor);

        return "Abelhas adicionadas com sucesso na colmeia " + idColmeia + ".";
    }

}
