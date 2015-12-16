package br.edu.ufabc.sd2015.projeto.server;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;

public class Servidor extends UnicastRemoteObject implements ServerInterface{
		
	/**
	 * 
	 */
	
	//Variáveis 
	private static final long serialVersionUID = 1420785107657269733L;
	private static final String SEP = System.getProperty("file.separator");
    private String diretorio;
    private String svId;

    private File folder;
    //Fim variáveis
  
    //Construtor 
    public Servidor(String id) throws RemoteException{
   	
    	this.setSvId(id);
    	diretorio = System.getProperty("user.home")+SEP+"ServidorDeJobs"+SEP+id+SEP;
		folder = new File(diretorio);
		System.out.println("Criando diretório:" +folder);
		if(!(folder.exists() && folder.isDirectory())){
			folder.mkdirs();
			System.out.println("Diretório criado "+folder);
		}else{
			System.out.println("Diretório já existe ou caminho inválido");
		}

    }

    //Fim Construtor
	 
    //Gets e sets
	public String getSvId() {
		return svId;
	}
	public void setSvId(String svId) {
		this.svId = svId;
	}
	//Fim gets e sets
	
	//Listar arquivos
	public String[] getJobList() throws RemoteException {
		File[] vFiles = folder.listFiles();
		String[] fileList = new String[vFiles.length];
        for (int i = 0; i < vFiles.length; i++) {
			fileList[i] = vFiles[i].getName();					
		}		
		return fileList;
	}
	
	//Métodos de Jobs
	@SuppressWarnings("unchecked")
	public int addJob(Job j) throws RemoteException {
		System.out.println("servidor: addJob");
		String name = "Job_"+j.getId();
		File newFile = new File(name);
		if(!newFile.exists()){
			try {
				//Escreve json
				JSONObject obj = new JSONObject();
				obj.put("id", j.getId());
				obj.put("command", j.getCommand());
				obj.put("executable", j.getExecutable());
				obj.put("time",j.getTime());
				obj.put("priority",j.getPriority());
				obj.put("group",j.getGroup());
				obj.put("grouporder",j.getGroupOrder());
				obj.put("output",j.getOutput());
				obj.put("outputfile",j.getOutputFile());
				
				//Cria arquivo
				FileWriter file  = new FileWriter(diretorio+name);
				file.write(obj.toJSONString());
				file.flush();
				file.close();
				System.out.println(newFile.getAbsolutePath()+" criado.");
				
			} catch (IOException e) {				
				System.out.println("Erro ao criar novo job:"+e.getMessage());
				return ServerInterface.ERROR;
			}
		}else{
			return ServerInterface.ERROR;
		}
		System.out.println("Arquivo criado com sucesso!");
		return ServerInterface.OK;
	}
	
	public Job getLog(String filename) throws RemoteException {
		Job jo;
		JSONParser parser = new JSONParser();

				try {
					System.out.println("Tentando ler:"+filename);
					Object obj = parser.parse(new FileReader(diretorio+filename));
					JSONObject jsonObject = (JSONObject) obj;
					long id = (long) jsonObject.get("id");
					String[] command = ((String) jsonObject.get("command")).split(" ");
					long time = (long) jsonObject.get("time");
					File executable = (File) jsonObject.get("executable");
					long priority = (long) jsonObject.get("priority");
					long group = (long) jsonObject.get("group");
					long grouporder = (long) jsonObject.get("grouporder");
					String output = (String) jsonObject.get("output");
					File outputfile = (File) jsonObject.get("outputFile");
					jo = new Job(executable,id,command,priority,time,group,grouporder,output,outputfile);
					
					return jo;					
				} catch (IOException e) {					
					System.out.println("Erro ao ler o arquivo:"+e.getMessage());
					return null;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	
	}
//Implementar
	@Override
	public int sendJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	//Implementar
	@Override
	public int finishJob(Job j) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sayHi(String RMIaddress) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getServerList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getClientList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

// Fim métodos de jobs

	
}

