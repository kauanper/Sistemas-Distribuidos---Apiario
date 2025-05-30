package Streams;

import POJO.Apicultor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TesteApicultor {

    public static void main(String[] args) throws Exception {
        Apicultor[] apicultores = new Apicultor[] {
                new Apicultor("João"),
                new Apicultor("Maria")
        };

        System.out.println("=== Teste com Arquivo ===");
        testeArquivo(apicultores);

        System.out.println("\n=== Teste com ByteArrayStream (Memória) ===");
        testeByteArray(apicultores);

        System.out.println("\n=== Teste com Socket ===");
        testeSocket(apicultores);
    }

    private static void testeArquivo(Apicultor[] apicultores) throws Exception {
        File file = new File("Streams/apicultores.bin");

        try (OutputStream fos = new FileOutputStream(file)) {
            new ApicultorOutputStream(apicultores, apicultores.length, fos);
        }

        try (InputStream fis = new FileInputStream(file)) {
            ApicultorInputStream ais = new ApicultorInputStream(fis);
            Apicultor[] lidos = ais.lerApicultores();

            for (Apicultor a : lidos) {
                System.out.println("Lido: " + a.getNome() + " - ID: " + a.getId());
            }
        }
    }

    private static void testeByteArray(Apicultor[] apicultores) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        new ApicultorOutputStream(apicultores, apicultores.length, baos);

        byte[] dados = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(dados);
        ApicultorInputStream ais = new ApicultorInputStream(bais);

        Apicultor[] lidos = ais.lerApicultores();

        for (Apicultor a : lidos) {
            System.out.println("Lido: " + a.getNome() + " - ID: " + a.getId());
        }
    }

    private static void testeSocket(Apicultor[] apicultores) throws Exception {
        final int porta = 5555;

        Thread servidor = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(porta)) {
                System.out.println("Servidor esperando conexão...");
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Servidor aceitou conexão");
                    ApicultorInputStream ais = new ApicultorInputStream(socket.getInputStream());
                    Apicultor[] lidos = ais.lerApicultores();

                    for (Apicultor a : lidos) {
                        System.out.println("Servidor recebeu: " + a.getNome() + " - ID: " + a.getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        servidor.start();

        Thread.sleep(500);

        Thread cliente = new Thread(() -> {
            try (Socket socket = new Socket("localhost", porta)) {
                System.out.println("Cliente conectado ao servidor");
                new ApicultorOutputStream(apicultores, apicultores.length, socket.getOutputStream());
                System.out.println("Cliente enviou apicultores");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cliente.start();

        cliente.join();
        servidor.join();
    }
}
