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
	
	public Votar(SGE s) {
		//sge = s;
		//eleicao = sge.eleicaoAtiva();
		//boletim = sge.getBoletim(eleicao, sge.getEleitor());
		initComponents();
		this.setVisible(true);
	}

	private void buttonLimparActionPerformed(ActionEvent e) {
		table1.clearSelection();
		table1.getSelectionModel().clearSelection();
	}

	private void buttonVotarActionPerformed(ActionEvent e) {
		List<Listavel> a = boletim.getListas();
		Lista lista_ar = null;
		ListaPR lista_pr = null;
		Eleitor eleitor = sge.getEleitor();
		
		if(table1.getSelectedRowCount()==1){
			for(Listavel l : a){
				if(l.getClass().getSimpleName().equals("Lista")){
					lista_ar = (Lista) l;
					if(lista_ar.getNome().equals(table1.getModel().getValueAt(table1.getSelectedRow(), table1.getSelectedColumn()))){
						sge.addVoto(eleicao, lista_ar, eleitor);
						break;
					}
				}else{
					lista_pr = (ListaPR) l;
					if(lista_pr.getCandidato().getNome().equals(table1.getModel().getValueAt(table1.getSelectedRow(), table1.getSelectedColumn()))){
						sge.addVoto(eleicao, lista_pr, eleitor);
						break;
					}
				}
			}			
		}else if(table1.getSelectedRowCount()>1){
			sge.addVotoNulo(eleicao, eleitor);
		}else{
			sge.addVotoBranco(eleicao, eleitor);
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
					"Listas"
				}
			) {
				Class<?>[] columnTypes = new Class<?>[] {
					String.class
				};
				boolean[] columnEditable = new boolean[] {
					false
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
				TableColumnModel cm = table1.getColumnModel();
				cm.getColumn(0).setResizable(false);
			}
			table1.setFont(new Font("Arial", Font.PLAIN, 12));
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(15, 45, 230, 200);
		contentPane.add(labelImagem);
		labelImagem.setBounds(290, 45, 150, 150);

		//---- nomeEleitor ----
		//nomeEleitor.setText(sge.getEleitor().getNome());
		nomeEleitor.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeEleitor);
		nomeEleitor.setBounds(125, 15, 335, 20);

		//---- buttonLimpar ----
		buttonLimpar.setText("Limpar");
		buttonLimpar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonLimpar.addActionListener(e -> buttonLimparActionPerformed(e));
		contentPane.add(buttonLimpar);
		buttonLimpar.setBounds(new Rectangle(new Point(280, 220), buttonLimpar.getPreferredSize()));

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
