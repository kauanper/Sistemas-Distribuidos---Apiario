package POJO;

import java.time.LocalTime;
import java.util.UUID;

public abstract class Abelha {
    private String categoria;
    private int qtdMelPorSeg;
    private String id;

    public Abelha(String categoria, int qtdMelPorSeg) {
        this.categoria = categoria;
        this.qtdMelPorSeg = qtdMelPorSeg;
        this.id = UUID.randomUUID().toString();
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQtdMelPorSeg() {
        return qtdMelPorSeg;
    }

}
