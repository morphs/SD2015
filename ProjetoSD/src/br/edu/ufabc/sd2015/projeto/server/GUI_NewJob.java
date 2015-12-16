package br.edu.ufabc.sd2015.projeto.server;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import br.edu.ufabc.sd2015.projeto.comuns.Job;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class GUI_NewJob extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtComandos;
	private JFormattedTextField txtPrioridade;
	private JFormattedTextField txtGrupo;
	private JFormattedTextField txtPrioridadeGrupo;
	private JFormattedTextField txtTempoLimite;
	private JFormattedTextField ftxtArquivo;
	//private File selectedFile;

	//Vars
	private  File executable;
	private   boolean hasFile;
	private   String cmd;	
	private   int time;
	private   int group;
	private   int groupOrder;
	private   int priority;
	private   String output;
	private   File outputFile;
	private Servidor sv1;
		//End Vars
	
	
	
	
	public GUI_NewJob(Servidor sv) {
		sv1 = sv;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblComandos = new JLabel("Comandos");
			lblComandos.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblComandos, "2, 2, right, default");
		}
		{
			txtComandos = new JTextField();
			contentPanel.add(txtComandos, "4, 2, fill, default");
			txtComandos.setColumns(10);
		}
		{
			JLabel lblPrioridade = new JLabel("Prioridade");
			contentPanel.add(lblPrioridade, "2, 4, right, default");
		}
		{
			txtPrioridade = new JFormattedTextField(createFormatter("###"));
			txtPrioridade.setText("000");
			contentPanel.add(txtPrioridade, "4, 4, fill, default");
			txtPrioridade.setColumns(10);
		}
		{
			JLabel lblGrupo = new JLabel("Grupo");
			contentPanel.add(lblGrupo, "2, 6, right, default");
		}
		{
			txtGrupo = new JFormattedTextField(createFormatter("###"));
			txtGrupo.setText("000");
			contentPanel.add(txtGrupo, "4, 6, fill, default");
			txtGrupo.setColumns(10);
		}
		{
			JLabel lblPG = new JLabel("PrioridadeGrupo");
			contentPanel.add(lblPG, "2, 8, right, default");
		}
		{
			txtPrioridadeGrupo = new JFormattedTextField(createFormatter("###"));
			txtPrioridadeGrupo.setText("000");
			contentPanel.add(txtPrioridadeGrupo, "4, 8, fill, default");
			txtPrioridadeGrupo.setColumns(10);
		}
		{
			JLabel lblTempoLimite = new JLabel("Tempo Limite");
			contentPanel.add(lblTempoLimite, "2, 10, right, default");
		}
		{
			txtTempoLimite = new JFormattedTextField(createFormatter("######"));
			txtTempoLimite.setText("000");
			contentPanel.add(txtTempoLimite, "4, 10, fill, default");
			txtTempoLimite.setColumns(10);
		}
		{
			JLabel lblArquivo = new JLabel("Arquivo");
			contentPanel.add(lblArquivo, "2, 12, right, default");
		}
		{
			ftxtArquivo = new JFormattedTextField();
			ftxtArquivo.setEditable(false);
			contentPanel.add(ftxtArquivo, "4, 12, fill, default");
		}
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						createNewJob();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//file
		{
			JButton btnSelecionar = new JButton("Selecionar");
			btnSelecionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = fileChooser.showOpenDialog(getParent());
					if (result == JFileChooser.APPROVE_OPTION) {						
						executable = fileChooser.getSelectedFile();
						System.out.println(executable.getAbsolutePath());
						ftxtArquivo.setText(executable.getName());
						hasFile = true;
						System.out.println(hasFile);
					}
				}
			});
			contentPanel.add(btnSelecionar, "4, 14");
		}
		//file
	}
	
	public void createNewJob(){
		Job job;
		cmd = txtComandos.getText().toString();
		priority = Integer.parseInt(txtPrioridade.getText().trim().toString());
		time = Integer.parseInt(txtTempoLimite.getText().trim().toString());
		group = Integer.parseInt(txtGrupo.getText().trim().toString());
		groupOrder = Integer.parseInt(txtPrioridadeGrupo.getText().trim().toString());
		
		if(hasFile)
			job = new Job(executable, cmd, priority, time, group, groupOrder);
		else
		job = new Job(cmd, priority, time, group, groupOrder);
		
		System.out.println(job);
		try {
			sv1.addJob(job);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}

	public Servidor getSv1() {
		return sv1;
	}

	public void setSv1(Servidor sv1) {
		this.sv1 = sv1;
	}

	
	
}
