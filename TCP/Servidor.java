package TCP;

import POJO.Apicultor;
import Streams.ApicultorInputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

public class Servidor {
    public static void main(String[] args) {
        int porta = 1234;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor rodando na porta " + porta);

            while (true) {
                Socket clienteSocket = null;
                try {
                    clienteSocket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                    InputStream fluxoEntrada = clienteSocket.getInputStream();
                    ApicultorInputStream entradaApicultor = new ApicultorInputStream(fluxoEntrada);

                    Apicultor[] apicultoresRecebidos = entradaApicultor.lerApicultores();

                    for (Apicultor a : apicultoresRecebidos) {
                        System.out.println("Apicultor recebido: " + a.getNome() + " - ID: " + a.getId());
                    }

                    entradaApicultor.close();
                    clienteSocket.close();
                    System.out.println("Cliente desconectado.");

                } catch (Exception e) {
                    System.err.println("Erro na conexão com cliente: " + e.getMessage());
                    if (clienteSocket != null && !clienteSocket.isClosed()) {
                        try { clienteSocket.close(); } catch (Exception ex) {}
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }
}