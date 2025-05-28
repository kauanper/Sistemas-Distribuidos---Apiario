import POJO.Colmeia;
import OutPutStream.ColmeiaOutputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            Colmeia[] colmeias = {
                    new Colmeia(100, 50),
                    new Colmeia(120, 60)
            };

            int qtdObjetos      = 2;
            int bytesPorObjeto  = 150;   // agora serve só como “limite recomendado”

            // Console
            ColmeiaOutputStream cosConsole =
                    new ColmeiaOutputStream(colmeias, qtdObjetos, bytesPorObjeto, System.out);
            cosConsole.enviar();

            // Arquivo
            System.out.println("\n--- gravando em arquivo ---");
            try (FileOutputStream fos = new FileOutputStream("colmeias_output.txt")) {
                ColmeiaOutputStream cosFile =
                        new ColmeiaOutputStream(colmeias, qtdObjetos, bytesPorObjeto, fos);
                cosFile.enviar(); // o close() do try-with-resources fecha tudo
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
