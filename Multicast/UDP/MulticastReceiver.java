package Multicast.UDP;

import java.net.*;

public class MulticastReceiver {
    public static void main(String[] args) throws Exception {
        String grupo = "230.0.0.0";
        int porta = 4321;

        MulticastSocket socket = new MulticastSocket(porta);
        socket.joinGroup(InetAddress.getByName(grupo));

        System.out.println("Aguardando notas informativas...");

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
            socket.receive(pacote);

            String msg = new String(pacote.getData(), 0, pacote.getLength());
            System.out.println("NOTA: " + msg);
        }
    }
}
