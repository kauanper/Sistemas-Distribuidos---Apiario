package POJO;

import Databases.ApicultorRepository;

public interface ValidaApicultor {

    default Apicultor validarApicultorOuCriar(Apicultor apicultorRecebido) {
        Apicultor existente = ApicultorRepository.buscarPorId(apicultorRecebido.getId());
        return (existente == null) ? apicultorRecebido : existente;
    }

    default Apicultor validarApicultorExistente(Apicultor apicultorRecebido) {
        return ApicultorRepository.buscarPorId(apicultorRecebido.getId());
    }
}
