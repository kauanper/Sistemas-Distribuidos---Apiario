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

                            ApicultorInputStream entradaApicultor1 = new ApicultorInputStream(dis);
                            Apicultor[] apicultoresRecebidos1 = entradaApicultor1.lerApicultores();
                            Apicultor apicultorCriar = apicultoresRecebidos1[0];

                            resposta = colmeiaService.criarColmeia(capacidadeAbelhas, capacidadeMel, apicultorCriar);
                            break;

                        case 2:
                            ApicultorInputStream entradaApicultor2 = new ApicultorInputStream(dis);
                            Apicultor[] apicultoresRecebidos2 = entradaApicultor2.lerApicultores();
                            Apicultor apicultorListar = apicultoresRecebidos2[0];

                            resposta = colmeiaService.listarColmeiasPorApicultor(apicultorListar);
                            break;
                        case 3:
                            String idColmeia = dis.readUTF();
                            int qtdOperarias = dis.readInt();
                            int qtdRainhas = dis.readInt();

                            ApicultorInputStream entradaApicultor3 = new ApicultorInputStream(dis);
                            Apicultor[] apicultoresRecebidos3 = entradaApicultor3.lerApicultores();
                            Apicultor apicultorAdicionar = apicultoresRecebidos3[0];

                            resposta = "colmeiaService.adicionarAbelhas(idColmeia, qtdOperarias, qtdRainhas, apicultorAdicionar);";
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