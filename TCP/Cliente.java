package TCP;

import POJO.Apicultor;
import Streams.ApicultorOutputStream;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 1234;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do apicultor: ");
        String nomeApicultor = scanner.nextLine();
        Apicultor apicultor = new Apicultor(nomeApicultor);

        while (true) {
            System.out.println("Menu:");
            System.out.println("digite 1 - Criar Colmeia");
            System.out.println("digite 2 - Ver Suas Colmeias");
            System.out.println("digite 3 - Adicionar Abelhas");
            System.out.println("digite 0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            }

            try (Socket socket = new Socket(host, porta);
                 OutputStream saida = socket.getOutputStream();
                 DataOutputStream dos = new DataOutputStream(saida);
                 InputStream entrada = socket.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(entrada))) {

                dos.writeInt(opcao);
                dos.flush();

                if (opcao == 1) {
                    System.out.print("Digite a capacidade de abelhas: ");
                    int capacidadeAbelhas = scanner.nextInt();
                    System.out.print("Digite a capacidade de mel: ");
                    int capacidadeMel = scanner.nextInt();
                    scanner.nextLine();

                    dos.writeInt(capacidadeAbelhas);
                    dos.writeInt(capacidadeMel);
                    dos.flush();

                    ApicultorOutputStream apicultorOut = new ApicultorOutputStream(
                            new Apicultor[]{apicultor},
                            1,
                            saida
                    );
                } else if (opcao == 2) {
                    ApicultorOutputStream apicultorOut = new ApicultorOutputStream(
                            new Apicultor[]{apicultor},
                            1,
                            saida
                    );
                } else if (opcao == 3) {
                    System.out.print("Digite o ID da colmeia: ");
                    String idColmeia = scanner.nextLine();

                    System.out.print("Digite a quantidade de abelhas operárias: ");
                    int qtdOperarias = scanner.nextInt();

                    System.out.print("Digite a quantidade de abelhas rainhas: ");
                    int qtdRainhas = scanner.nextInt();
                    scanner.nextLine();

                    dos.writeUTF(idColmeia);
                    dos.writeInt(qtdOperarias);
                    dos.writeInt(qtdRainhas);
                    dos.flush();

                    ApicultorOutputStream apicultorOut = new ApicultorOutputStream(
                            new Apicultor[]{apicultor},
                            1,
                            saida
                    );
                }


                StringBuilder respostaCompleta = new StringBuilder();
                String linha;

                while ((linha = br.readLine()) != null) {
                    respostaCompleta.append(linha).append("\n");
                }

                System.out.println("\nResposta do servidor:\n" + respostaCompleta.toString());

            } catch (Exception e) {
                System.err.println("Erro ao conectar ou enviar dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
