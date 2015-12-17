package br.edu.ufabc.sd2015.projeto.comuns;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

import javax.crypto.SealedObject;

public interface ClientInterface extends Remote {
	
	
	public Job runJob(SealedObject j) throws RemoteException;
	
	public String getClientName() throws RemoteException;
	
	public void sayHi(String RMIaddress) throws RemoteException;
	

}
