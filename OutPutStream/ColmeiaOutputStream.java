package OutPutStream;

import POJO.Colmeia;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ColmeiaOutputStream extends OutputStream {
    private Colmeia[] colmeias;
    private int quantidade;
    private int bytesPorObjeto;
    private OutputStream destino;

    public ColmeiaOutputStream(Colmeia[] colmeias, int quantidade, int bytesPorObjeto, OutputStream destino) {
        this.colmeias = colmeias;
        this.quantidade = quantidade;
        this.bytesPorObjeto = bytesPorObjeto;
        this.destino = destino;
    }

    @Override
    public void write(int b) throws IOException {
        // Escrita de 1 byte direto (usado apenas por compatibilidade)
        destino.write(b);
    }

    public void enviar() throws IOException {
        for (int i = 0; i < quantidade && i < colmeias.length; i++) {
            Colmeia c = colmeias[i];

            String dados = "ID: " + c.getId()
                    + " | Capacidade Abelhas: " + c.getCapacidadeAbelhas()
                    + " | Capacidade Mel: " + c.getCapacidadeMel();

            byte[] dadosBytes = dados.getBytes(StandardCharsets.UTF_8);

            if (dadosBytes.length > bytesPorObjeto) {
                // corta se passar do limite
                byte[] cortado = new byte[bytesPorObjeto];
                System.arraycopy(dadosBytes, 0, cortado, 0, bytesPorObjeto);
                destino.write(cortado);
            } else {
                destino.write(dadosBytes);
                // completa com espaço caso seja menor
                for (int j = dadosBytes.length; j < bytesPorObjeto; j++) {
                    destino.write(' ');
                }
            }
        }

        destino.flush();
    }

    @Override
    public void close() throws IOException {
        destino.close();
    }
}
