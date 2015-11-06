package br.edu.ufabc.sd2015.controlador;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.edu.ufabc.sd2015.comuns.ServerInterface;
import br.edu.ufabc.sd2015.servidor.Servidor;

public class Controller implements ServerInterface{
    private int serversNumber;
    ArrayList<ServerInterface> servers = new ArrayList<>();
	public Controller(int serversNumber) throws RemoteException, MalformedURLException, NotBoundException{
		for (int i = 0; i < servers.size(); i++) {
			servers.add((ServerInterface) Naming.lookup("rmi://localhost/Servidor/Server"+i+"/"));
		}
		this.serversNumber = serversNumber;
		
	}
	
	public String[] getList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int newFile(String filename) throws RemoteException {
		int resultado = 1;
		for (int i = 0; i < servers.size(); i++) {
			resultado *= servers.get(i).newFile(filename);			
		}
		return resultado;
		
	}
	
	public int writeFile(String filename, String content) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String readFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String delteFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
