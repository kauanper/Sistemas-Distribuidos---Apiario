package Multicast.TCP;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final Set<String> candidatos = ConcurrentHashMap.newKeySet();
    private static final Map<String, Integer> votos = new ConcurrentHashMap<>();
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final int DURACAO_VOTACAO_SEGUNDOS = 60;
    private static volatile boolean votacaoAberta = true;

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(1234);
        System.out.println("Servidor de votação iniciado...");

        candidatos.add("Alice");
        candidatos.add("Bob");

        agendarFimDaVotacao();

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

            if (!votacaoAberta && !isAdmin) {
                out.write("A votação foi encerrada. Você não pode mais votar.\n");
                out.flush();
                return;
            }

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

                if (!votacaoAberta) {
                    out.write("A votação foi encerrada. Voto não será contabilizado.\n");
                } else {
                    String voto = in.readUTF();
                    if (candidatos.contains(voto)) {
                        votos.merge(voto, 1, Integer::sum);
                        out.write("Voto recebido em " + voto + "\n");
                        System.out.println("Voto recebido: " + voto);
                    } else {
                        out.write("Candidato inválido. Voto não registrado.\n");
                    }
                }
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void agendarFimDaVotacao() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            votacaoAberta = false;
            System.out.println("\nTempo de votação encerrado!");
            exibirResultados();
        }, DURACAO_VOTACAO_SEGUNDOS, TimeUnit.SECONDS);
    }

    private static void exibirResultados() {
        int totalVotos = votos.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("\nRESULTADOS FINAIS:");
        if (totalVotos == 0) {
            System.out.println("Nenhum voto foi registrado.");
            return;
        }

        String vencedor = null;
        int maxVotos = 0;

        for (String candidato : candidatos) {
            int v = votos.getOrDefault(candidato, 0);
            double percentual = (v * 100.0) / totalVotos;
            System.out.printf("%s: %d votos (%.2f%%)%n", candidato, v, percentual);

            if (v > maxVotos) {
                maxVotos = v;
                vencedor = candidato;
            }
        }

        System.out.println("VENCEDOR: " + vencedor);
    }
}