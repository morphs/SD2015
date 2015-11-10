

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4072279396821515725L;
	/**
	 * 
	 */
	//variaveis
	private ServerInterface controller;
	private JPanel contentPane;
	private JTextPane caixaTexto;
	private JList<String> listaArquivos;
	private JButton btnList;
	private JButton btnNew;
	private JButton btnRead;
	private JButton btnWrite;
	private JLabel lblArquivoAtual;
	private JLabel lblArquivosDisponvel;
	private DefaultListModel<String> model;
	
	//fim variaveis
	
	//main
	public static void main(String[] args) {
		//thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Client.....");
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Erro na main do client: "+e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
	// fim main
	
	//construtor
	public Client() {
		
		try {
			controller = (ServerInterface) Naming.lookup("rmi://localhost/Servidor/Controller/");
			System.out.println("Binder encontrado!");
			
		} catch (MalformedURLException e1) {
		System.out.println("Erro de url mal formada:"+e1.getMessage());
			e1.printStackTrace();
		} catch (RemoteException e1) {
			System.out.println("Erro de falha remota:"+e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			System.out.println("Erro de bind ou conexão:"+e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
					
		
		// swing
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
        // swing
		

		contentPane.add(listaArquivos);
        // list
		btnList = new JButton("List");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.clear();
					String[] list = controller.getList();
					if (list!=null){
					    for(String file: list)						
					    model.addElement(file);				
					    listaArquivos.updateUI();}
				     else{
				    	 JOptionPane.showMessageDialog(null,"Falha ao obter a lista de arquivos!","Mensagem de erro:",JOptionPane.ERROR_MESSAGE);
				     }
						
				} catch (RemoteException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
				
				
			}
		});
		// end list
		
        // new
		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("Entre com o nome do arquivo:");
				if(name != null){
					if(name.length() != 0){
						try {
							int result = controller.newFile(name);
							if (result==1)
							    JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso!");
							else
								JOptionPane.showMessageDialog(null, "Falha ao criar o arquivo!");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(null,"Nome de arquivo inválido!","Mensagem de erro:",JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		//end new
		
        // read
		btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = listaArquivos.getSelectedValue().toString(); 
				String result;
				try {
					result = controller.readFile(filename);
					if (result!=null)
						caixaTexto.setText(result);					
				} catch (RemoteException e1) {
					System.out.println("Falha ao ler arquivo!");
					e1.printStackTrace();
				}
			}
		});
		// end read
		
        // write
		btnWrite = new JButton("Write");
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = listaArquivos.getSelectedValue().toString(); 
				String content = caixaTexto.getText();
				try {
					int result = controller.writeFile(filename, content);
					if (result==1)
					    JOptionPane.showMessageDialog(null, "Arquivo escrito com sucesso!");
					else
						JOptionPane.showMessageDialog(null, "Falha ao escrever no arquivo!");
					
				} catch (RemoteException e1) {
					System.out.println("Falha ao escrever arquivo!");
					e1.printStackTrace();
				}
			}
		});
		// end write
		
		// swing 
		btnWrite.setBounds(571, 123, 117, 25);
		contentPane.add(btnWrite);
		btnRead.setBounds(571, 86, 117, 25);
		contentPane.add(btnRead);
		btnNew.setBounds(571, 49, 117, 25);
		contentPane.add(btnNew);
		btnList.setBounds(571, 12, 117, 25);
		contentPane.add(btnList);
		
		lblArquivoAtual = new JLabel("Arquivo atual:");
		lblArquivoAtual.setBounds(12, 282, 352, 15);
		contentPane.add(lblArquivoAtual);

		lblArquivosDisponvel = new JLabel("Arquivos disponíveis:");
		lblArquivosDisponvel.setBounds(12, 12, 188, 15);
		contentPane.add(lblArquivosDisponvel);
		// swing
	}
	





}
