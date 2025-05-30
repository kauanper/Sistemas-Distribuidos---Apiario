package Databases;

import POJO.Apicultor;

import java.util.*;

//banco de dados em memoria
public class ApicultorRepository {
    private static final Map<String, Apicultor> apicultores = new HashMap<>();

    public static void salvarOuAtualizar(Apicultor apicultor) {
        apicultores.put(apicultor.getId(), apicultor);
    }

    public static Apicultor buscarPorId(String id) {
        return apicultores.get(id);
    }

}

