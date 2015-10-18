package br.edu.ufabc.sd2015.projeto1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Servidor {
	
	static String sep = System.getProperty("file.separator");
    static String Diretorio;
    //static HashMap<Integer, File> mapa = new HashMap<Integer, File>();
    static File Folder;

	public static void main(String[] args) {
		System.out.println("....");
		
		String quemsou = "1";
		if(args.length != 0){quemsou=args[0];}
		Diretorio = System.getProperty("user.home")+sep+"ServidorDeArquivos"+sep+quemsou+sep;
		//Pasta do usuário
		//               --ServidorDeArquivos
	    //                                 --servidor (thread) 1,2 e 3....
		Folder = new File(Diretorio);
		System.out.println("Criando pasta:" +Folder);
		if(!(Folder.exists() && Folder.isDirectory())){
			Folder.mkdirs();
			System.out.println("Diretório criado "+Folder);
		}else{
			System.out.println("Diretório já existe ou caminho inválido");
		}
		//test
//		File teste = new File(Diretorio+"arquivoteste");
//		if(!teste.exists()){try {
//			teste.createNewFile();
//		} catch (IOException e) {		
//		}}
//		//test
//		
		File[] Files = Folder.listFiles();
//	   for (int i = 0; i < Files.length; i++) {
//		mapa.put(i, Files[i]);
//			}
//	   
		//mapa.forEach((i,f) -> System.out.println(String.valueOf(i)+" : "+f.getAbsolutePath()));  //test
	
		ServerSocket Server;
		try {
			System.out.println("Cria socket");
			Server = new ServerSocket(21000);
			do{
				
				Socket client = Server.accept();
				System.out.println("Conexão1: "+client);
				ObjectInputStream ServerInput = new ObjectInputStream(client.getInputStream());
				Requisicao request = (Requisicao)ServerInput.readObject();
				Resposta response = new Resposta();
				
				//Case da requisicao
				File novoArquivo;
                switch (request.getMessageType()) { 
				case Requisicao.GET_LIST:
					//String[] filesList = new String[mapa.size()];
					//mapa.forEach((i,f) -> FilesList[i] = f.getName());
					response.setListFiles(getFileList());
			    	response.setMessageStatus(Resposta.GET_LIST_OK);
					System.out.println("oks");
					break;
				case Requisicao.NEW_FILE:
					novoArquivo = new File(Diretorio+request.getFileName());
					if(!novoArquivo.exists()){
						try {
							novoArquivo.createNewFile();
							
						} catch (IOException e) {
							response.setMessageStatus(Resposta.FILE_WRITE_ERROR);
							System.out.println("Erro ao criar novo arquivo:"+e.getMessage());
						}
					}
					response.setListFiles(getFileList());
					response.setMessageStatus(Resposta.FILE_WRITE_OK);
					System.out.println("Arquivo criado com sucesso");
					break;
					
				case Requisicao.WRITE_FILE:
					
					break;
					
				case Requisicao.READ_FILE:
					novoArquivo = new File(Diretorio+request.getFileName());
					String content ="";
					if(!novoArquivo.exists()){
						response.setMessageStatus(Resposta.FILE_NOT_FOUND);
					}else{
						content = new String(java.nio.file.Files.readAllBytes(Paths.get(Diretorio+request.getFileName())));
						response.setMessageStatus(Resposta.GET_FILE_OK);
					}
					response.setFileContent(content);
					
					break;
				default:
					System.out.println("Requisição inválida");
					break;
				}
                 System.out.println("enviando...");
				 ObjectOutputStream serverOutput = new ObjectOutputStream(client.getOutputStream());
				 serverOutput.writeObject(response);	                
				 
				 serverOutput.close();
				 ServerInput.close();
	             client.close();
			}while(true);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
								
					
					 
			            	
			                 	
			                 	
			

		
	}

	private static String[] getFileList(){
		File[] vFiles = Folder.listFiles();
		String[] retorno = new String[vFiles.length];
		for(int i =0;i<retorno.length;i++){
			retorno[i]= vFiles[i].getName();
		}
		return retorno;
}

}

