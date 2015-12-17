package br.edu.ufabc.sd2015.projeto.server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.KeyManager;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;
import soa.atomicrmi.Transaction;
import soa.atomicrmi.TransactionalUnicastRemoteObject;

public class Controller extends TransactionalUnicastRemoteObject implements ServerInterface{
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
		Registry reg = LocateRegistry.getRegistry();
		ClientInterface cli;
		ClientInterface cli2;
		Transaction transaction = null;
		for(int i = 0;i<clients.size();i++){
			
			if (clients.get(i).getStatus() == 0) {
				try {
					System.out.println(Arrays.toString(reg.list()));
					System.out.println("Transa gostosa");
					cli = (ClientInterface) reg.lookup("Clients/"+clients.get(i).getClientName()+System.getProperty("file.separator"));
					transaction = new Transaction(reg);
				
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					return -1;
				}
				cli =  (ClientInterface) transaction.accesses(clients.get(i), 1);
				
				KeyManager km = new KeyManager();
				transaction.start();
				cli.runJob(km.encrypt(j, "SD2015"));
				transaction.commit();
				return 1;

			}
			if(i == clients.size()-1){
				transaction.rollback();
				return -1;
			}
		}

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
