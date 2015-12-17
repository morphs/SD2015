package br.edu.ufabc.sd2015.projeto.server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;


public class Controller extends UnicastRemoteObject implements ServerInterface{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5716897884693689319L;

	ArrayList<ServerInterface> servers = new ArrayList<ServerInterface>();
	ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
	
	// construtor
	public Controller(int serversNumber,int clientNumber) throws RemoteException, MalformedURLException, NotBoundException{
		for (int i = 0; i < serversNumber; i++) {
			System.out.println("Controller: Adicionando o servidor "+i);
			servers.add((ServerInterface) Naming.lookup("rmi://localhost/Servers/Server"+i+"/"));
		}
		for (int i = 0; i < clientNumber; i++) {
			System.out.println("Controller: Adicionando o client "+i);
			clients.add((ClientInterface) Naming.lookup("rmi://localhost/Clients/Client"+i+"/"));
		}	
	}
	//fim construtor
	
	//Pega a lista de Clientes
	@SuppressWarnings("unused")
	public String[] getClientList() throws RemoteException {
		String[] retorno = new String [clients.size()];
		for (int i = 0; i < clients.size();i++) {
			retorno[i] = clients.get(i).getClientName();				   				   			
		}
		if(clients.size()>=1){
			return retorno;
		}else{
			return null;
		}
		
	}
	//Fim lista de servidores
	
	@Override
	public int addJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		
		clients.get(0).runJob(j);
		return 0;
	}

	@Override
	public int finishJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Job getLog(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sayHi(String RMIaddress) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getJobList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getServerList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
