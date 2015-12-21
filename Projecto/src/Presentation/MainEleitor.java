/*
 * Created by JFormDesigner on Fri Dec 11 14:21:01 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.*;

import Business.SGE;

/**
 * @author Octavio Maia
 */
public class MainEleitor {
	
	public MainEleitor(SGE sge) {
		initComponents(sge);
	}

	private void initComponents(SGE sge) {
		//vars
		String nome,tipo,data;
		Calendar cal = sge.eleicaoAtiva().getData();

		nome = sge.getEleitor().getNome();
		
		if(sge.eleicaoAtiva().getClass().getSimpleName().equals("EleicaoAR")) 
			tipo = "Eleição A.R.";
		else 
			tipo = "Eleição P.R.";
		
		data =  cal.get(Calendar.DAY_OF_WEEK)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);
		
		//fim vars
		
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
			label1.setBounds(new Rectangle(new Point(20, 20), label1.getPreferredSize()));

			//---- label2 ----
			label2.setText("Tipo de elei\u00e7\u00e3o:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label2);
			label2.setBounds(20, 50, 103, 17);

			//---- label3 ----
			label3.setText("Data de in\u00edcio:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label3);
			label3.setBounds(20, 80, 103, 17);

			//---- labelNome ----
			labelNome.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelNome);
			labelNome.setBounds(140, 20, 103, 17);

			//---- labelEleicao ----
			labelEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelEleicao);
			labelEleicao.setBounds(140, 50, 103, 17);

			//---- labelDataInicio ----
			labelDataInicio.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(labelDataInicio);
			labelDataInicio.setBounds(140, 80, 103, 17);

			//---- buttonVotar ----
			buttonVotar.setText("Votar");
			buttonVotar.setFont(new Font("Arial", Font.BOLD, 14));
			frameEleitorContentPane.add(buttonVotar);
			buttonVotar.setBounds(285, 25, 85, 55);
			frameEleitorContentPane.add(separator2);
			separator2.setBounds(10, 110, 365, 10);

			//---- label5 ----
			label5.setText("Hist\u00f3rico de Elei\u00e7\u00f5es");
			label5.setFont(new Font("Arial", Font.PLAIN, 14));
			frameEleitorContentPane.add(label5);
			label5.setBounds(new Rectangle(new Point(20, 120), label5.getPreferredSize()));

			//======== scrollPane1 ========
			{
				scrollPane1.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableEleicoes ----
				tableEleicoes.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null, null},
						{null, null, null},
						{null, null, null},
						{null, null, ""},
						{null, null, null},
					},
					new String[] {
						"Data de in\u00edcio", "Data de fim", "Tipo de Elei\u00e7\u00e3o"
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
				scrollPane1.setViewportView(tableEleicoes);
			}
			frameEleitorContentPane.add(scrollPane1);
			scrollPane1.setBounds(15, 140, 360, 200);

			//---- button1 ----
			button1.setText("Ver resultados");
			button1.setFont(new Font("Arial", Font.PLAIN, 14));
			button1.setEnabled(false);
			frameEleitorContentPane.add(button1);
			button1.setBounds(15, 345, 360, button1.getPreferredSize().height);

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
			frameEleitor.setSize(400, 415);
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
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
