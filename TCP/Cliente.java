package TCP;

import POJO.Colmeia;
import Streams.ColmeiaOutputStream;

import java.io.OutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 1234;

        try (Socket socket = new Socket(host, porta)) {

            System.out.println("Conectado com sucesso ao servidor em " + host + ":" + porta);

            Colmeia[] colmeias = {
                    new Colmeia(1, 100),
                    new Colmeia(2, 150),
            };

            OutputStream saida = socket.getOutputStream();

            ColmeiaOutputStream colmeiaOut = new ColmeiaOutputStream(
                    colmeias,
                    colmeias.length,
                    256,
                    saida
            );

            colmeiaOut.enviar();
            System.out.println("Dados enviados com sucesso.");
            colmeiaOut.close();

        } catch (Exception e) {
            System.err.println("Erro ao conectar ou enviar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}