package Services;

import POJO.Colmeia;
import POJO.Validacoes;

public class InspecaoService implements Validacoes {

    @Override
    public boolean verificarIdExistente(Colmeia colmeia) {
        return colmeia != null;
    }

    @Override
    public boolean verificarLimiteAbelhas(Colmeia colmeia) {
        return colmeia.qtdAtualAbelhas() < colmeia.getCapacidadeAbelhas();
    }

    @Override
    public boolean verificarRainha(Colmeia colmeia) {
        return !colmeia.verificarRainha();
    }
}
