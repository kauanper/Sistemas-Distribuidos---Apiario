package Databases;

import POJO.Apicultor;

import java.util.*;

public class ApicultorRepository {
    private static final Map<String, Apicultor> apicultores = new HashMap<>();

    public static void salvarOuAtualizar(Apicultor apicultor) {
        apicultores.put(apicultor.getId(), apicultor);
    }

    public static Apicultor buscarPorId(String id) {
        return apicultores.get(id);
    }

    public static Apicultor buscarPorNome(String nome) {
        for (Apicultor a : apicultores.values()) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }
}

