package POJO;

import java.util.List;
import java.util.UUID;

public class Apicultor {
    private String nome;
    private String id;
    private List<Colmeia> colmeias;

    public Apicultor(String nome, String id) {
        this.nome = nome;
        this.id = this.id = UUID.randomUUID().toString();
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public List<Colmeia> getColmeias() {
        return colmeias;
    }

    public void addColmeias(Colmeia colmeia) {
        this.colmeias.add(colmeia);
    }
}
