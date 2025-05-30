package Services;

import Databases.ApicultorRepository;
import POJO.Apicultor;
import POJO.Colmeia;
import POJO.ValidaApicultor;

public class CriarColmeiaService implements ValidaApicultor {

    public String execute(int capacidadeAbelhas, int capacidadeMel, Apicultor apicultorRecebido) {
        Apicultor apicultor = validarApicultorOuCriar(apicultorRecebido);

        Colmeia novaColmeia = new Colmeia(capacidadeAbelhas, capacidadeMel);
        apicultor.addColmeias(novaColmeia);

        ApicultorRepository.salvarOuAtualizar(apicultor);

        return "Colmeia criada com sucesso!\n" + novaColmeia;
    }
}
