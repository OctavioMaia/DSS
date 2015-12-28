/*
 * Created by JFormDesigner on Tue Dec 15 18:17:45 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import Business.Candidato;
import Business.Eleicao;
import Business.EleicaoPR;
import Business.ListaPR;
import Business.Listavel;
import Business.SGE;
import Exception.ExceptionEleicaoEstado;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;
import Exception.ExceptionMandanteInvalido;

import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author Octavio Maia
 */
public class GerirPR {
	
	private GregorianCalendar dataInicio,dataNasc;
	private SGE sge;
	private EleicaoPR eleicao;
	
	public GerirPR(SGE s,EleicaoPR e) {
		sge = s;
		eleicao = e;
		initComponents(sge,e);
		GerirPR.setVisible(true);
	}

	private void buttonProcurarActionPerformed(ActionEvent e) {
		fileChooser1.setVisible(true);
		int result = fileChooser1.showOpenDialog(fileChooser1);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser1.getSelectedFile();
		    pathImagem.setText(selectedFile.getAbsolutePath());
		    fileChooser1.setVisible(false);
		}else
			fileChooser1.setVisible(false);
	}

	private void buttonSairActionPerformed(ActionEvent e) {
		GerirPR.dispose();
		//voltar atras?
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
			sge.addCandidatoPR(eleicao,c);
			
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

	//data inicio
	private void button1ActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(true);
		
		//TODO parse a info do calendario!
		//TODO depois mudar a label dd/mm/aa pra data selecionada!!
	}
	
	//data nascimento
	private void buttonDataNascimentoActionPerformed(ActionEvent e) {
		dialogoCalendario2.setVisible(true);
		
		//TODO parse a info do calendario!
		//TODO depois mudar a label dd/mm/aa pra data selecionada!!
	}

	private void buttonConfirmarDataActionPerformed(ActionEvent e) {
		Calendar cal = calendar1.getCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		int ano = cal.get(Calendar.YEAR);
		dataInicioEleicao.setText(dia+"/"+(mes+1)+"/"+ano);
		
		dataInicio = new GregorianCalendar(ano, mes, dia);
		sge.alterarDataEleicao(eleicao,dataInicio);
		//TODO implementar dataInicio
		
		dialogoCalendario.setVisible(false);
		dialogoCalendario2.setVisible(false);
	}
	
	private void buttonConfirmarDataNascimentoActionPerformed(ActionEvent e) {
		Calendar cal = calendar2.getCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		int ano = cal.get(Calendar.YEAR);
		dataNascimento.setText(dia+"/"+(mes+1)+"/"+ano);
		
		dataNasc = new GregorianCalendar(ano, mes, dia);
		//TODO implementar dataNascimento
		
		dialogoCalendario.setVisible(false);
		dialogoCalendario2.setVisible(false);
	}
	
	private void buttonCancelarDataActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(false);
		dialogoCalendario2.setVisible(false);
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

	private void buttonEliminarCandidatoActionPerformed(ActionEvent e) {
		sge.removeLista(eleicao, (Listavel) tableCandidatos.getValueAt(tableCandidatos.getSelectedRow(), 2));
		povoarTabela();
	}
	
	private void povoarTabela() {
		Collection<ListaPR> candidatos = eleicao.getLista();
		Object[][] data = new Object[candidatos.size()][]; 
		int i=0;
		
		if (tableCandidatos.getRowCount() > 0) {
            for (int conta = tableCandidatos.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableCandidatos.getModel()).removeRow(conta);;
            }
        }
		
		for(ListaPR el : candidatos){	
			System.out.println("id" + el.getIdListaPR());
			data[i] = el.toTable();
			
			DefaultTableModel model = (DefaultTableModel) tableCandidatos.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void buttonAtualizarActionPerformed(ActionEvent e) {
		povoarTabela();
	}

	private void tableCandidatosFocusGained(FocusEvent e) {
		if(tableCandidatos.getSelectedColumnCount()==1) buttonEliminarCandidato.setEnabled(true);
	}
	
	
	private void initComponents(SGE sge,EleicaoPR eleicao) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		GerirPR = new JFrame();
		label1 = new JLabel();
		buttonApagarInfo = new JButton();
		label3 = new JLabel();
		label4 = new JLabel();
		nomeCandidato = new JTextField();
		buttonProcurar = new JButton();
		pathImagem = new JTextField();
		buttonAdicionarCandidato = new JButton();
		buttonSair = new JButton();
		buttonData = new JButton();
		dataInicioEleicao = new JLabel();
		separator1 = compFactory.createSeparator("Inser\u00e7\u00e3o de candidatos", SwingConstants.CENTER);
		label5 = new JLabel();
		naturalidade = new JTextField();
		label6 = new JLabel();
		residencia = new JTextField();
		label2 = new JLabel();
		dataNascimento = new JLabel();
		buttonDataNascimento = new JButton();
		profissao = new JTextField();
		label7 = new JLabel();
		bi = new JTextField();
		label8 = new JLabel();
		separator2 = new JSeparator();
		scrollPane1 = new JScrollPane();
		tableCandidatos = new JTable();
		labelFoto = new JLabel();
		buttonEliminarCandidato = new JButton();
		button1 = new JButton();
		dialogoCalendario = new JDialog();
		calendar1 = new JCalendar();
		buttonConfirmarDataInicio = new JButton();
		buttonCancelarData = new JButton();
		dialogoCalendario2 = new JDialog();
		calendar2 = new JCalendar();
		buttonConfirmarDataNascimento = new JButton();
		buttonCancelarData2 = new JButton();
		fileChooser1 = new JFileChooser();

		//======== GerirPR ========
		{
			GerirPR.setTitle("Gerir Elei\u00e7\u00e3o Presid\u00eancia da Rep\u00fablica");
			GerirPR.setResizable(false);
			GerirPR.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container GerirPRContentPane = GerirPR.getContentPane();
			GerirPRContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Data in\u00edcio da elei\u00e7\u00e3o:");
			label1.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(15, 25), label1.getPreferredSize()));

			//---- buttonApagarInfo ----
			buttonApagarInfo.setText("Apagar informa\u00e7\u00f5es");
			buttonApagarInfo.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonApagarInfo.addActionListener(e -> buttonApagarInfoActionPerformed(e));
			GerirPRContentPane.add(buttonApagarInfo);
			buttonApagarInfo.setBounds(365, 305, 165, buttonApagarInfo.getPreferredSize().height);

			//---- label3 ----
			label3.setText("Nome do candidato:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label3);
			label3.setBounds(15, 80, 139, 22);

			//---- label4 ----
			label4.setText("Foto");
			label4.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label4);
			label4.setBounds(15, 270, 35, 22);

			//---- nomeCandidato ----
			nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(nomeCandidato);
			nomeCandidato.setBounds(155, 80, 375, nomeCandidato.getPreferredSize().height);

			//---- buttonProcurar ----
			buttonProcurar.setText("Procurar");
			buttonProcurar.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonProcurar.addActionListener(e -> buttonProcurarActionPerformed(e));
			GerirPRContentPane.add(buttonProcurar);
			buttonProcurar.setBounds(55, 270, 95, buttonProcurar.getPreferredSize().height);

			//---- pathImagem ----
			pathImagem.setEditable(false);
			GerirPRContentPane.add(pathImagem);
			pathImagem.setBounds(155, 270, 375, 25);

			//---- buttonAdicionarCandidato ----
			buttonAdicionarCandidato.setText("Adicionar");
			buttonAdicionarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonAdicionarCandidato.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
			GerirPRContentPane.add(buttonAdicionarCandidato);
			buttonAdicionarCandidato.setBounds(265, 305, 95, 25);

			//---- buttonSair ----
			buttonSair.setText("Sair");
			buttonSair.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonSair.addActionListener(e -> buttonSairActionPerformed(e));
			GerirPRContentPane.add(buttonSair);
			buttonSair.setBounds(375, 585, 155, 25);

			//---- buttonData ----
			buttonData.setText("Alterar");
			buttonData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonData.addActionListener(e -> button1ActionPerformed(e));
			GerirPRContentPane.add(buttonData);
			buttonData.setBounds(305, 20, 80, 25);

			//---- dataInicioEleicao ----
			Calendar cal = eleicao.getData();
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH)+1;
			int ano = cal.get(Calendar.YEAR);
			dataInicioEleicao.setText(dia+"/"+mes+"/"+ano);
			dataInicioEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(dataInicioEleicao);
			dataInicioEleicao.setBounds(170, 25, 115, 17);

			//---- separator1 ----
			separator1.setFont(new Font("Arial", Font.PLAIN, 12));
			GerirPRContentPane.add(separator1);
			separator1.setBounds(15, 60, 510, 15);

			//---- label5 ----
			label5.setText("Naturalidade:");
			label5.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label5);
			label5.setBounds(15, 110, 139, 22);

			//---- naturalidade ----
			naturalidade.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(naturalidade);
			naturalidade.setBounds(155, 110, 375, 23);

			//---- label6 ----
			label6.setText("Resid\u00eancia:");
			label6.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label6);
			label6.setBounds(15, 140, 139, 22);

			//---- residencia ----
			residencia.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(residencia);
			residencia.setBounds(155, 140, 375, 23);

			//---- label2 ----
			label2.setText("Data de nascimento:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label2);
			label2.setBounds(15, 235, 139, 17);

			//---- dataNascimento ----
			dataNascimento.setText("dd/mm/aa");
			dataNascimento.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(dataNascimento);
			dataNascimento.setBounds(170, 235, 115, 17);

			//---- buttonDataNascimento ----
			buttonDataNascimento.setText("Alterar");
			buttonDataNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonDataNascimento.addActionListener(e -> buttonDataNascimentoActionPerformed(e));
			GerirPRContentPane.add(buttonDataNascimento);
			buttonDataNascimento.setBounds(305, 230, 80, 25);

			//---- profissao ----
			profissao.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(profissao);
			profissao.setBounds(155, 170, 375, 23);

			//---- label7 ----
			label7.setText("Profiss\u00e3o:");
			label7.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label7);
			label7.setBounds(15, 170, 139, 22);

			//---- bi ----
			bi.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(bi);
			bi.setBounds(155, 200, 375, 23);

			//---- label8 ----
			label8.setText("B.I. / C.C.");
			label8.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label8);
			label8.setBounds(15, 200, 139, 22);
			GerirPRContentPane.add(separator2);
			separator2.setBounds(15, 340, 510, 5);

			//======== scrollPane1 ========
			{

				//---- tableCandidatos ----
				tableCandidatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableCandidatos.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Nome", "B.I. / C.C.", null
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, String.class, String.class
					};
					boolean[] columnEditable = new boolean[] {
						true, true, false
					};
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
					@Override
					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return columnEditable[columnIndex];
					}
				});
				{
					TableColumnModel cm = tableCandidatos.getColumnModel();
					cm.getColumn(2).setResizable(false);
				}
				tableCandidatos.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						tableCandidatosFocusGained(e);
					}
				});
				tableCandidatos.getColumnModel().getColumn(2).setPreferredWidth(0);
				tableCandidatos.getColumnModel().getColumn(2).setMinWidth(0);
				tableCandidatos.getColumnModel().getColumn(2).setWidth(0);
				tableCandidatos.getColumnModel().getColumn(2).setMaxWidth(0);
				povoarTabela();
				scrollPane1.setViewportView(tableCandidatos);
			}
			GerirPRContentPane.add(scrollPane1);
			scrollPane1.setBounds(15, 350, 335, 220);

			//---- labelFoto ----
			labelFoto.setText("Imagem");
			GerirPRContentPane.add(labelFoto);
			labelFoto.setBounds(375, 370, 150, 150);

			//---- buttonEliminarCandidato ----
			buttonEliminarCandidato.setText("Eliminar candidato");
			buttonEliminarCandidato.setEnabled(false);
			buttonEliminarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonEliminarCandidato.addActionListener(e -> buttonEliminarCandidatoActionPerformed(e));
			GerirPRContentPane.add(buttonEliminarCandidato);
			buttonEliminarCandidato.setBounds(375, 540, 154, buttonEliminarCandidato.getPreferredSize().height);

			//---- button1 ----
			button1.setText("Atualizar tabela");
			button1.setFont(new Font("Arial", Font.PLAIN, 14));
			button1.addActionListener(e -> buttonAtualizarActionPerformed(e));
			GerirPRContentPane.add(button1);
			button1.setBounds(15, 585, 335, 25);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < GerirPRContentPane.getComponentCount(); i++) {
					Rectangle bounds = GerirPRContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = GerirPRContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				GerirPRContentPane.setMinimumSize(preferredSize);
				GerirPRContentPane.setPreferredSize(preferredSize);
			}
			GerirPR.setSize(560, 660);
			GerirPR.setLocationRelativeTo(null);
		}

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

		//======== dialogoCalendario2 ========
		{
			dialogoCalendario2.setTitle("Calendario");
			dialogoCalendario2.setResizable(false);
			dialogoCalendario2.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container dialogoCalendario2ContentPane = dialogoCalendario2.getContentPane();
			dialogoCalendario2ContentPane.setLayout(null);
			dialogoCalendario2ContentPane.add(calendar2);
			calendar2.setBounds(0, 0, 210, 155);

			//---- buttonConfirmarDataNascimento ----
			buttonConfirmarDataNascimento.setText("Confirmar");
			buttonConfirmarDataNascimento.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonConfirmarDataNascimento.addActionListener(e -> buttonConfirmarDataNascimentoActionPerformed(e));
			dialogoCalendario2ContentPane.add(buttonConfirmarDataNascimento);
			buttonConfirmarDataNascimento.setBounds(5, 155, 90, 28);

			//---- buttonCancelarData2 ----
			buttonCancelarData2.setText("Cancelar");
			buttonCancelarData2.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonCancelarData2.addActionListener(e -> buttonCancelarDataActionPerformed(e));
			dialogoCalendario2ContentPane.add(buttonCancelarData2);
			buttonCancelarData2.setBounds(110, 155, 90, 28);

			dialogoCalendario2ContentPane.setPreferredSize(new Dimension(225, 235));
			dialogoCalendario2.setSize(225, 235);
			dialogoCalendario2.setLocationRelativeTo(null);
		}

		//---- fileChooser1 ----
		fileChooser1.setFont(new Font("Arial", Font.PLAIN, 11));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame GerirPR;
	private JLabel label1;
	private JButton buttonApagarInfo;
	private JLabel label3;
	private JLabel label4;
	private JTextField nomeCandidato;
	private JButton buttonProcurar;
	private JTextField pathImagem;
	private JButton buttonAdicionarCandidato;
	private JButton buttonSair;
	private JButton buttonData;
	private JLabel dataInicioEleicao;
	private JComponent separator1;
	private JLabel label5;
	private JTextField naturalidade;
	private JLabel label6;
	private JTextField residencia;
	private JLabel label2;
	private JLabel dataNascimento;
	private JButton buttonDataNascimento;
	private JTextField profissao;
	private JLabel label7;
	private JTextField bi;
	private JLabel label8;
	private JSeparator separator2;
	private JScrollPane scrollPane1;
	private JTable tableCandidatos;
	private JLabel labelFoto;
	private JButton buttonEliminarCandidato;
	private JButton button1;
	private JDialog dialogoCalendario;
	private JCalendar calendar1;
	private JButton buttonConfirmarDataInicio;
	private JButton buttonCancelarData;
	private JDialog dialogoCalendario2;
	private JCalendar calendar2;
	private JButton buttonConfirmarDataNascimento;
	private JButton buttonCancelarData2;
	private JFileChooser fileChooser1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}