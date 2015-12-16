package br.edu.ufabc.sd2015.projeto.principal;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.Job;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String vec =  "/bin/sh -c ls -l" ;
		Job j = new Job(vec,1,20,20,10,10);
		//CJob cj = new CJob();
		//System.out.println(cj.runCmd(j).getOutput());
	}

}
