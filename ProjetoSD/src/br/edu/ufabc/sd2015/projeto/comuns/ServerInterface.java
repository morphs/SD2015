package br.edu.ufabc.sd2015.projeto.comuns;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ufabc.sd2015.projeto.comuns.Job;

public interface ServerInterface extends Remote {
	
	//Definições de interface
	public String[] getJobList() throws RemoteException;
	
	public String[] getClientList() throws RemoteException;

	public String[] getServerList() throws RemoteException;
	
	public int addJob(Job j) throws IOException;	
   
	public int sendJob(Job j) throws RemoteException;	
	
	public int finishJob(Job j) throws RemoteException;
	
	public Job getLog(String filename) throws RemoteException;
	
	public void sayHi(String RMIaddress) throws RemoteException;
	
	//Fim definições

 //Constantes	
 int OK    =  1;
 int ERROR =  0;
//Fim constantes
		
	
}
