package TCP;

import Streams.ColmeiaInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

                    java.io.InputStream fluxoEntrada = clienteSocket.getInputStream();
                    ColmeiaInputStream entradaColmeia = new ColmeiaInputStream(fluxoEntrada);

                    String dados;
                    while ((dados = entradaColmeia.lerObjetoComoTexto()) != null) {
                        System.out.println("Colmeia recebida: " + dados);
                    }

                    entradaColmeia.close();
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