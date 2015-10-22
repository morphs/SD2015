package br.edu.ufabc.sd2015.controlador;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import br.edu.ufabc.sd2015.comuns.Requisicao;
import br.edu.ufabc.sd2015.comuns.Resposta;

public class Controller extends Thread {
	private int[] portas;
	private ServerSocket server;
	private Socket[] srvs;
	public Controller (int[] ports){
		portas = ports;
		srvs = new Socket[portas.length];
	}
    
    public void run(){
    	  try{
              server = new ServerSocket(20000);
             
             mainloop:
              do{
                  Socket cli = server.accept();
                  
                  ObjectInputStream input = new ObjectInputStream(cli.getInputStream());
                  ObjectOutputStream[] outs = new  ObjectOutputStream[portas.length];
                  ObjectInputStream[] ins = new ObjectInputStream[portas.length];
                  Resposta[] rs = new Resposta[portas.length];
                  Requisicao req = (Requisicao)input.readObject();
                 
                  
                  
                  
                  System.out.println("CONTROLLER - Requisicao Recebida");
                  for(int i = 0;i<portas.length;i++){
                	  srvs[i] = new Socket("localhost", portas[i]);
                	  srvs[i].setKeepAlive(true);
                	  outs[i] =  new ObjectOutputStream(srvs[i].getOutputStream());
                	  outs[i].writeObject(req);
                	  ins[i] = new ObjectInputStream(srvs[i].getInputStream());
                  }
             
            

                  for(int i = 0;i<portas.length;i++){
                	rs[i] =   (Resposta)ins[i].readObject();
                	if (rs[i].getMessageStatus() < 0){
						ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
						outserver.writeObject(rs[i]);		                 
		                outserver.close();
		                break mainloop;
					}
                  }
                                                                                                     
                  
                  if (req.getMessageType() == Requisicao.GET_LIST){
                	  if(!(Arrays.deepEquals(rs[0].getListFiles(), rs[1].getListFiles()) && Arrays.deepEquals(rs[1].getListFiles(), rs[2].getListFiles())
          					&& Arrays.deepEquals(rs[0].getListFilesMD5(), rs[1].getListFilesMD5())	&& Arrays.deepEquals(rs[1].getListFilesMD5(), rs[2].getListFilesMD5())
          					)){
          				     rs[0].setMessageStatus(Resposta.GET_LIST_ERROR);
          				     ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
          						outserver.writeObject(rs[0]);		                 
          		                outserver.close();
          		                break mainloop;
          					}                	  
                  }

                  //
                  //
                  //Tratar respostas:::::
                  //
                  //
                  ObjectOutputStream outserver = new ObjectOutputStream(cli.getOutputStream());
                  outserver.writeObject(rs[0]);
                  outserver.close();
                  
                  for(int i = 0;i<portas.length;i++){
                	  outs[i].close();
                	     ins[i].close();
                  }
                  

                  input.close();
              
                  
              } while (true);
              
          }
          
          catch(Exception ex){
              ex.printStackTrace();
          }
    }
    
    
}