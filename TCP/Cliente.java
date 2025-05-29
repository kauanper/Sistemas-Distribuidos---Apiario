package TCP;

import POJO.Apicultor;
import Streams.ApicultorOutputStream;

import java.io.OutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 1234;

        try (Socket socket = new Socket(host, porta)) {

            System.out.println("Conectado com sucesso ao servidor em " + host + ":" + porta);

            Apicultor[] apicultores = {
                    new Apicultor("João"),
                    new Apicultor("Maria"),
            };

            OutputStream saida = socket.getOutputStream();

            ApicultorOutputStream apicultorOut = new ApicultorOutputStream(
                    apicultores,
                    apicultores.length,
                    saida
            );

            apicultorOut.close();

            System.out.println("Dados enviados com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao conectar ou enviar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}