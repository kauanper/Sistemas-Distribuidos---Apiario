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

        Apicultor apicultor = new Apicultor("João");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Opção 1");
            System.out.println("2 - Opção 2");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
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

                ApicultorOutputStream apicultorOut = new ApicultorOutputStream(
                        new Apicultor[]{apicultor},
                        1,
                        saida
                );
                apicultorOut.close();

                String resposta = br.readLine();
                System.out.println("Resposta do servidor: " + resposta);

            } catch (Exception e) {
                System.err.println("Erro ao conectar ou enviar dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
