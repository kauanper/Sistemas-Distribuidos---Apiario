package TCP;

import POJO.Apicultor;
import Services.ColmeiaService;
import Streams.ApicultorInputStream;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int porta = 1234;
        ColmeiaService colmeiaService = new ColmeiaService(); // Instância do service

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

                    String resposta;

                    switch (opcao) {
                        case 1:
                            int capacidadeAbelhas = dis.readInt();
                            int capacidadeMel = dis.readInt();

                            ApicultorInputStream entradaApicultor = new ApicultorInputStream(dis);
                            Apicultor[] apicultoresRecebidos = entradaApicultor.lerApicultores();

                            System.out.println("Apicultor: " + apicultoresRecebidos[0].getNome());

                            resposta = colmeiaService.criarColmeia(capacidadeAbelhas, capacidadeMel, apicultoresRecebidos[0]);
                            break;

                        case 2:
                            ApicultorInputStream entradaApicultor2 = new ApicultorInputStream(dis);
                            Apicultor[] apicultoresRecebidos2 = entradaApicultor2.lerApicultores();
                            Apicultor apicultor = apicultoresRecebidos2[0];

                            System.out.println("Apicultor recebido para listar colmeias: " + apicultor.getNome());

                            resposta = colmeiaService.listarColmeiasPorApicultor(apicultor);
                            break;


                        default:
                            resposta = "Opção inválida.";
                    }

                    OutputStream saida = clienteSocket.getOutputStream();
                    PrintWriter pw = new PrintWriter(saida, true);
                    pw.println(resposta);

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