package TCP;

import POJO.Apicultor;
import Streams.ApicultorInputStream;
import java.io.*;
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

                    InputStream entradaBruta = clienteSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(entradaBruta);

                    int opcao = dis.readInt();
                    System.out.println("Opção recebida: " + opcao);

                    ApicultorInputStream entradaApicultor = new ApicultorInputStream(dis);
                    Apicultor[] apicultoresRecebidos = entradaApicultor.lerApicultores();

                    for (Apicultor a : apicultoresRecebidos) {
                        System.out.println("Apicultor recebido: " + a.getNome() + " - ID: " + a.getId());
                    }

                    String resposta;
                    switch (opcao) {
                        case 1:
                            resposta = "Você escolheu a opção 1!";
                            break;
                        case 2:
                            resposta = "Opção 2 selecionada!";
                            break;
                        default:
                            resposta = "Opção inválida.";
                    }

                    OutputStream saida = clienteSocket.getOutputStream();
                    PrintWriter pw = new PrintWriter(saida, true);
                    pw.println(resposta);

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