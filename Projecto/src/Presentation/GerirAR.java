/*
 * Created by JFormDesigner on Tue Dec 22 18:35:58 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.table.*;

import Business.*;
import Exception.ExceptionEleicaoEstado;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;
import Exception.ExceptionMandanteInvalido;

import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author Octavio Maia
 */
public class GerirAR extends JFrame {
	
	private SGE sge;
	private Eleicao eleicao;
	private GregorianCalendar dataNasc;
	
	public GerirAR(SGE s, Eleicao e) {
		eleicao=e;
		sge=s;
		initComponents();
		this.setVisible(true);
	}

	private void button1ActionPerformed(ActionEvent e) {
		fileChooser1.setVisible(true);
		int result = fileChooser1.showOpenDialog(fileChooser1);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser1.getSelectedFile();
		    pathImagem.setText(selectedFile.getAbsolutePath());
		    fileChooser1.setVisible(false);
		}else
			fileChooser1.setVisible(false);
	}

	private void buttonDataNascimentoActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(true);
	}

	private void buttonProcurarActionPerformed(ActionEvent e) {
		fileChooser1.setVisible(true);
		int result = fileChooser1.showOpenDialog(fileChooser1);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser1.getSelectedFile();
		    pathImagem2.setText(selectedFile.getAbsolutePath());
		    fileChooser1.setVisible(false);
		}else
			fileChooser1.setVisible(false);
	}

	private void buttonAdicionarFotoActionPerformed(ActionEvent e) {
		String nome = nomeCandidato.getText();
		String naturalidade = this.naturalidade.getText();
		String residencia = this.residencia.getText();
		String profissao = this.profissao.getText();
		int bi = Integer.parseInt(this.bi.getText());
		String foto = this.pathImagem.getText();
		GregorianCalendar dataN = this.dataNasc;
		
		try{
			Candidato c = new Candidato(nome, bi, profissao, dataN, residencia, naturalidade, foto);
			sge.addCandidatoAR(eleicao,c);
			
			//reset tabelas
			nomeCandidato.setText("");
			this.naturalidade.setText("");
			this.residencia.setText("");
			this.profissao.setText("");
			this.bi.setText("");
			this.dataNascimento.setText("dd/mm/aa");
			this.pathImagem.setText("");
		}catch(ExceptionListaExiste | ExceptionLimiteCandidatos|  ExceptionMandanteInvalido | ExceptionEleicaoEstado ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void buttonApagarInfoActionPerformed(ActionEvent e) {
		nomeCandidato.setText("");
		this.naturalidade.setText("");
		this.residencia.setText("");
		this.profissao.setText("");
		this.bi.setText("");
		this.dataNascimento.setText("dd/mm/aa");
		this.pathImagem.setText("");
	}

	private void buttonConfirmarDataActionPerformed(ActionEvent e) {
		Calendar cal = calendar1.getCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		int ano = cal.get(Calendar.YEAR);
		dataNascimento.setText(dia+"/"+(mes+1)+"/"+ano);
		
		dataNasc = new GregorianCalendar(ano, mes, dia);
		sge.alterarDataEleicao(eleicao,dataNasc);
		
		dialogoCalendario.setVisible(false);
	}

	private void buttonCancelarDataActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(false);
	}

	private void comboBox1ItemStateChanged(ItemEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		comboBox1 = new JComboBox<>();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		separator1 = compFactory.createSeparator("Listas existentes", SwingConstants.CENTER);
		scrollPane2 = new JScrollPane();
		list1 = new JList();
		label4 = new JLabel();
		button1 = new JButton();
		nomeLista = new JTextField();
		sigla = new JTextField();
		pathImagem = new JTextField();
		button2 = new JButton();
		button3 = new JButton();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		bi = new JTextField();
		profissao = new JTextField();
		residencia = new JTextField();
		naturalidade = new JTextField();
		nomeCandidato = new JTextField();
		label10 = new JLabel();
		dataNascimento = new JLabel();
		buttonDataNascimento = new JButton();
		label11 = new JLabel();
		buttonProcurar = new JButton();
		pathImagem2 = new JTextField();
		buttonAdicionarCandidato = new JButton();
		buttonApagarInfo = new JButton();
		scrollPane3 = new JScrollPane();
		table2 = new JTable();
		separator4 = new JSeparator();
		separator5 = new JSeparator();
		separator2 = compFactory.createSeparator("Candidatos", SwingConstants.CENTER);
		buttonOK = new JButton();
		button4 = new JButton();
		buttonEliminarCandidato = new JButton();
		separator6 = new JSeparator();
		fileChooser1 = new JFileChooser();
		dialogoCalendario = new JDialog();
		calendar1 = new JCalendar();
		buttonConfirmarDataInicio = new JButton();
		buttonCancelarData = new JButton();

		//======== this ========
		setTitle("Gerir Elei\u00e7\u00e3o Assembleia da Rep\u00fablica");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Circulo pertencente");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(20, 20, label1.getPreferredSize().width, 25);

		//---- label2 ----
		label2.setText("Nome da lista");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(15, 280, 124, 25);

		//---- label3 ----
		label3.setText("Sigla");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(15, 315, 124, 25);

		//---- comboBox1 ----
		comboBox1.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
			"1",
			"2",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"9",
			"10",
			"11",
			"12",
			"13",
			"14",
			"15",
			"16",
			"17",
			"18",
			"19",
			"20",
			"21",
			"22"
		}));
		comboBox1.addItemListener(e -> comboBox1ItemStateChanged(e));
		contentPane.add(comboBox1);
		comboBox1.setBounds(170, 20, 130, 25);

		//======== scrollPane1 ========
		{

			//---- table1 ----
			table1.setFont(new Font("Arial", Font.PLAIN, 14));
			table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"S\u00edmbolo", "Sigla", "Nome", null
				}
			));
			table1.getColumnModel().getColumn(3).setPreferredWidth(0);
			table1.getColumnModel().getColumn(3).setMinWidth(0);
			table1.getColumnModel().getColumn(3).setWidth(0);
			table1.getColumnModel().getColumn(3).setMaxWidth(0);
			//povoarTabela();
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(20, 80, 930, 145);

		//---- separator1 ----
		separator1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(separator1);
		separator1.setBounds(20, 55, 930, 25);

		//======== scrollPane2 ========
		{

			//---- list1 ----
			list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane2.setViewportView(list1);
		}
		contentPane.add(scrollPane2);
		scrollPane2.setBounds(745, 280, 210, 105);

		//---- label4 ----
		label4.setText("S\u00edmbolo");
		label4.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label4);
		label4.setBounds(15, 355, 124, 25);

		//---- button1 ----
		button1.setText("Procurar");
		button1.setFont(new Font("Arial", Font.PLAIN, 14));
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(595, 355, 130, 25);

		//---- nomeLista ----
		nomeLista.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeLista);
		nomeLista.setBounds(155, 280, 565, 25);

		//---- sigla ----
		sigla.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(sigla);
		sigla.setBounds(155, 315, 565, 25);

		//---- pathImagem ----
		pathImagem.setFont(new Font("Arial", Font.PLAIN, 14));
		pathImagem.setEnabled(false);
		contentPane.add(pathImagem);
		pathImagem.setBounds(155, 355, 430, 25);

		//---- button2 ----
		button2.setText("Eliminar lista");
		button2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(button2);
		button2.setBounds(745, 235, 205, 25);

		//---- button3 ----
		button3.setText("Adicionar lista");
		button3.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(button3);
		button3.setBounds(745, 395, 210, 25);

		//---- label5 ----
		label5.setText("Nome do candidato:");
		label5.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label5);
		label5.setBounds(10, 445, 139, 22);

		//---- label6 ----
		label6.setText("Naturalidade:");
		label6.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label6);
		label6.setBounds(10, 475, 139, 22);

		//---- label7 ----
		label7.setText("Resid\u00eancia:");
		label7.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label7);
		label7.setBounds(515, 445, 85, 22);

		//---- label8 ----
		label8.setText("Profiss\u00e3o:");
		label8.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label8);
		label8.setBounds(515, 475, 80, 22);

		//---- label9 ----
		label9.setText("B.I. / C.C.");
		label9.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label9);
		label9.setBounds(515, 505, 70, 22);

		//---- bi ----
		bi.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(bi);
		bi.setBounds(595, 505, 355, bi.getPreferredSize().height);

		//---- profissao ----
		profissao.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(profissao);
		profissao.setBounds(595, 475, 355, profissao.getPreferredSize().height);

		//---- residencia ----
		residencia.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(residencia);
		residencia.setBounds(595, 445, 355, residencia.getPreferredSize().height);

		//---- naturalidade ----
		naturalidade.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(naturalidade);
		naturalidade.setBounds(155, 475, 335, naturalidade.getPreferredSize().height);

		//---- nomeCandidato ----
		nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeCandidato);
		nomeCandidato.setBounds(155, 445, 335, nomeCandidato.getPreferredSize().height);

		//---- label10 ----
		label10.setText("Data de nascimento:");
		label10.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label10);
		label10.setBounds(10, 510, 139, 25);

		//---- dataNascimento ----
		dataNascimento.setText("dd/mm/aa");
		dataNascimento.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(dataNascimento);
		dataNascimento.setBounds(155, 510, 115, 25);

		//---- buttonDataNascimento ----
		buttonDataNascimento.setText("Alterar");
		buttonDataNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonDataNascimento.addActionListener(e -> buttonDataNascimentoActionPerformed(e));
		contentPane.add(buttonDataNascimento);
		buttonDataNascimento.setBounds(270, 510, 80, 25);

		//---- label11 ----
		label11.setText("Foto");
		label11.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label11);
		label11.setBounds(10, 545, 35, 22);

		//---- buttonProcurar ----
		buttonProcurar.setText("Procurar");
		buttonProcurar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonProcurar.addActionListener(e -> buttonProcurarActionPerformed(e));
		contentPane.add(buttonProcurar);
		buttonProcurar.setBounds(50, 545, 95, buttonProcurar.getPreferredSize().height);

		//---- pathImagem2 ----
		pathImagem2.setEditable(false);
		contentPane.add(pathImagem2);
		pathImagem2.setBounds(155, 545, 335, 25);

		//---- buttonAdicionarCandidato ----
		buttonAdicionarCandidato.setText("Adicionar");
		buttonAdicionarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAdicionarCandidato.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
		contentPane.add(buttonAdicionarCandidato);
		buttonAdicionarCandidato.setBounds(680, 535, 95, buttonAdicionarCandidato.getPreferredSize().height);

		//---- buttonApagarInfo ----
		buttonApagarInfo.setText("Apagar informa\u00e7\u00f5es");
		buttonApagarInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonApagarInfo.addActionListener(e -> buttonApagarInfoActionPerformed(e));
		contentPane.add(buttonApagarInfo);
		buttonApagarInfo.setBounds(785, 535, 165, buttonApagarInfo.getPreferredSize().height);

		//======== scrollPane3 ========
		{

			//---- table2 ----
			table2.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
				},
				new String[] {
					"Nome", "B.I. / C.C.", "Partido", "Tipo Candidato", null
				}
			));
			scrollPane3.setViewportView(table2);
		}
		contentPane.add(scrollPane3);
		scrollPane3.setBounds(10, 595, 935, 240);
		contentPane.add(separator4);
		separator4.setBounds(0, 1015, 940, 5);
		contentPane.add(separator5);
		separator5.setBounds(15, 585, 930, 7);
		contentPane.add(separator2);
		separator2.setBounds(15, 420, 930, separator2.getPreferredSize().height);

		//---- buttonOK ----
		buttonOK.setText("OK");
		buttonOK.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(buttonOK);
		buttonOK.setBounds(895, 885, buttonOK.getPreferredSize().width, 25);

		//---- button4 ----
		button4.setText("Cancelar");
		button4.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(button4);
		button4.setBounds(795, 885, button4.getPreferredSize().width, 25);

		//---- buttonEliminarCandidato ----
		buttonEliminarCandidato.setText("Eliminar candidato");
		buttonEliminarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(buttonEliminarCandidato);
		buttonEliminarCandidato.setBounds(795, 840, buttonEliminarCandidato.getPreferredSize().width, 25);
		contentPane.add(separator6);
		separator6.setBounds(15, 870, 925, 5);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		setSize(980, 955);
		setLocationRelativeTo(null);

		//======== dialogoCalendario ========
		{
			dialogoCalendario.setTitle("Calendario");
			dialogoCalendario.setResizable(false);
			dialogoCalendario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container dialogoCalendarioContentPane = dialogoCalendario.getContentPane();
			dialogoCalendarioContentPane.setLayout(null);
			dialogoCalendarioContentPane.add(calendar1);
			calendar1.setBounds(0, 0, 210, 155);

			//---- buttonConfirmarDataInicio ----
			buttonConfirmarDataInicio.setText("Confirmar");
			buttonConfirmarDataInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonConfirmarDataInicio.addActionListener(e -> buttonConfirmarDataActionPerformed(e));
			dialogoCalendarioContentPane.add(buttonConfirmarDataInicio);
			buttonConfirmarDataInicio.setBounds(5, 155, 90, 28);

			//---- buttonCancelarData ----
			buttonCancelarData.setText("Cancelar");
			buttonCancelarData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonCancelarData.addActionListener(e -> buttonCancelarDataActionPerformed(e));
			dialogoCalendarioContentPane.add(buttonCancelarData);
			buttonCancelarData.setBounds(110, 155, 90, 28);

			dialogoCalendarioContentPane.setPreferredSize(new Dimension(225, 235));
			dialogoCalendario.setSize(225, 235);
			dialogoCalendario.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JComboBox<String> comboBox1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JComponent separator1;
	private JScrollPane scrollPane2;
	private JList list1;
	private JLabel label4;
	private JButton button1;
	private JTextField nomeLista;
	private JTextField sigla;
	private JTextField pathImagem;
	private JButton button2;
	private JButton button3;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JTextField bi;
	private JTextField profissao;
	private JTextField residencia;
	private JTextField naturalidade;
	private JTextField nomeCandidato;
	private JLabel label10;
	private JLabel dataNascimento;
	private JButton buttonDataNascimento;
	private JLabel label11;
	private JButton buttonProcurar;
	private JTextField pathImagem2;
	private JButton buttonAdicionarCandidato;
	private JButton buttonApagarInfo;
	private JScrollPane scrollPane3;
	private JTable table2;
	private JSeparator separator4;
	private JSeparator separator5;
	private JComponent separator2;
	private JButton buttonOK;
	private JButton button4;
	private JButton buttonEliminarCandidato;
	private JSeparator separator6;
	private JFileChooser fileChooser1;
	private JDialog dialogoCalendario;
	private JCalendar calendar1;
	private JButton buttonConfirmarDataInicio;
	private JButton buttonCancelarData;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
