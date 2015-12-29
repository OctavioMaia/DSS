/*
 * Created by JFormDesigner on Fri Dec 11 14:21:01 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Calendar;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import Business.*;

/**
 * @author Octavio Maia
 */
public class MainEleitor {
	
	private SGE sge;
	
	public MainEleitor(SGE s) {
		sge = s;
		initComponents(sge);
		frameEleitor.setVisible(true);
	}

	private void buttonVotarActionPerformed(ActionEvent e) {
		if(!sge.eleitorVotar(sge.getEleitor())) 
			new Votar(sge);
		else{
			buttonVotar.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Já votou nesta eleição!");
		}
	}

	private void button1ActionPerformed(ActionEvent e) {
		if(tableEleicoes.getValueAt(tableEleicoes.getSelectedRow(), 2).getClass().getSimpleName().equals("EleicaoAR")){
			ResultadosAR gui = new ResultadosAR(null, (EleicaoAR) tableEleicoes.getValueAt(tableEleicoes.getSelectedRow(), 2));
		}else{
			ResultadosPR gui = new ResultadosPR(null, (EleicaoPR) tableEleicoes.getValueAt(tableEleicoes.getSelectedRow(), 2));
		}
	}

	private void scrollPane1MouseClicked(MouseEvent e) {
		if(tableEleicoes.getSelectedColumns().length>0){
			button1.setEnabled(true);
		}
	}
	
	private void verificaVoto() {
		if(sge.eleitorVotar(sge.getEleitor())) 
			buttonVotar.setEnabled(false);
	}
	
	private void povoarTabela(){
		Set<Eleicao> historico = sge.getEleicoesTerminadas();
		Object[][] data = new Object[historico.size()][]; 
		int i=0;
		
		if (tableEleicoes.getRowCount() > 0) {
            for (int conta = tableEleicoes.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableEleicoes.getModel()).removeRow(conta);
            }
        }
		
		for(Eleicao el : historico){	
			data[i] = el.toTable();
			
			DefaultTableModel model = (DefaultTableModel) tableEleicoes.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void button2ActionPerformed(ActionEvent e) {
		frameEleitor.setVisible(false);
		new Login(sge);
	}

	private void initComponents(SGE sge) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		frameEleitor = new JFrame();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		labelNome = new JLabel();
		labelEleicao = new JLabel();
		labelDataInicio = new JLabel();
		buttonVotar = new JButton();
		separator2 = new JSeparator();
		label5 = new JLabel();
		scrollPane1 = new JScrollPane();
		tableEleicoes = new JTable();
		button1 = new JButton();
		separator3 = new JSeparator();
		button2 = new JButton();

		//======== frameEleitor ========
		{
			frameEleitor.setFont(new Font("Arial", Font.PLAIN, 12));
			frameEleitor.setTitle("Janela de Eleitor");
			frameEleitor.setResizable(false);
			Container frameEleitorContentPane = frameEleitor.getContentPane();
			frameEleitorContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Nome do eleitor:");
			label1.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label1);
			label1.setBounds(20, 15, label1.getPreferredSize().width, 25);

			//---- label2 ----
			label2.setText("Tipo de elei\u00e7\u00e3o:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label2);
			label2.setBounds(20, 45, 103, 25);

			//---- label3 ----
			label3.setText("Data de in\u00edcio:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label3);
			label3.setBounds(20, 75, 103, 25);

			//---- labelNome ----
			labelNome.setText(sge.getEleitor().getNome());
			labelNome.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelNome);
			labelNome.setBounds(140, 15, 310, 25);

			//---- labelEleicao ----
			if(sge.eleicaoAtiva().getClass().getSimpleName().equals("EleicaoAR")) 
				labelEleicao.setText("Eleição Assembleia República");
			else 
				labelEleicao.setText("Eleição Presidência República");
			labelEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelEleicao);
			labelEleicao.setBounds(140, 45, 310, 25);

			//---- labelDataInicio ----
			labelDataInicio.setText(sge.eleicaoAtiva().getData().get(Calendar.DAY_OF_MONTH)+"/"+(sge.eleicaoAtiva().getData().get(Calendar.MONTH)+1)+"/"+sge.eleicaoAtiva().getData().get(Calendar.YEAR));
			labelDataInicio.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelDataInicio);
			labelDataInicio.setBounds(140, 75, 310, 25);

			//---- buttonVotar ----
			buttonVotar.setText("Votar");
			buttonVotar.setFont(new Font("Arial", Font.BOLD, 14));
			buttonVotar.addActionListener(e -> buttonVotarActionPerformed(e));
			frameEleitorContentPane.add(buttonVotar);
			buttonVotar.setBounds(480, 25, 85, 55);
			frameEleitorContentPane.add(separator2);
			separator2.setBounds(10, 110, 555, 5);

			//---- label5 ----
			label5.setText("Hist\u00f3rico de Elei\u00e7\u00f5es");
			label5.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label5);
			label5.setBounds(new Rectangle(new Point(15, 115), label5.getPreferredSize()));

			//======== scrollPane1 ========
			{
				scrollPane1.setFont(new Font("Arial", Font.PLAIN, 12));
				scrollPane1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						scrollPane1MouseClicked(e);
					}
				});

				//---- tableEleicoes ----
				tableEleicoes.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Data de in\u00edcio", "Tipo de Elei\u00e7\u00e3o", "id"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, Object.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, true, true
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
					TableColumnModel cm = tableEleicoes.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableEleicoes.setFont(new Font("Arial", Font.PLAIN, 12));
				tableEleicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableEleicoes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						scrollPane1MouseClicked(e);
					}
				});
				tableEleicoes.getColumnModel().getColumn(2).setPreferredWidth(0);
				tableEleicoes.getColumnModel().getColumn(2).setMinWidth(0);
				tableEleicoes.getColumnModel().getColumn(2).setWidth(0);
				tableEleicoes.getColumnModel().getColumn(2).setMaxWidth(0);
				verificaVoto();
				povoarTabela();
				scrollPane1.setViewportView(tableEleicoes);
			}
			frameEleitorContentPane.add(scrollPane1);
			scrollPane1.setBounds(15, 140, 555, 200);

			//---- button1 ----
			button1.setText("Ver resultados");
			button1.setFont(new Font("Arial", Font.PLAIN, 14));
			button1.setEnabled(false);
			button1.addActionListener(e -> button1ActionPerformed(e));
			frameEleitorContentPane.add(button1);
			button1.setBounds(15, 345, 555, button1.getPreferredSize().height);
			frameEleitorContentPane.add(separator3);
			separator3.setBounds(15, 375, 555, 5);

			//---- button2 ----
			button2.setText("Sair");
			button2.setFont(new Font("Arial", Font.PLAIN, 14));
			button2.addActionListener(e -> button2ActionPerformed(e));
			frameEleitorContentPane.add(button2);
			button2.setBounds(505, 380, 60, 25);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < frameEleitorContentPane.getComponentCount(); i++) {
					Rectangle bounds = frameEleitorContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = frameEleitorContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				frameEleitorContentPane.setMinimumSize(preferredSize);
				frameEleitorContentPane.setPreferredSize(preferredSize);
			}
			frameEleitor.setSize(600, 455);
			frameEleitor.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame frameEleitor;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel labelNome;
	private JLabel labelEleicao;
	private JLabel labelDataInicio;
	private JButton buttonVotar;
	private JSeparator separator2;
	private JLabel label5;
	private JScrollPane scrollPane1;
	private JTable tableEleicoes;
	private JButton button1;
	private JSeparator separator3;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}