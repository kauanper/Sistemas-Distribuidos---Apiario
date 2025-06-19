package Trabalho2.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoRMI extends Remote {
    byte[] doOperation(String objectReference, String methodId, byte[] arguments) throws RemoteException;
}