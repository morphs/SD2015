package br.edu.ufabc.sd2015.principal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.edu.ufabc.sd2015.cliente.Client;
import br.edu.ufabc.sd2015.controlador.Controller;
import br.edu.ufabc.sd2015.servidor.Servidor;

public class Main {

	public static void main(String[] args) {
		int serversNumber = 2;
		ArrayList<Servidor> serversList = new ArrayList<>();
		
		
		//Client cli;
				
			try {
				for (int i = 0; i < serversNumber; i++) {
					Naming.rebind("rmi://localhost/Servidor/Server"+i+"/", new Servidor("Server"+i));
					Naming.rebind("rmi://localhost/Servidor/Controller", new Controller(2));
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		
		//cli = new Client();
		
	

	//	cli.run();

	}

}
