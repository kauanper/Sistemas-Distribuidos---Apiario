package POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Apicultor implements Serializable {
    private String nome;
    private String id;
    private List<Colmeia> colmeias;

    public Apicultor(String nome) {
        this.nome = nome;
        this.id = UUID.randomUUID().toString();
        this.colmeias = new ArrayList<>();
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