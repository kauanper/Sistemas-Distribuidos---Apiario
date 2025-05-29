package POJO;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class Colmeia implements Serializable {
    private String id;
    private int capacidadeAbelhas;
    private int capacidadeMel;
    private List<Abelha> abelhas;
    private boolean rainhaExist;

    public Colmeia(int capacidadeAbelhas, int capacidadeMel) {
        this.capacidadeAbelhas = capacidadeAbelhas;
        this.capacidadeMel = capacidadeMel;
        this.rainhaExist = false;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public int getCapacidadeAbelhas() {
        return capacidadeAbelhas;
    }

    public int getCapacidadeMel() {
        return capacidadeMel;
    }

    public int qtdAtualAbelhas() {
        return abelhas.size();
    }

    public void setRainhaExist(boolean rainhaExist) {
        this.rainhaExist = rainhaExist;
    }

    public int verificarQtdMelAtual(){
        return 1;
    }

    public boolean vericarLimiteAbelhas(){
        return false;
    }

    public boolean verificarRainha(){
        return false;
    }
}
