package POJO;

import java.io.Serializable;
import java.util.ArrayList;
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
        this.abelhas = new ArrayList<>(); // ← Lista inicializada corretamente
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

    public List<Abelha> getAbelhas() {
        return abelhas; // ← Getter adicionado
    }

    public void setRainhaExist(boolean rainhaExist) {
        this.rainhaExist = rainhaExist;
    }

    public int verificarQtdMelAtual() {
        return 1;
    }

    public boolean vericarLimiteAbelhas() {
        return false;
    }

    public boolean verificarRainha() {
        return false;
    }

    @Override
    public String toString() {
        return "Colmeia {" +
                "\n  ID: " + id +
                "\n  Capacidade de Abelhas: " + capacidadeAbelhas +
                "\n  Capacidade de Mel: " + capacidadeMel +
                "\n  Quantidade Atual de Abelhas: " + (abelhas != null ? abelhas.size() : 0) +
                "\n  Possui Rainha: " + rainhaExist +
                "\n}";
    }
}
