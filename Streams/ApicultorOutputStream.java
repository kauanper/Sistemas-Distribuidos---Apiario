package Streams;

import POJO.Apicultor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ApicultorOutputStream extends OutputStream {
    private final OutputStream destino;
    private final ObjectOutputStream objectOutputStream;

    public ApicultorOutputStream(Apicultor[] apicultores, int quantidade, OutputStream destino) throws IOException {
        this.destino = destino;
        this.objectOutputStream = new ObjectOutputStream(destino);

        objectOutputStream.writeInt(quantidade);

        for (int i = 0; i < quantidade; i++) {
            objectOutputStream.writeObject(apicultores[i]);
        }

        objectOutputStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }
}
