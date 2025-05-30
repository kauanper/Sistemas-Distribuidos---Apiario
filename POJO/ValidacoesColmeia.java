package POJO;

public interface ValidacoesColmeia {
    default boolean verificarIdExistente(Colmeia colmeia) {
        return colmeia != null;
    }

    default boolean verificarLimiteAbelhas(Colmeia colmeia) {
        return colmeia.qtdAtualAbelhas() < colmeia.getCapacidadeAbelhas();
    }

    default boolean verificarRainha(Colmeia colmeia) {
        return !colmeia.verificarRainha();
    }
}
