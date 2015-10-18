package br.edu.ufabc.sd2015.projeto1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

	private JPanel contentPane;
	private Socket client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor
	 * Create the frame.
	 */
	public Client() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		caixaTexto = new JTextPane();
		caixaTexto.setBounds(12, 307, 676, 130);
		contentPane.add(caixaTexto);
		 model = new DefaultListModel();
		listaArquivos = new JList(model);
		listaArquivos.setBounds(12, 26, 188, 244);
		
		
		contentPane.add(listaArquivos);
		
		btnList = new JButton("List");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				requestFilesList();
			}
		});
		btnList.setBounds(571, 12, 117, 25);
		contentPane.add(btnList);
		
		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("Entre com o nome do arquivo");
				if(name != null){
					if(name.length() != 0){
						requestNewFile(name);
					}else{
						JOptionPane.showMessageDialog(null,"Nome inválido","Mensagem de erro",JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnNew.setBounds(571, 49, 117, 25);
		contentPane.add(btnNew);
		
		btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = listaArquivos.getSelectedValue().toString(); 
				//
				//Verificar erros nulo etc 
				//
				String resultado = requestFileRead(name);
				//
				//Verificar erros mimimimi
				//
				
				caixaTexto.setText(resultado);
				
			}
		});
		btnRead.setBounds(571, 86, 117, 25);
		contentPane.add(btnRead);
		
		btnWrite = new JButton("Write");
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = listaArquivos.getSelectedValue().toString(); 
				String content = caixaTexto.getText(); 
				requestFileWrite(name,content);
			}
		});
		btnWrite.setBounds(571, 123, 117, 25);
		contentPane.add(btnWrite);
		
		lblArquivoAtual = new JLabel("Arquivo Atual:");
		lblArquivoAtual.setBounds(12, 282, 352, 15);
		contentPane.add(lblArquivoAtual);
		
		lblArquivosDisponvel = new JLabel("Arquivos Disponíveis:");
		lblArquivosDisponvel.setBounds(12, 12, 188, 15);
		contentPane.add(lblArquivosDisponvel);
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setText("127.0.0.1");
		formattedTextField.setBounds(246, 52, 171, 25);
		contentPane.add(formattedTextField);
		
		formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setText("21000");
		formattedTextField_1.setBounds(429, 52, 52, 25);
		contentPane.add(formattedTextField_1);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConectar.setBounds(310, 86, 117, 25);
		contentPane.add(btnConectar);
		
		lblSPraTestes = new JLabel("Só pra testes");
		lblSPraTestes.setBounds(245, 27, 137, 15);
		contentPane.add(lblSPraTestes);
	}
	
	//Fim construtor
	
	public String getCurrentText(){
		return caixaTexto.getText();		
	}
	
	public void setListaDeArquivos(){
		
		//ListaArquivos.updateUI();
		
	}
	
	public void requestFilesList(){
		try {
			if(client == null || client.isConnected()){
				client = new Socket("localhost", 21000);
			}
			
			 client.setKeepAlive(true);
		     ObjectOutputStream clientOutput = new ObjectOutputStream(client.getOutputStream());		        
		     Requisicao request = new Requisicao();
		     request.setMessageType(Requisicao.GET_LIST);
		     clientOutput.writeObject(request);
		     
		     ObjectInputStream  clientInput  = new ObjectInputStream(client.getInputStream());
	         Resposta response = (Resposta)clientInput.readObject();
	         
	         
	         if (response.getMessageStatus() == Resposta.GET_LIST_OK){
	        	   listaDeArquivosArray = response.getListFiles();
	        	  model.clear();
	        	   System.out.println( listaDeArquivosArray.length);
	        	   for(String s: listaDeArquivosArray){
	        		   System.out.println(s);
	        		   model.addElement(s);
	        	   }
	        	   System.out.println("getlistok");
	               listaArquivos.updateUI();
	              
	               
	            }
	            else{
	                System.out.println("ops");
	            }
		     
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	public void requestNewFile(String name){
		try {
			//tentativa de conexão
			if(client == null || client.isConnected()){
				client = new Socket("localhost", 21000);
			}
			
			 client.setKeepAlive(true);
		     ObjectOutputStream clientOutput = new ObjectOutputStream(client.getOutputStream());		        
		     Requisicao request = new Requisicao();
		     
		     //Definindo a requisição
		     request.setMessageType(Requisicao.NEW_FILE);
		     request.setFileName(name);
		     
		     
		     clientOutput.writeObject(request);
		     
		     ObjectInputStream  clientInput  = new ObjectInputStream(client.getInputStream());
	         Resposta response = (Resposta)clientInput.readObject();
	         
	         if (response.getMessageStatus() == Resposta.FILE_WRITE_OK){
	        	   listaDeArquivosArray = response.getListFiles();
	        	  model.clear();
	        	   System.out.println(listaDeArquivosArray.length);
	        	   for(String s: listaDeArquivosArray){
	        		   System.out.println(s);
	        		   model.addElement(s);
	        	   }
	        	   System.out.println("File write ok");
	               listaArquivos.updateUI();
	              
	               
	            }
	            else{
	                System.out.println("File write error");
	            }
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String requestFileRead(String fileName){
		String retorno = "";
		try {
			if(client == null || client.isConnected()){
				client = new Socket("localhost", 21000);
			}
			
			 client.setKeepAlive(true);
		     ObjectOutputStream clientOutput = new ObjectOutputStream(client.getOutputStream());		        
		     Requisicao request = new Requisicao();
		     request.setMessageType(Requisicao.READ_FILE);
		     request.setFileName(fileName);
		     clientOutput.writeObject(request);
		     
		     ObjectInputStream  clientInput  = new ObjectInputStream(client.getInputStream());
	         Resposta response = (Resposta)clientInput.readObject();
	         
	         if (response.getMessageStatus() == Resposta.GET_FILE_OK){
	        	   retorno = response.getFileContent();	  
	        	   System.out.println("arquivo retornado");
	            }
	            else{
	                System.out.println("Erro ao ler o arquivo");
	            }
		     
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retorno = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retorno = "";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retorno = "";
		}
		return retorno;
	}
	
	private void requestFileWrite(String fileName,String content){
		
		try {
			if(client == null || client.isConnected()){
				client = new Socket("localhost", 21000);
			}
			
			 client.setKeepAlive(true);
		     ObjectOutputStream clientOutput = new ObjectOutputStream(client.getOutputStream());		        
		     Requisicao request = new Requisicao();
		     request.setMessageType(Requisicao.WRITE_FILE);
		     request.setFileName(fileName);
		     request.setFileContent(content);
		     clientOutput.writeObject(request);
		     
		     ObjectInputStream  clientInput  = new ObjectInputStream(client.getInputStream());
	         Resposta response = (Resposta)clientInput.readObject();
	         
	         if (response.getMessageStatus() == Resposta.FILE_WRITE_OK){
	        	   JOptionPane.showMessageDialog(null, "Arquivo escrito com sucesso!");
	            }
	            else{
	                System.out.println("Erro ao escrever o arquivo");
	            }
		     
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
	
	
	private JTextPane caixaTexto;
	private JList listaArquivos;
	private JButton btnList;
	private JButton btnNew;
	private JButton btnRead;
	private JButton btnWrite;
	private JLabel lblArquivoAtual;
	private JLabel lblArquivosDisponvel;
	private JFormattedTextField formattedTextField;
	private JFormattedTextField formattedTextField_1;
	private JButton btnConectar;
	private JLabel lblSPraTestes;
	private DefaultListModel model;
	private String[] listaDeArquivosArray = {"test"};
	
	
	
	
}
