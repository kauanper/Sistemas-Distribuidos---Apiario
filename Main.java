import POJO.Colmeia;
import OutPutStream.ColmeiaOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // Criar array de colmeias
            Colmeia[] colmeias = {
                    new Colmeia(100, 50),
                    new Colmeia(120, 60)
            };

            int quantidade = 2;
            int bytesPorObjeto = 80;

            // Teste 1: Saída padrão
            OutputStream destino1 = System.out;
            ColmeiaOutputStream cos1 = new ColmeiaOutputStream(colmeias, quantidade, bytesPorObjeto, destino1);
            cos1.enviar();

            System.out.println("\n\n--- Escrita em arquivo ---");

            // Teste 2: Arquivo
            OutputStream destino2 = new FileOutputStream("colmeias_output.txt");
            ColmeiaOutputStream cos2 = new ColmeiaOutputStream(colmeias, quantidade, bytesPorObjeto, destino2);
            cos2.enviar();
            cos2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
