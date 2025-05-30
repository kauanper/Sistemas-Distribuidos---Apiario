package Services;

import Databases.ApicultorRepository;
import POJO.Apicultor;
import POJO.Colmeia;

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

        return "Colmeia criada com sucesso!\n" + novaColmeia.toString();
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
}
