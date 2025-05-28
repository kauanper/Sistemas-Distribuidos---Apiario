package Streams;

import java.io.FileInputStream;
import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\kauan\\OneDrive\\Área de Trabalho\\Pasta das Pastas\\Distribuidos\\GestãoApiario\\colmeias_output.txt");

        InputStream stream = new InputStream(fis); // sua classe customizada

        String resultado = stream.lerObjetoComoTexto();
        while (resultado != null) {
            System.out.println("Lido do arquivo: " + resultado);
            resultado = stream.lerObjetoComoTexto();
        }

        stream.close();
    }
}
