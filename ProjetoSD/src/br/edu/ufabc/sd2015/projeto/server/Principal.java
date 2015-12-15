package br.edu.ufabc.sd2015.projeto.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;

public class Principal implements ServerInterface{
	private FJob_ServerGUI frame;
    static ArrayList<Job> jobsList = new ArrayList<Job>();
    ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
	public static void main(String[] args) {
		new Thread(() -> new FJob_ServerGUI().setVisible(true)).start();
        
      
	}

	
	 static void addJobtoList(Job job){
		jobsList.add(job);
		
	}


	@Override
	public String[] getList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int addJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int sendJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
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
	public void sayHi(String RMIaddress) throws RemoteException{
		try {
			clients.add((ClientInterface) Naming.lookup(RMIaddress));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
