package POJO;

import java.util.UUID;

public class Rainha extends Abelha{
    private String id;

    public Rainha(String categoria, int qtdMelPorSeg) {
        super(categoria, qtdMelPorSeg);
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void botarOvos(){
        //por enquanto nada
    }
}
