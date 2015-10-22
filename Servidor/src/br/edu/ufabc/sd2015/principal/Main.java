package br.edu.ufabc.sd2015.principal;

import br.edu.ufabc.sd2015.cliente.Client;
import br.edu.ufabc.sd2015.controlador.Controller;
import br.edu.ufabc.sd2015.servidor.Servidor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Servidor sv1,sv2,sv3;
		Controller co;
		final int[] portas = {21001,21002,21003};
		Client cli;
		sv1 = new Servidor("sv1", portas[0]);
		sv2 = new Servidor("sv2", portas[1]);
		sv3 = new Servidor("sv3", portas[2]);
		
		co = new Controller(portas);
		cli = new Client();
		co.start();
		sv1.start();
		sv2.start();
		sv3.start();

		cli.run();

	}

}
