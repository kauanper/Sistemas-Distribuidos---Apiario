package Streams;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InputStream extends java.io.InputStream {

    private final DataInputStream origem;

    public InputStream(java.io.InputStream origem) {
        this.origem = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return origem.read(); // leitura byte a byte, normalmente não usada aqui
    }

    public String lerObjetoComoTexto() throws IOException {
        try {
            int tamanho = origem.readInt(); // lê o tamanho do próximo bloco
            byte[] buffer = new byte[tamanho];
            origem.readFully(buffer);       // lê exatamente 'tamanho' bytes
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null; // fim de stream ou erro
        }
    }

    @Override
    public void close() throws IOException {
        origem.close();
    }
}