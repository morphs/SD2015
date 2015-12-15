package br.edu.ufabc.sd2015.projeto.server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;


public class Arrumar_Controle extends UnicastRemoteObject implements ServerInterface{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5716897884693689319L;

	ArrayList<ServerInterface> servers = new ArrayList<ServerInterface>();
	
	// construtor
	public Arrumar_Controle(int serversNumber) throws RemoteException, MalformedURLException, NotBoundException{
		for (int i = 0; i < serversNumber; i++) {
			System.out.println("Iniciando controller...");
			servers.add((ServerInterface) Naming.lookup("rmi://localhost/Servidor/Server"+i+"/"));
		}		
	}
	//fim construtor
	
	//Pega a lista de servidores
	@SuppressWarnings("unused")
	public String[] getList() throws RemoteException {
		for (int i = 0; i < servers.size()-1;i++) {
			if (!(Arrays.deepEquals(servers.get(i).getList(), servers.get(i+1).getList())))
			   return null;
			else
				return servers.get(0).getList();				   				   			
		}
		return null;
	}
	//Fim lista de servidores
	
	//Métodos de arquivo
	public int newFile(String filename) throws RemoteException {
		System.out.println("Controller: newFile");
		int result = 1;
		for (int i = 0; i < servers.size(); i++) {
		//	result *= servers.get(i).newFile(filename);			
		}
		return result;		
	}
	
	public int writeFile(String filename, String content) throws RemoteException {
		System.out.println("Controller: writeFile");
		int result = 1;
		for (int i = 0; i < servers.size(); i++) {
		//	result *= servers.get(i).writeFile(filename, content);	
		}
		return result;
	}
	
//	public String readFile(String filename) throws RemoteException {
	//	return servers.get((int)Math.random()*servers.size()).readFile(filename);  // escolhe um server aleatório
		
//	}
	//Fim métodos de arquivo

	@Override
	public int sendJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Job getLog(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int finishJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
