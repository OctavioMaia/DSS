/*
 * Created by JFormDesigner on Tue Dec 22 18:35:58 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

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
	private EleicaoAR eleicao;
	private GregorianCalendar dataNasc;
	private ArrayList<Partido> listaPartidos;
	
	public GerirAR(SGE s, Eleicao e) {
		eleicao=(EleicaoAR) e;
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


	private void buttonAdicionarFotoActionPerformed(ActionEvent e) {
		String nome = nomeCandidato.getText();
		String naturalidade = this.naturalidade.getText();
		String residencia = this.residencia.getText();
		String profissao = this.profissao.getText();
		int bi = Integer.parseInt(this.bi.getText());
		Calendar dataN = this.dataNasc;
		
		try{
			CandidatoAR c = new CandidatoAR(nome, bi, profissao, dataN, residencia, naturalidade, listaPartidos.get(comboBox3.getSelectedIndex()), ((String) comboBox2.getSelectedItem()).charAt(0));
			sge.addCandidatoAR(eleicao,(Lista) table1.getValueAt(table1.getSelectedRow(), 3), c);
			povoarTabelaCandidato();
			
			//reset tabelas
			nomeCandidato.setText("");
			this.naturalidade.setText("");
			this.residencia.setText("");
			this.profissao.setText("");
			this.bi.setText("");
			this.dataNascimento.setText("dd/mm/aa");
			this.pathImagem.setText("");
		}catch(ExceptionLimiteCandidatos|  ExceptionMandanteInvalido ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void buttonApagarInfoActionPerformed(ActionEvent e) {
		this.nomeCandidato.setText("");
		this.naturalidade.setText("");
		this.residencia.setText("");
		this.profissao.setText("");
		this.bi.setText("");
		this.dataNascimento.setText("dd/mm/aa");
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
		povoarTabelaListas();
	}

	private void povoarTabelaListas() {
		Set<Lista> set = eleicao.getListasCirculo(comboBox1.getSelectedIndex() + 1);
		Object[][] data = new Object[set.size()][];
		int i = 0;

		if (table1.getRowCount() > 0) {
			for (int conta = table1.getRowCount() - 1; conta > -1; conta--) {
				((DefaultTableModel) table1.getModel()).removeRow(conta);
				;
			}
		}

		for (Lista el : set) {
			data[i] = el.toTable();

			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.addRow(data[i]);

			i++;
		}

	}
	
	private void povoarTabelaCandidato() {
		Lista l = (Lista) table1.getValueAt(table1.getSelectedRow(), 3);
		ArrayList<CandidatoAR> set = l.getCandidatos();
		Object[][] data = new Object[set.size()][];
		int i = 0;

		if (table2.getRowCount() > 0) {
			for (int conta = table2.getRowCount() - 1; conta > -1; conta--) {
				((DefaultTableModel) table2.getModel()).removeRow(conta);
				;
			}
		}

		for (CandidatoAR el : set) {
			data[i] = el.toTable();

			DefaultTableModel model = (DefaultTableModel) table2.getModel();
			model.addRow(data[i]);

			i++;
		}
	}
	
	private void button2ActionPerformed(ActionEvent e) {
		sge.removeLista(eleicao, (Listavel) table1.getValueAt(table1.getSelectedRow(), 3));
		((DefaultTableModel) table1.getModel()).removeRow(table1.getSelectedRow());
	}

	private void buttonAtualizarCandidatosActionPerformed(ActionEvent e) {
		povoarTabelaCandidato();
	}

	private void button5ActionPerformed(ActionEvent e) {
		povoarTabelaListas();
	}

	private void button3ActionPerformed(ActionEvent e) {
		Lista lista = new Lista(0, sge.getCirculo(comboBox1.getSelectedIndex()+1), sigla.getText(), nomeLista.getText(), pathImagem.getText(), (Votavel) table3.getValueAt(table3.getSelectedRow(), 2));
		try {
			sge.addLista(eleicao, lista);
			povoarTabelaListas();
		} catch (ExceptionListaExiste e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionLimiteCandidatos e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionMandanteInvalido e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionEleicaoEstado e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
	
	private void povoarVotavel() {
		if(sge.getVotaveis()!=null){
			Set<Votavel> set = sge.getVotaveis();
			Object[][] data = new Object[set.size()][]; 
			int i=0;
			
			if (table3.getRowCount() > 0) {
	            for (int conta = table3.getRowCount() - 1; conta > -1; conta--) {
	                ((DefaultTableModel) table3.getModel()).removeRow(conta);;
	            }
	        }
			
			for(Votavel el : set){	
				data[i] = el.toTable();
				
				DefaultTableModel model = (DefaultTableModel) table3.getModel();
				model.addRow(data[i]);
	
				i++;
			}
		}
	}

	private void buttonEliminarCandidatoActionPerformed(ActionEvent e) {
		sge.removeCandidatoAR(eleicao, (Lista) table1.getValueAt(table1.getSelectedRow(), 4), (CandidatoAR) table2.getValueAt(table2.getSelectedRow(), 4));
		((DefaultTableModel) table2.getModel()).removeRow(table2.getSelectedRow());
	}

	private void button4ActionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void scrollPane2MouseClicked(MouseEvent e) {
		listaPartidos = sge.partCandidato((Votavel) table3.getValueAt(table3.getSelectedRow(), 2));
		
		for(Partido p : listaPartidos){
			comboBox3.addItem(p.getSigla());
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		comboBox1 = new JComboBox<>();
		separator1 = compFactory.createSeparator("Listas existentes", SwingConstants.CENTER);
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
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		buttonAtualizarCandidatos = new JButton();
		button5 = new JButton();
		scrollPane2 = new JScrollPane();
		table3 = new JTable();
		comboBox2 = new JComboBox<>();
		comboBox3 = new JComboBox();
		label12 = new JLabel();
		label13 = new JLabel();
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
		label1.setBounds(15, 20, label1.getPreferredSize().width, 25);

		//---- label2 ----
		label2.setText("Nome da lista");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(15, 75, 95, 25);

		//---- label3 ----
		label3.setText("Sigla");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(15, 110, 90, 25);

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
		comboBox1.setBounds(155, 20, 130, 25);

		//---- separator1 ----
		separator1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(separator1);
		separator1.setBounds(20, 55, 930, 15);

		//---- label4 ----
		label4.setText("S\u00edmbolo");
		label4.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label4);
		label4.setBounds(15, 150, 90, 25);

		//---- button1 ----
		button1.setText("Procurar");
		button1.setFont(new Font("Arial", Font.PLAIN, 14));
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(480, 150, 130, 25);

		//---- nomeLista ----
		nomeLista.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeLista);
		nomeLista.setBounds(110, 75, 500, 25);

		//---- sigla ----
		sigla.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(sigla);
		sigla.setBounds(110, 110, 500, 25);

		//---- pathImagem ----
		pathImagem.setFont(new Font("Arial", Font.PLAIN, 14));
		pathImagem.setEditable(false);
		pathImagem.setEnabled(false);
		contentPane.add(pathImagem);
		pathImagem.setBounds(110, 150, 365, 25);

		//---- button2 ----
		button2.setText("Eliminar lista");
		button2.setFont(new Font("Arial", Font.PLAIN, 14));
		button2.addActionListener(e -> button2ActionPerformed(e));
		contentPane.add(button2);
		button2.setBounds(525, 355, 205, 25);

		//---- button3 ----
		button3.setText("Adicionar lista");
		button3.setFont(new Font("Arial", Font.PLAIN, 16));
		button3.addActionListener(e -> button3ActionPerformed(e));
		contentPane.add(button3);
		button3.setBounds(735, 355, 210, 25);

		//---- label5 ----
		label5.setText("Nome do candidato:");
		label5.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label5);
		label5.setBounds(10, 415, 139, 22);

		//---- label6 ----
		label6.setText("Naturalidade:");
		label6.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label6);
		label6.setBounds(10, 445, 139, 22);

		//---- label7 ----
		label7.setText("Resid\u00eancia:");
		label7.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label7);
		label7.setBounds(515, 415, 85, 22);

		//---- label8 ----
		label8.setText("Profiss\u00e3o:");
		label8.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label8);
		label8.setBounds(515, 445, 80, 22);

		//---- label9 ----
		label9.setText("B.I. / C.C.");
		label9.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label9);
		label9.setBounds(515, 475, 70, 22);

		//---- bi ----
		bi.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(bi);
		bi.setBounds(595, 475, 355, bi.getPreferredSize().height);

		//---- profissao ----
		profissao.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(profissao);
		profissao.setBounds(595, 445, 355, profissao.getPreferredSize().height);

		//---- residencia ----
		residencia.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(residencia);
		residencia.setBounds(595, 415, 355, residencia.getPreferredSize().height);

		//---- naturalidade ----
		naturalidade.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(naturalidade);
		naturalidade.setBounds(155, 445, 335, naturalidade.getPreferredSize().height);

		//---- nomeCandidato ----
		nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeCandidato);
		nomeCandidato.setBounds(155, 415, 335, nomeCandidato.getPreferredSize().height);

		//---- label10 ----
		label10.setText("Data de nascimento:");
		label10.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label10);
		label10.setBounds(10, 480, 139, 25);

		//---- dataNascimento ----
		dataNascimento.setText("dd/mm/aa");
		dataNascimento.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(dataNascimento);
		dataNascimento.setBounds(155, 480, 115, 25);

		//---- buttonDataNascimento ----
		buttonDataNascimento.setText("Alterar");
		buttonDataNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonDataNascimento.addActionListener(e -> buttonDataNascimentoActionPerformed(e));
		contentPane.add(buttonDataNascimento);
		buttonDataNascimento.setBounds(410, 480, 80, 25);

		//---- buttonAdicionarCandidato ----
		buttonAdicionarCandidato.setText("Adicionar");
		buttonAdicionarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAdicionarCandidato.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
		contentPane.add(buttonAdicionarCandidato);
		buttonAdicionarCandidato.setBounds(670, 520, 95, 25);

		//---- buttonApagarInfo ----
		buttonApagarInfo.setText("Apagar informa\u00e7\u00f5es");
		buttonApagarInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonApagarInfo.addActionListener(e -> buttonApagarInfoActionPerformed(e));
		contentPane.add(buttonApagarInfo);
		buttonApagarInfo.setBounds(780, 520, 165, 25);

		//======== scrollPane3 ========
		{

			//---- table2 ----
			table2.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "B.I. / C.C.", "Partido", "Tipo Candidato", null
				}
			));
			table2.getColumnModel().getColumn(4).setPreferredWidth(0);
			table2.getColumnModel().getColumn(4).setMinWidth(0);
			table2.getColumnModel().getColumn(4).setWidth(0);
			table2.getColumnModel().getColumn(4).setMaxWidth(0);
			povoarTabelaCandidato();
			scrollPane3.setViewportView(table2);
		}
		contentPane.add(scrollPane3);
		scrollPane3.setBounds(10, 565, 935, 240);
		contentPane.add(separator4);
		separator4.setBounds(0, 1015, 940, 5);
		contentPane.add(separator5);
		separator5.setBounds(15, 585, 930, 7);
		contentPane.add(separator2);
		separator2.setBounds(15, 390, 930, separator2.getPreferredSize().height);

		//---- buttonOK ----
		buttonOK.setText("OK");
		buttonOK.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonOK.addActionListener(e -> button4ActionPerformed(e));
		contentPane.add(buttonOK);
		buttonOK.setBounds(895, 850, buttonOK.getPreferredSize().width, 25);

		//---- button4 ----
		button4.setText("Cancelar");
		button4.setFont(new Font("Arial", Font.PLAIN, 14));
		button4.addActionListener(e -> button4ActionPerformed(e));
		contentPane.add(button4);
		button4.setBounds(795, 850, button4.getPreferredSize().width, 25);

		//---- buttonEliminarCandidato ----
		buttonEliminarCandidato.setText("Eliminar candidato");
		buttonEliminarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonEliminarCandidato.addActionListener(e -> buttonEliminarCandidatoActionPerformed(e));
		contentPane.add(buttonEliminarCandidato);
		buttonEliminarCandidato.setBounds(795, 810, buttonEliminarCandidato.getPreferredSize().width, 25);
		contentPane.add(separator6);
		separator6.setBounds(15, 840, 925, 5);

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
			povoarTabelaListas();
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(15, 200, 930, 145);

		//---- buttonAtualizarCandidatos ----
		buttonAtualizarCandidatos.setText("Atualizar tabela candidatos");
		buttonAtualizarCandidatos.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAtualizarCandidatos.addActionListener(e -> buttonAtualizarCandidatosActionPerformed(e));
		contentPane.add(buttonAtualizarCandidatos);
		buttonAtualizarCandidatos.setBounds(565, 810, 220, 25);

		//---- button5 ----
		button5.setText("Atualizar tabela listas");
		button5.setFont(new Font("Arial", Font.PLAIN, 14));
		button5.addActionListener(e -> button5ActionPerformed(e));
		contentPane.add(button5);
		button5.setBounds(345, 355, button5.getPreferredSize().width, 25);

		//======== scrollPane2 ========
		{
			scrollPane2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					scrollPane2MouseClicked(e);
				}
			});

			//---- table3 ----
			table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table3.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Sigla", "Nome", null
				}
			));
			table3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					scrollPane2MouseClicked(e);
				}
			});
			table3.getColumnModel().getColumn(2).setPreferredWidth(0);
			table3.getColumnModel().getColumn(2).setMinWidth(0);
			table3.getColumnModel().getColumn(2).setWidth(0);
			table3.getColumnModel().getColumn(2).setMaxWidth(0);
			povoarVotavel();
			scrollPane2.setViewportView(table3);
		}
		contentPane.add(scrollPane2);
		scrollPane2.setBounds(620, 75, 325, 110);

		//---- comboBox2 ----
		comboBox2.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
			"Primario",
			"Secundario"
		}));
		contentPane.add(comboBox2);
		comboBox2.setBounds(385, 515, 105, 25);

		//---- comboBox3 ----
		comboBox3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(comboBox3);
		comboBox3.setBounds(80, 515, 145, 25);

		//---- label12 ----
		label12.setText("Partido:");
		label12.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label12);
		label12.setBounds(10, 515, label12.getPreferredSize().width, 25);

		//---- label13 ----
		label13.setText("Tipo:");
		label13.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label13);
		label13.setBounds(330, 515, 40, 25);

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
		setSize(980, 920);
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
	private JComponent separator1;
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
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton buttonAtualizarCandidatos;
	private JButton button5;
	private JScrollPane scrollPane2;
	private JTable table3;
	private JComboBox<String> comboBox2;
	private JComboBox comboBox3;
	private JLabel label12;
	private JLabel label13;
	private JFileChooser fileChooser1;
	private JDialog dialogoCalendario;
	private JCalendar calendar1;
	private JButton buttonConfirmarDataInicio;
	private JButton buttonCancelarData;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
