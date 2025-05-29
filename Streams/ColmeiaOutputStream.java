package Streams;

import POJO.Colmeia;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ColmeiaOutputStream extends OutputStream {

    private final Colmeia[] colmeias;
    private final int quantidade;
    private final int bytesPorObjeto;
    private final OutputStream destinoOriginal;
    private final DataOutputStream destino;

    public ColmeiaOutputStream(Colmeia[] colmeias,
                               int quantidade,
                               int bytesPorObjeto,
                               OutputStream destino) {
        this.colmeias        = colmeias;
        this.quantidade      = quantidade;
        this.bytesPorObjeto  = bytesPorObjeto;
        this.destinoOriginal = destino;
        this.destino         = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    public void enviar() throws IOException {
        for (int i = 0; i < quantidade && i < colmeias.length; i++) {
            Colmeia c = colmeias[i];

            String payload = "ID=" + c.getId()
                    + ";ABELHAS=" + c.getCapacidadeAbelhas()
                    + ";MEL=" + c.getCapacidadeMel();
            byte[] dados = payload.getBytes(StandardCharsets.UTF_8);

            if (dados.length > bytesPorObjeto) {
                System.err.printf(
                        "Aviso: objeto %d excedeu o limite (%d > %d bytes).%n",
                        i, dados.length, bytesPorObjeto);
            }

            destino.writeInt(dados.length);
            destino.write(dados);

            if (destinoOriginal == System.out) {
                destino.write('\n');
            }
        }
        destino.flush();
    }

    @Override
    public void close() throws IOException {
        destino.close();
    }
}
