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
                try (Socket clienteSocket = serverSocket.accept()) {
                    System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                    // Usa o InputStream original do Java com nome completo
                    java.io.InputStream fluxoEntrada = clienteSocket.getInputStream();

                    // Usa seu InputStream customizado
                    ColmeiaInputStream entradaColmeia = new ColmeiaInputStream(fluxoEntrada);

                    String dados;
                    while ((dados = entradaColmeia.lerObjetoComoTexto()) != null) {
                        System.out.println("Colmeia recebida: " + dados);
                    }

                    entradaColmeia.close();
                    System.out.println("Cliente desconectado.");
                } catch (Exception e) {
                    System.err.println("Erro na conexão com cliente: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }
}
