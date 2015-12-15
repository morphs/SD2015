package br.edu.ufabc.sd2015.projeto.server;


import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ufabc.sd2015.projeto.comuns.Job;

public interface ServerInterface extends Remote {
	
	//Definições de interface
	public String[] getList() throws RemoteException;
	
	public int addJob(Job j) throws RemoteException;	
   
	public int sendJob(Job j) throws RemoteException;	
	
	public int finishJob(Job j) throws RemoteException;
	
	public Job getLog(String filename) throws RemoteException;
	//Fim definições

 //Constantes	
 int OK    =  1;
 int ERROR =  0;
//Fim constantes
		
	
}
