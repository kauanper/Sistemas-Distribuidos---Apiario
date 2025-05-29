package POJO;

import java.time.LocalTime;

public abstract class Abelha {
    private String categoria;
    private int qtdMelPorSeg;
    private LocalTime entrouNaComeia;

    public Abelha(String categoria, int qtdMelPorSeg) {
        this.categoria = categoria;
        this.qtdMelPorSeg = qtdMelPorSeg;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQtdMelPorSeg() {
        return qtdMelPorSeg;
    }

    public LocalTime getEntrouNaComeia() {
        return entrouNaComeia;
    }

    public void setEntrouNaComeia(LocalTime entrouNaComeia) {
        this.entrouNaComeia = entrouNaComeia;
    }
}
