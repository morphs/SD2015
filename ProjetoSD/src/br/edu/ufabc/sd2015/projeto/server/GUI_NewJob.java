package br.edu.ufabc.sd2015.projeto.server;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_NewJob extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtComandos;
	private JTextField txtPrioridade;
	private JTextField txtGrupo;
	private JTextField txtPrioridadeGrupo;
	private JTextField txtTempoLimite;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GUI_NewJob dialog = new GUI_NewJob();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GUI_NewJob() {
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
			txtPrioridade = new JTextField();
			contentPanel.add(txtPrioridade, "4, 4, fill, default");
			txtPrioridade.setColumns(10);
		}
		{
			JLabel lblGrupo = new JLabel("Grupo");
			contentPanel.add(lblGrupo, "2, 6, right, default");
		}
		{
			txtGrupo = new JTextField();
			contentPanel.add(txtGrupo, "4, 6, fill, default");
			txtGrupo.setColumns(10);
		}
		{
			JLabel lblPG = new JLabel("PrioridadeGrupo");
			contentPanel.add(lblPG, "2, 8, right, default");
		}
		{
			txtPrioridadeGrupo = new JTextField();
			contentPanel.add(txtPrioridadeGrupo, "4, 8, fill, default");
			txtPrioridadeGrupo.setColumns(10);
		}
		{
			JLabel lblTempoLimite = new JLabel("Tempo Limite");
			contentPanel.add(lblTempoLimite, "2, 10, right, default");
		}
		{
			txtTempoLimite = new JTextField();
			contentPanel.add(txtTempoLimite, "4, 10, fill, default");
			txtTempoLimite.setColumns(10);
		}
		{
			JLabel lblArquivo = new JLabel("Arquivo");
			contentPanel.add(lblArquivo, "2, 12, right, default");
		}
		{
			JFormattedTextField ftxtArquivo = new JFormattedTextField();
			ftxtArquivo.setEditable(false);
			contentPanel.add(ftxtArquivo, "4, 12, fill, default");
		}
		{
			JButton btnSelecionar = new JButton("Selecionar");
			btnSelecionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			contentPanel.add(btnSelecionar, "4, 14");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
	}

}
