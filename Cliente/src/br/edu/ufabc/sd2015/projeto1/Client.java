package br.edu.ufabc.sd2015.projeto1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JList;
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
		
		CaixaTexto = new JTextPane();
		CaixaTexto.setBounds(12, 307, 676, 130);
		contentPane.add(CaixaTexto);
		 model = new DefaultListModel();
		ListaArquivos = new JList(model);
		ListaArquivos.setBounds(12, 26, 188, 244);
		
		
		contentPane.add(ListaArquivos);
		
		btnList = new JButton("List");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RequestFilesList();
			}
		});
		btnList.setBounds(571, 12, 117, 25);
		contentPane.add(btnList);
		
		btnNew = new JButton("New");
		btnNew.setBounds(571, 49, 117, 25);
		contentPane.add(btnNew);
		
		btnRead = new JButton("Read");
		btnRead.setBounds(571, 86, 117, 25);
		contentPane.add(btnRead);
		
		btnWrite = new JButton("Write");
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
		btnConectar.setBounds(310, 86, 117, 25);
		contentPane.add(btnConectar);
		
		lblSPraTestes = new JLabel("Só pra testes");
		lblSPraTestes.setBounds(245, 27, 137, 15);
		contentPane.add(lblSPraTestes);
	}
	
	public String getCurrentText(){
		return CaixaTexto.getText();		
	}
	
	public void setListaDeArquivos(){
		
		//ListaArquivos.updateUI();
		
	}
	
	public void RequestFilesList(){
		Socket client;
		try {
			client = new Socket("localhost", 21000);
			 client.setKeepAlive(true);
		     ObjectOutputStream ClientOutput = new ObjectOutputStream(client.getOutputStream());		        
		     Requisicao Request = new Requisicao();
		     Request.setMessageType(Requisicao.GET_LIST);
		     ClientOutput.writeObject(Request);
		     
		     ObjectInputStream  ClientInput  = new ObjectInputStream(client.getInputStream());
	         Resposta Response = (Resposta)ClientInput.readObject();
	         
	         
	         if (Response.getMessageStatus() == Resposta.GET_LIST_OK){
	        	   ListaDeArquivosArray = Response.getListFiles();
	        	  
	        	   System.out.println( ListaDeArquivosArray.length);
	        	   for(String s: ListaDeArquivosArray){
	        		   System.out.println(s);
	        		   model.addElement(s);
	        	   }
	        	   System.out.println("getlistok");
	               ListaArquivos.updateUI();
	              
	               
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
	
	
	
	
	private JTextPane CaixaTexto;
	private JList ListaArquivos;
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
	private String[] ListaDeArquivosArray = {"test"};
	
	
	
	
}
