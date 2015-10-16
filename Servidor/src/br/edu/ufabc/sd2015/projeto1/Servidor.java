package br.edu.ufabc.sd2015.projeto1;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Servidor {
	
	static String sep = System.getProperty("file.separator");
    static String Diretorio;
    static HashMap<Integer, File> mapa = new HashMap<Integer, File>();
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
		System.out.println(Folder);
		if(!(Folder.exists() && Folder.isDirectory())){
			Folder.mkdirs();
			System.out.println("Diretório criado "+Folder);
		}
		//test
		File teste = new File(Diretorio+"arquivoteste");
		if(!teste.exists()){try {
			teste.createNewFile();
		} catch (IOException e) {		
		}}
		//test
		
		File[] Files = Folder.listFiles();
	   for (int i = 0; i < Files.length; i++) {
		mapa.put(i, Files[i]);
			}
	   
		mapa.forEach((i,f) -> System.out.println(String.valueOf(i)+" : "+f.getAbsolutePath()));  //test
	
			
								
					
					 
			            	
			                 	ServerSocket Server;
								try {
									System.out.println("try");
									Server = new ServerSocket(21000);
									do{
										System.out.println("do");
										Socket Client = Server.accept();
										ObjectInputStream ServerInput = new ObjectInputStream(Client.getInputStream());
										Requisicao Request = (Requisicao)ServerInput.readObject();
										Resposta Response = new Resposta();
										
                                        switch (Request.getMessageType()) {
										case Requisicao.GET_LIST:
											String[] FilesList = new String[mapa.size()];
											mapa.forEach((i,f) -> FilesList[i] = f.getName());
											Response.setListFiles(FilesList);
									    	Response.setMessageStatus(Resposta.GET_LIST_OK);
											System.out.println("oks");
											break;

										default:
											break;
										}
                                         System.out.println("enviando...");
										 ObjectOutputStream ServerOutput = new ObjectOutputStream(Client.getOutputStream());
										 ServerOutput.writeObject(Response);	                
										 
										 ServerOutput.close();
										 ServerInput.close();
							             Client.close();
									}while(true);

									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                 	
			

		
	}

}
