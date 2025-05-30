package Multicast.TCP;

import Multicast.UDP.MulticastSender;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String host = "localhost";
        int porta = 1234;

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Você é administrador? (s/n): ");
        boolean isAdmin = scanner.nextLine().equalsIgnoreCase("s");

        try (Socket socket = new Socket(host, porta);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.writeUTF(nome);
            out.writeBoolean(isAdmin);
            out.flush();

            if (isAdmin) {
                System.out.println("1 - Adicionar candidato");
                System.out.println("2 - Remover candidato");
                System.out.println("3 - Enviar nota informativa (UDP)");
                int opcao = scanner.nextInt(); scanner.nextLine();

                out.writeInt(opcao);
                if (opcao == 1 || opcao == 2) {
                    System.out.print("Nome do candidato: ");
                    String candidato = scanner.nextLine();
                    out.writeUTF(candidato);
                } else if (opcao == 3) {
                    System.out.print("Mensagem: ");
                    String msg = scanner.nextLine();
                    MulticastSender.enviarNota(msg);
                }

            } else {
                // Cliente recebe lista de candidatos
                System.out.println("Lista de candidatos:");
                String candidato;
                while (!(candidato = in.readLine()).equals("FIM")) {
                    System.out.println("- " + candidato);
                }

                System.out.print("Digite o nome do candidato para votar: ");
                String voto = scanner.nextLine();
                out.writeUTF(voto);
            }

            System.out.println("Resposta do servidor: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
