/*
 * Created by JFormDesigner on Mon Dec 21 19:20:22 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import Business.Boletim;
import Business.Eleicao;
import Business.Eleitor;
import Business.Lista;
import Business.ListaPR;
import Business.Listavel;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class Votar extends JFrame {
	
	private Eleicao eleicao;
	private Boletim boletim;
	private SGE sge;
	private String path;
	
	public Votar(SGE s) {
		//sge = s;
		//eleicao = sge.eleicaoAtiva();
		initComponents();
		this.setVisible(true);
	}

	private void buttonLimparActionPerformed(ActionEvent e) {
		table1.clearSelection();
		table1.getSelectionModel().clearSelection();
	}

	private void buttonVotarActionPerformed(ActionEvent e) {
		Eleitor eleitor = sge.getEleitor();
		
		if(table1.getSelectedRowCount()==1){
			sge.addVoto(eleicao, (Listavel) table1.getValueAt(table1.getSelectedRow(), 2), eleitor);			
		}else if(table1.getSelectedRowCount()>1){
			sge.addVotoNulo(eleicao, eleitor);
		}else{
			sge.addVotoBranco(eleicao, eleitor);
		}
	}

	private void povoarTabela() {
		boletim = sge.getBoletim(eleicao, sge.getEleitor());
		int i = 0;
		List <Listavel> lista = boletim.getListas();
		Object[][] data = new Object[lista.size()][]; 
		
		for(Listavel l :lista){
			data[i] = l.toTable();
			
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.addRow(data[i]);
			
			i++;
		}
	}

	private void table1FocusGained(FocusEvent e) {
		Listavel l;
		
		for (int i = 0; i < table1.getRowCount(); i++) {
			if (table1.isRowSelected(i) && table1.getSelectedRowCount()==1) {
				l = (Listavel) table1.getValueAt(table1.getSelectedRow(), 2);
				path = l.pathImage();
			}
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		labelImagem = new JLabel();
		nomeEleitor = new JLabel();
		buttonLimpar = new JButton();
		buttonVotar = new JButton();

		//======== this ========
		setTitle("Boletim de voto");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Nome do eleitor:");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(15, 15, label1.getPreferredSize().width, 20);

		//======== scrollPane1 ========
		{

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "Identifica\u00e7\u00e3o", null
				}
			) {
				boolean[] columnEditable = new boolean[] {
					false, true, true
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}
			});
			{
				TableColumnModel cm = table1.getColumnModel();
				cm.getColumn(0).setResizable(false);
			}
			table1.setFont(new Font("Arial", Font.PLAIN, 12));
			table1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					table1FocusGained(e);
				}
			});
			table1.getColumnModel().getColumn(2).setPreferredWidth(0);
			table1.getColumnModel().getColumn(2).setMinWidth(0);
			table1.getColumnModel().getColumn(2).setWidth(0);
			table1.getColumnModel().getColumn(2).setMaxWidth(0);
			povoarTabela();
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(15, 45, 230, 200);

		//---- labelImagem ----
		labelImagem.setIcon(new ImageIcon(getClass().getResource(path)));
		labelImagem.setText("Imagem");
		contentPane.add(labelImagem);
		labelImagem.setBounds(290, 45, 150, 150);

		//---- nomeEleitor ----
		nomeEleitor.setText(sge.getEleitor().getNome());
		nomeEleitor.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeEleitor);
		nomeEleitor.setBounds(125, 15, 335, 20);

		//---- buttonLimpar ----
		buttonLimpar.setText("Limpar");
		buttonLimpar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonLimpar.addActionListener(e -> buttonLimparActionPerformed(e));
		contentPane.add(buttonLimpar);
		buttonLimpar.setBounds(280, 220, buttonLimpar.getPreferredSize().width, 25);

		//---- buttonVotar ----
		buttonVotar.setText("Votar");
		buttonVotar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonVotar.addActionListener(e -> buttonVotarActionPerformed(e));
		contentPane.add(buttonVotar);
		buttonVotar.setBounds(375, 220, 75, 25);

		contentPane.setPreferredSize(new Dimension(485, 295));
		setSize(485, 295);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JLabel labelImagem;
	private JLabel nomeEleitor;
	private JButton buttonLimpar;
	private JButton buttonVotar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
