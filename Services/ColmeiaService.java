package Services;

import POJO.Apicultor;
import POJO.Colmeia;

public class ColmeiaService {

    public String criarColmeia(int capacidadeAbelhas, int capacidadeMel, Apicultor apicultor) {
        Colmeia nova = new Colmeia(capacidadeAbelhas, capacidadeMel);
        apicultor.getColmeias().add(nova);
        String a = nova.toString();
        return "Colmeia criada com sucesso!\n" + a;
    }
}
