package POJO;

import java.util.UUID;

public class Operaria extends Abelha{
    private String id;

    public Operaria(String categoria, int qtdMelPorSeg) {
        super(categoria, qtdMelPorSeg);
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void produzirMel(){
        //por enquanto nada
    }
}
