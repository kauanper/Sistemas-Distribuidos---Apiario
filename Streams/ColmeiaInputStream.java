package Streams;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ColmeiaInputStream extends java.io.InputStream {

    private final DataInputStream origem;

    public ColmeiaInputStream(java.io.InputStream origem) {
        this.origem = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return origem.read();
    }

    // Lê e desempacota um "objeto" serializado como texto
    public String lerObjetoComoTexto() throws IOException {
        try {
            int tamanho = origem.readInt(); // lê o tamanho do payload
            byte[] buffer = new byte[tamanho];
            origem.readFully(buffer); // lê o conteúdo
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        origem.close();
    }
}