package POJO;

public interface Validacoes {
    boolean verificarIdExistente(Colmeia colmeia);
    boolean verificarLimiteAbelhas(Colmeia colmeia);
    boolean verificarRainha(Colmeia colmeia);
}