package Trabalho2.remote;

import POJO.Apicultor;
import Services.AdicionarAbelhasService;
import Services.CriarColmeiaService;
import Services.ListarColmeiasService;
import Services.RemoverColmeiasService;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicoRMIImpl extends UnicastRemoteObject implements ServicoRMI {
    private final CriarColmeiaService criarService = new CriarColmeiaService();
    private final ListarColmeiasService listarService = new ListarColmeiasService();
    private final AdicionarAbelhasService adicionarService = new AdicionarAbelhasService();
    private final RemoverColmeiasService removerService = new RemoverColmeiasService();

    public ServicoRMIImpl() throws RemoteException, RemoteException { super(); }

    @Override
    public byte[] doOperation(String objectReference, String methodId, byte[] arguments) throws RemoteException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(arguments);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            switch (methodId) {
                case "1": { // criar colmeia
                    Apicultor apicultor = (Apicultor) ois.readObject();
                    int capacidadeAbelhas = ois.readInt();
                    int capacidadeMel = ois.readInt();
                    String resultado = criarService.execute(capacidadeAbelhas, capacidadeMel, apicultor);
                    return serializarResultado(resultado);
                }
                case "2": { // listar colmeias
                    Apicultor apicultor = (Apicultor) ois.readObject();
                    String resultado = listarService.execute(apicultor);
                    return serializarResultado(resultado);
                }
                case "3": { // adicionar abelhas
                    Apicultor apicultor = (Apicultor) ois.readObject();
                    String idColmeia = ois.readUTF();
                    int operarias = ois.readInt();
                    int rainha = ois.readInt();
                    String resultado = adicionarService.execute(operarias, rainha, apicultor, idColmeia);
                    return serializarResultado(resultado);
                }
                case "4": { // remover colmeia
                    Apicultor apicultor = (Apicultor) ois.readObject();
                    String idColmeia = ois.readUTF();
                    String resultado = removerService.execute(apicultor, idColmeia);
                    return serializarResultado(resultado);
                }
                default:
                    return serializarResultado("Método inválido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return serializarResultado("Erro ao processar a operação: " + e.getMessage());
        }
    }

    private byte[] serializarResultado(String resultado) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(resultado);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
