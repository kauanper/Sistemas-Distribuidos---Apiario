package Streams;

import POJO.Apicultor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ApicultorInputStream extends InputStream {
    private final ObjectInputStream objectInputStream;

    public ApicultorInputStream(InputStream origem) throws IOException {
        this.objectInputStream = new ObjectInputStream(origem);
    }

    public Apicultor[] lerApicultores() throws IOException, ClassNotFoundException {
        int quantidade = objectInputStream.readInt();
        Apicultor[] apicultores = new Apicultor[quantidade];

        for (int i = 0; i < quantidade; i++) {
            apicultores[i] = (Apicultor) objectInputStream.readObject();
        }

        return apicultores;
    }

    @Override
    public int read() throws IOException {
        return objectInputStream.read();
    }
}
