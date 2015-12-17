package br.edu.ufabc.sd2015.projeto.server;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
	private static final String SEP = System.getProperty("file.separator");
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
				//write sdtOut
				List<String> Sdtout = cli.runJob(km.encrypt(j, "SD2015"));
				String path = System.getProperty("user.home")+SEP+"ServidorDeJobs"+SEP+"Jobs"+SEP+"job_"+String.valueOf(j.getId())
				+SEP+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())+".txt";
				File f = new File(path);
				try {
					Files.createDirectories(Paths.get(path).getParent());
					f.createNewFile();
					Files.write(Paths.get(path), Sdtout);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				
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

	
	
	public void writeSdtOut(List<String> sdtout){
		
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
