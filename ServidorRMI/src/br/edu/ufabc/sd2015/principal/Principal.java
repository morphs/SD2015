package br.edu.ufabc.sd2015.principal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.edu.ufabc.sd2015.cliente.Client;
import br.edu.ufabc.sd2015.comuns.ServerInterface;
import br.edu.ufabc.sd2015.controlador.Controller;
import br.edu.ufabc.sd2015.servidor.Servidor;

public class Principal {

	public static void main(String[] args) {
		int serversNumber = 1;
		ArrayList<Servidor> serversList = new ArrayList<>();
		
		int d=3;
		Client cli;
		
		try {
			ServerInterface controller  = new Controller(1);
			ServerInterface server1     = new Servidor("0");
			Naming.rebind("rmi://localhost/Servidor/Server0/", server1);
		//	Naming.rebind("rmi://localhost/Servidor/Controller", controller);
			
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		
		
		
		
		
	//	cli = new Client();
		
	

	  //  cli.run();

	}

}
