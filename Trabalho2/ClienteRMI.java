package Trabalho2;

import POJO.Apicultor;
import Trabalho2.remote.ServicoRMI;

import java.io.*;
import java.rmi.Naming;
import java.util.Scanner;


public class ClienteRMI {
    public static void main(String[] args) {
        try {
            ServicoRMI servico = (ServicoRMI) Naming.lookup("rmi://localhost/ServicoRMI");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o nome do apicultor: ");
            Apicultor apicultor = new Apicultor(scanner.nextLine());

            while (true) {
                System.out.println("1. Criar colmeia\n2. Listar colmeias\n3. Adicionar abelhas\n0. Sair");
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao == 0) break;

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(apicultor);

                switch (opcao) {
                    case 1:
                        System.out.print("Capacidade de abelhas: ");
                        oos.writeInt(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Capacidade de mel: ");
                        oos.writeInt(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 2:
                        // apenas o apicultor
                        break;
                    case 3:
                        System.out.print("ID da colmeia: ");
                        oos.writeUTF(scanner.nextLine());
                        System.out.print("Qtd operárias: ");
                        oos.writeInt(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Qtd rainhas: ");
                        oos.writeInt(Integer.parseInt(scanner.nextLine()));
                        break;
                }
                oos.flush();
                byte[] argumentos = bos.toByteArray();
                byte[] resposta = servico.doOperation("ApicultorService", String.valueOf(opcao), argumentos);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(resposta));
                String respostaFinal = (String) ois.readObject();
                System.out.println("\nResposta: " + respostaFinal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}