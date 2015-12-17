package br.edu.ufabc.sd2015.projeto.server;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufabc.sd2015.projeto.comuns.Job;

public class FJob_ServerGUI extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Servidor sv;
	private Controller ct;
	private DefaultListModel<String> modelClients;
	private DefaultListModel<String> modelJobs;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public FJob_ServerGUI(Servidor sv,Controller control) {
		this.setSv(sv);
		this.setCt(control);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{91, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		modelClients = new DefaultListModel<String>();
		
		modelJobs = new DefaultListModel<String>();
		JList<String> lClients = new JList<String>(modelClients);
		GridBagConstraints gbc_lClients = new GridBagConstraints();
		gbc_lClients.insets = new Insets(0, 0, 5, 5);
		gbc_lClients.fill = GridBagConstraints.BOTH;
		gbc_lClients.gridx = 3;
		gbc_lClients.gridy = 1;
		
		contentPane.add(lClients, gbc_lClients);
		
		JButton btnListclients = new JButton("ListClients");
		btnListclients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					modelClients.clear();
					String[] list = ct.getClientList();
					if (list!=null){
					    for(String file: list)						
					    modelClients.addElement(file);				
					    lClients.updateUI();}
				     else{
				    	 JOptionPane.showMessageDialog(null,"Falha ao obter a lista de arquivos!","Mensagem de erro:",JOptionPane.ERROR_MESSAGE);
				     }
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}				
				
		});
		GridBagConstraints gbc_btnListclients = new GridBagConstraints();
		gbc_btnListclients.insets = new Insets(0, 0, 5, 5);
		gbc_btnListclients.gridx = 0;
		gbc_btnListclients.gridy = 1;
		contentPane.add(btnListclients, gbc_btnListclients);
		
		
		JList<String> lJobs = new JList<String>(modelJobs);
		GridBagConstraints gbc_lJobs = new GridBagConstraints();
		gbc_lJobs.insets = new Insets(0, 0, 5, 5);
		gbc_lJobs.fill = GridBagConstraints.BOTH;
		gbc_lJobs.gridx = 3;
		gbc_lJobs.gridy = 3;
		contentPane.add(lJobs, gbc_lJobs);
		
		
		JButton btnListjobs = new JButton("List Jobs");
		btnListjobs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					modelJobs.clear();
					String[] list = sv.getJobList();
					if (list!=null){
					    for(String file: list)						
					    modelJobs.addElement(file);				
					    lJobs.updateUI();}
				     else{
				    	 JOptionPane.showMessageDialog(null,"Falha ao obter a lista de arquivos!","Mensagem de erro:",JOptionPane.ERROR_MESSAGE);
				     }
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnListjobs = new GridBagConstraints();
		gbc_btnListjobs.insets = new Insets(0, 0, 5, 5);
		gbc_btnListjobs.gridx = 0;
		gbc_btnListjobs.gridy = 3;
		contentPane.add(btnListjobs, gbc_btnListjobs);
		
		
		JButton btnRemoveJob = new JButton("Remove Job");
		btnRemoveJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
				String selected = lJobs.getSelectedValue();
			      if(sv.RemoveJob(selected)){
			    	  JOptionPane.showMessageDialog(null,"Job "+selected+" removido com sucesso!");
			      }
				}catch (Exception e1){				
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnSendJob = new JButton("Send Job");
		btnSendJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ct.sendJob(sv.getLog(lJobs.getSelectedValue().toString()));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		JButton btnReadjob = new JButton("Read Job");
		btnReadjob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Job j =	sv.getLog(lJobs.getSelectedValue().toString());
				JOptionPane.showMessageDialog(null,j.toString());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton btnNewJob = new JButton("New job");
		btnNewJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_NewJob dialog = new GUI_NewJob(sv);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
			
			GridBagConstraints gbc_btnNewJob = new GridBagConstraints();
			gbc_btnNewJob.insets = new Insets(0, 0, 5, 5);
			gbc_btnNewJob.gridx = 0;
			gbc_btnNewJob.gridy = 5;
			contentPane.add(btnNewJob, gbc_btnNewJob);
		GridBagConstraints gbc_btnReadjob = new GridBagConstraints();
		gbc_btnReadjob.insets = new Insets(0, 0, 5, 5);
		gbc_btnReadjob.gridx = 0;
		gbc_btnReadjob.gridy = 6;
		contentPane.add(btnReadjob, gbc_btnReadjob);
		GridBagConstraints gbc_btnSendJob = new GridBagConstraints();
		gbc_btnSendJob.insets = new Insets(0, 0, 5, 5);
		gbc_btnSendJob.gridx = 0;
		gbc_btnSendJob.gridy = 7;
		contentPane.add(btnSendJob, gbc_btnSendJob);
		GridBagConstraints gbc_btnRemoveJob = new GridBagConstraints();
		gbc_btnRemoveJob.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveJob.gridx = 0;
		gbc_btnRemoveJob.gridy = 9;
		contentPane.add(btnRemoveJob, gbc_btnRemoveJob);
		this.setVisible(true);
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}

	public Servidor getSv() {
		return sv;
	}

	public void setSv(Servidor sv) {
		this.sv = sv;
	}


	public Controller getCt() {
		return ct;
	}


	public void setCt(Controller ct) {
		this.ct = ct;
	}

}
