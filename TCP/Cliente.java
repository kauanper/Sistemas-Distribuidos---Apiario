package TCP;

import POJO.Colmeia;
import Streams.ColmeiaOutputStream;

import java.io.OutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // ou IP do servidor
        int porta = 1234;

        try (Socket socket = new Socket(host, porta)) {

            // Array de colmeias para enviar
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
            colmeiaOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
