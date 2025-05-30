package Multicast.TCP;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final Set<String> candidatos = ConcurrentHashMap.newKeySet();
    private static final Map<String, Integer> votos = new ConcurrentHashMap<>();
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(1234);
        System.out.println("Servidor de votação iniciado...");

        candidatos.add("Alice");
        candidatos.add("Bob");

        while (true) {
            Socket cliente = servidor.accept();
            pool.execute(() -> tratarCliente(cliente));
        }
    }

    private static void tratarCliente(Socket socket) {
        try (
                DataInputStream in = new DataInputStream(socket.getInputStream());
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String nome = in.readUTF();
            boolean isAdmin = in.readBoolean();

            if (isAdmin) {
                int opcao = in.readInt();
                if (opcao == 1) {
                    String novo = in.readUTF();
                    candidatos.add(novo);
                    out.write("Candidato adicionado\n");
                } else if (opcao == 2) {
                    String rmv = in.readUTF();
                    candidatos.remove(rmv);
                    out.write("Candidato removido\n");
                } else {
                    out.write("Nota informativa enviada via UDP\n");
                }
            } else {
                for (String c : candidatos) {
                    out.write(c + "\n");
                }
                out.write("FIM\n");
                out.flush();

                String voto = in.readUTF();
                votos.merge(voto, 1, Integer::sum);
                out.write("Voto recebido em " + voto + "\n");
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
