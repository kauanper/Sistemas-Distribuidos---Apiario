package Multicast.UDP;

import java.net.*;

public class MulticastSender {
    public static void enviarNota(String mensagem) {
        String grupo = "230.0.0.0";
        int porta = 4321;

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress grupoInet = InetAddress.getByName(grupo);
            byte[] buffer = mensagem.getBytes();

            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, grupoInet, porta);
            socket.send(pacote);
            socket.close();
            System.out.println("Nota enviada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
