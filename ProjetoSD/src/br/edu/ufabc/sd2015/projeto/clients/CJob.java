package br.edu.ufabc.sd2015.projeto.clients;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.KeyManager;

public class CJob extends UnicastRemoteObject implements ClientInterface {
	
	
	private static final String SEP = System.getProperty("file.separator");
    private String diretorio;
    private String clId;
    private File folder;
    
	public CJob(String id) throws RemoteException{
		   	
	    	this.setClId(id);
	    	diretorio = System.getProperty("user.home")+SEP+"ServidorDeJobs"+SEP+id+SEP;
			folder = new File(diretorio);
			System.out.println("Criando diretório:" +folder);
			KeyManager km = new KeyManager();
			if(!km.areKeysPresent(folder)){
				km.generateKey(folder);
			}
		
			if(!(folder.exists() && folder.isDirectory())){
				folder.mkdirs();
				System.out.println("Diretório criado "+folder);
			}else{
				System.out.println("Diretório já existe ou caminho inválido");
			}
	    }


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Job runJob(Job j){
		
		try{
		//process construction					
		StringBuilder sb = new StringBuilder();
		Arrays.asList(j.getCommand()).forEach(s -> sb.append(s+" "));
		sb.deleteCharAt(sb.length()-1);
		String cmd = sb.toString();
		ProcessBuilder procbuilder;
		if(j.isHasFile()){
			procbuilder = new ProcessBuilder("/bin/bash","-c", cmd+" "+j.getExecutable().toString());
		}else{
			procbuilder = new ProcessBuilder("/bin/bash","-c", cmd);
		}
		
		System.out.println("cooooo::: "+procbuilder.command().toString());
		if(j.isHasFile()){
			procbuilder.directory(j.getExecutable().getParentFile());
		}else{
			procbuilder.directory(new File(System.getProperty("user.home")));
		}		
		procbuilder.redirectErrorStream(true);
		//Process execution
		//Read output
		
		File tempFile = new File("/tmp/job").createTempFile("job", "tmp");
		StringBuilder sb2 = new StringBuilder();
		procbuilder.redirectOutput(tempFile);
		//Process execution
		Process pro = procbuilder.start();
		int exitValue = pro.waitFor();
		List<String> text = Files.readAllLines(tempFile.toPath());
		text.forEach(S -> sb2.append(S+"\n"));
		String output = sb2.toString();

            System.out.println("\n\nExit Value is " + exitValue);
            System.out.println("\nsdtout:\n"+output);
            j.setOutput(output);
            //j.setOutputFile(outputFile);
            return j;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("The execution was interrupted:"+e.getMessage());
            return null;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("Input or output error:"+e.getMessage());
			return null;
		}
		
    }

	@Override
	public void sayHi(String RMIaddress) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getClId() {
		return clId;
	}

	public void setClId(String clId) {
		this.clId = clId;
	}

	@Override
	public String getClientName() throws RemoteException {
		return this.clId;
	}
	
	
}
