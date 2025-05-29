package Streams;

import POJO.Colmeia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TesteEscritaLeituraArquivo {
    public static void main(String[] args) throws IOException {

        try {
            Colmeia[] colmeias = {
                    new Colmeia(999, 40),
                    new Colmeia(333, 40)
            };

            int qtdObjetos      = 2;
            int bytesPorObjeto  = 150;

            // Console
            ColmeiaOutputStream cosConsole =
                    new ColmeiaOutputStream(colmeias, qtdObjetos, bytesPorObjeto, System.out);
            cosConsole.enviar();

            // Arquivo
            System.out.println("--- gravando em arquivo ---");
            try (FileOutputStream fos = new FileOutputStream("colmeias_output.txt")) {
                ColmeiaOutputStream cosFile =
                        new ColmeiaOutputStream(colmeias, qtdObjetos, bytesPorObjeto, fos);
                cosFile.enviar();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream fis = new FileInputStream("C:\\Users\\kauan\\OneDrive\\Área de Trabalho\\Pasta das Pastas\\Distribuidos\\GestãoApiario\\colmeias_output.txt");

        ColmeiaInputStream stream = new ColmeiaInputStream(fis);

        String resultado = stream.lerObjetoComoTexto();
        while (resultado != null) {
            System.out.println("Lido do arquivo: " + resultado);
            resultado = stream.lerObjetoComoTexto();
        }

        stream.close();
    }
}
