

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
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

	/**
	 * 
	 */
	private ServerInterface controller;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Socket client;
	private static final int PORTA = 20000;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("client.....");
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	
	
	
	
	public Client() {
		
		try {
			controller = (ServerInterface) Naming.lookup("rmi://localhost/Servidor/Controller/");
			System.out.println("deu bom");
			
		} catch (MalformedURLException e1) {
		//	JOptionPane.showMessageDialog(getComponent(0), "chu");
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
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
		model = new DefaultListModel<String>();
		listaArquivos = new JList<String>(model);
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
						try {
							System.out.println("try");
							controller.newFile(name);
							System.out.println("new file ok");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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

				setCurrentText(resultado);

			}
		});
		btnRead.setBounds(571, 86, 117, 25);
		contentPane.add(btnRead);

		btnWrite = new JButton("Write");
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = listaArquivos.getSelectedValue().toString(); 
				String content = getCurrentText(); 
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
	}

	//Fim construtor

	public String getCurrentText(){
		return caixaTexto.getText();		
	}
	public void setCurrentText(String text){
		caixaTexto.setText(text);		
	}

	public void setListaDeArquivos(){

		//ListaArquivos.updateUI();

	}



	//Requisicao genérica
	public Resposta request (Requisicao request){
		try{
			if(client == null || client.isConnected()){
				client = new Socket("localhost", PORTA);
			}

			client.setKeepAlive(true);
			ObjectOutputStream clientOutput = new ObjectOutputStream(client.getOutputStream());		        
			clientOutput.writeObject(request);

			ObjectInputStream  clientInput  = new ObjectInputStream(client.getInputStream());
			return (Resposta)clientInput.readObject();
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
		return null;

	}
	//Fim requisicao genérica

	//Requisicao de lista de arquivos
	public void requestFilesList(){
		Requisicao request = new Requisicao();
		request.setMessageType(Requisicao.GET_LIST);
		Resposta response = request(request); 
		if(response != null){
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
				System.out.println("erro ao preencher a lista");
			}   
		}else{
			System.out.println("Resposta nula");
		}
	}

	//Requisição de novo arquivo
	public void requestNewFile(String name){
		Requisicao req = new Requisicao();
		req.setMessageType(Requisicao.NEW_FILE);
		req.setFileName(name);
		Resposta response = request(req); 
		if(response != null){
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
		}else{
			System.out.println("Requisicao nula");
		}

	}
	//Fim requisição de novo arquivo

	//Requisição de leitura
	private String requestFileRead(String fileName){
		Requisicao req = new Requisicao();
		req.setMessageType(Requisicao.READ_FILE);
		req.setFileName(fileName);
		Resposta response = request(req); 
		String retorno = "";
		if(response != null){

			if (response.getMessageStatus() == Resposta.GET_FILE_OK){
				retorno = response.getFileContent();	  
				System.out.println("arquivo retornado");
			}
			else{
				System.out.println("Erro ao ler o arquivo");
			}
		}else{
			System.out.println("Requisicao nula");
		}
		return retorno;
	}
	//fimRequisição de leitura


	//Requisição de escrita de arquivo
	private void requestFileWrite(String fileName,String content){
		Requisicao request = new Requisicao();
		request.setMessageType(Requisicao.WRITE_FILE);
		request.setFileName(fileName);
		request.setFileContent(content);
		Resposta response = request(request); 
		if(response != null){

			if (response.getMessageStatus() == Resposta.FILE_WRITE_OK){
				JOptionPane.showMessageDialog(null, "Arquivo escrito com sucesso!");
				setCurrentText("");
			}
			else{
				System.out.println("Erro ao escrever o arquivo");
			}
		}else{
			System.out.println("Requisicao nula");
		}
	}
	//Fim Requisição de escrita de arquivo	

	private JTextPane caixaTexto;
	private JList<String> listaArquivos;
	private JButton btnList;
	private JButton btnNew;
	private JButton btnRead;
	private JButton btnWrite;
	private JLabel lblArquivoAtual;
	private JLabel lblArquivosDisponvel;
	private DefaultListModel<String> model;
	private String[] listaDeArquivosArray = {"test"};



}
