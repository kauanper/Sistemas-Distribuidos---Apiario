package Services;

import POJO.Apicultor;
import POJO.Colmeia;

import java.util.List;

public class ColmeiaService {

    public String criarColmeia(int capacidadeAbelhas, int capacidadeMel, Apicultor apicultor) {
        Colmeia nova = new Colmeia(capacidadeAbelhas, capacidadeMel);
        apicultor.getColmeias().add(nova);
        String a = nova.toString();
        return "Colmeia criada com sucesso!\n" + a;
    }

    public String listarColmeiasPorApicultor(Apicultor apicultor) {
        List<Colmeia> colmeias = apicultor.getColmeias();

        if (colmeias == null || colmeias.isEmpty()) {
            return "Nenhuma colmeia encontrada para o apicultor " + apicultor.getNome();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Colmeias do apicultor ").append(apicultor.getNome()).append(":\n");
        for (Colmeia c : colmeias) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

}
