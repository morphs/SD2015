package br.edu.ufabc.sd2015.projeto.comuns;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	
	public Job runJob(Job j) throws RemoteException;
	
	public String[] getClientList() throws RemoteException;
	
	
	public void sayHi(String RMIaddress) throws RemoteException;
	

}
