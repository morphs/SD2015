package br.edu.ufabc.sd2015.projeto1;

public class MainServidores {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Servidor sv1,sv2,sv3;
		sv1 = new Servidor("sv1", 21001);
		sv2 = new Servidor("sv2", 21002);
		sv3 = new Servidor("sv3", 21003);
		sv1.start();
		sv2.start();
		sv3.start();

	}

}
