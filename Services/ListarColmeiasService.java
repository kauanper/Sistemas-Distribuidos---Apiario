package Services;

import POJO.Apicultor;
import POJO.Colmeia;
import POJO.ValidaApicultor;

public class ListarColmeiasService implements ValidaApicultor {
    public String execute(Apicultor apicultorRecebido) {
        Apicultor apicultor = validarApicultorExistente(apicultorRecebido);

        if (apicultor == null || apicultor.getColmeias().isEmpty()) {
            return "Nenhuma colmeia encontrada para o apicultor " + apicultorRecebido.getNome();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Colmeias do apicultor ").append(apicultor.getNome()).append(":\n");
        for (Colmeia c : apicultor.getColmeias()) {
            sb.append(c.toString()).append("\n");
        }

        return sb.toString();
    }
}
