package br.edu.ufabc.sd2015.projeto.server;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.Job;

public class FJob_ServerGUI extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Servidor sv;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public FJob_ServerGUI(Servidor sv) {
		this.setSv(sv);
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
		
		JButton btnNewJob = new JButton("New job");
		btnNewJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_NewJob dialog = new GUI_NewJob(sv);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		JButton btnListclients = new JButton("ListClients");
		GridBagConstraints gbc_btnListclients = new GridBagConstraints();
		gbc_btnListclients.insets = new Insets(0, 0, 5, 5);
		gbc_btnListclients.gridx = 0;
		gbc_btnListclients.gridy = 1;
		contentPane.add(btnListclients, gbc_btnListclients);
		
		JList<CJob> lClients = new JList<CJob>();
		GridBagConstraints gbc_lClients = new GridBagConstraints();
		gbc_lClients.insets = new Insets(0, 0, 5, 5);
		gbc_lClients.fill = GridBagConstraints.BOTH;
		gbc_lClients.gridx = 3;
		gbc_lClients.gridy = 1;
		contentPane.add(lClients, gbc_lClients);
		
		JButton btnListjobs = new JButton("List Jobs");
		btnListjobs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnListjobs = new GridBagConstraints();
		gbc_btnListjobs.insets = new Insets(0, 0, 5, 5);
		gbc_btnListjobs.gridx = 0;
		gbc_btnListjobs.gridy = 3;
		contentPane.add(btnListjobs, gbc_btnListjobs);
		
		JList<Job> lJobs = new JList<Job>();
		GridBagConstraints gbc_lJobs = new GridBagConstraints();
		gbc_lJobs.insets = new Insets(0, 0, 5, 5);
		gbc_lJobs.fill = GridBagConstraints.BOTH;
		gbc_lJobs.gridx = 3;
		gbc_lJobs.gridy = 3;
		contentPane.add(lJobs, gbc_lJobs);
		
		JButton btnReadjob = new JButton("Read Job");
		GridBagConstraints gbc_btnReadjob = new GridBagConstraints();
		gbc_btnReadjob.insets = new Insets(0, 0, 5, 5);
		gbc_btnReadjob.gridx = 0;
		gbc_btnReadjob.gridy = 5;
		contentPane.add(btnReadjob, gbc_btnReadjob);
		
		JButton btnRemoveJob = new JButton("Remove Job");
		GridBagConstraints gbc_btnRemoveJob = new GridBagConstraints();
		gbc_btnRemoveJob.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveJob.gridx = 0;
		gbc_btnRemoveJob.gridy = 6;
		contentPane.add(btnRemoveJob, gbc_btnRemoveJob);
		GridBagConstraints gbc_btnNewJob = new GridBagConstraints();
		gbc_btnNewJob.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewJob.gridx = 0;
		gbc_btnNewJob.gridy = 9;
		contentPane.add(btnNewJob, gbc_btnNewJob);
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

}
