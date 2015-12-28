/*
 * Created by JFormDesigner on Wed Dec 23 20:01:18 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.table.*;

import Business.SGE;
import Exception.ExceptionEleicaoAtiva;
import Exception.ExceptionIniciarEleicao;
import Exception.ExceptionTerminarEleicao;

/**
 * @author Octavio Maia
 */
public class MainAdmin extends JFrame {
	
	private SGE sge;
	
	public MainAdmin(SGE s) {
		sge = s;
		initComponents();
		this.setVisible(true);
	}

	private void buttonSairActionPerformed(ActionEvent e) {
		this.setVisible(false);	
	}

	private void buttonIniciarEleicaoActionPerformed(ActionEvent e) {
		if(panel1.isShowing()){
			try {
				sge.iniciarEleicao(sge.getEleicao((int) table1.getValueAt(table1.getSelectedRow(), 2)));
			} catch (ExceptionEleicaoAtiva e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExceptionIniciarEleicao e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			try {
				sge.iniciarEleicao(sge.getEleicao((int) table2.getValueAt(table2.getSelectedRow(), 2)));
			} catch (ExceptionEleicaoAtiva e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExceptionIniciarEleicao e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void buttonCriarEleicaoActionPerformed(ActionEvent e) {
		new CriarEleicao(sge);
	}

	private void buttonInserirCadernoActionPerformed(ActionEvent e) {
		new CadernoRecenseamento(sge);
	}

	private void buttonGerirEleicaoActionPerformed(ActionEvent e) {
		if(panel1.isShowing()){
			if(table1.getValueAt(table1.getSelectedRow(), 1).equals("Assembleia da República")){
				new GerirAR(sge, null);
			}else{
				new GerirPR(sge, null);
			}
		}
	}

	private void buttonVerResultadosActionPerformed(ActionEvent e) {
		if(panel1.isShowing()){
			if(table1.getValueAt(table1.getSelectedRow(), 1).equals("Assembleia da República")){
				new ResultadosAR(sge, (int) table1.getValueAt(table1.getSelectedRow(), 2));
			}else{
				new ResultadosPR(sge, (int) table1.getValueAt(table1.getSelectedRow(), 2));
			}
		}else{
			if(table2.getValueAt(table2.getSelectedRow(), 1).equals("Assembleia da República")){
				new ResultadosAR(sge, (int) table2.getValueAt(table2.getSelectedRow(), 2));
			}else{
				new ResultadosPR(sge, (int) table2.getValueAt(table2.getSelectedRow(), 2));
			}
		}
	}

	private void table1FocusGained(FocusEvent e) {
		if(panel1.isShowing()){
			for (int i = 0; i < table1.getRowCount(); i++) {
				if(table1.isRowSelected(i)){
					buttonVerResultados.setEnabled(true);
				}
			}
		}else{
			buttonVerResultados.setEnabled(false);
		}
	}

	private void scrollPane2FocusGained(FocusEvent e) {
		if(panel2.isShowing()){
			for (int i = 0; i < table2.getRowCount(); i++) {
				if(table2.isRowSelected(i)){
					buttonVerResultados.setEnabled(true);
				}
			}
		}else{
			buttonVerResultados.setEnabled(false);
		}
	}

	private void buttonTerminarEleicaoActionPerformed(ActionEvent e) {
		try {
			sge.terminarEleicao(sge.eleicaoAtiva());
		} catch (ExceptionEleicaoAtiva e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExceptionTerminarEleicao e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		



	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		label1 = new JLabel();
		labelEleicaoAtiva = new JLabel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		panel2 = new JPanel();
		scrollPane2 = new JScrollPane();
		table2 = new JTable();
		separator1 = new JSeparator();
		buttonVerResultados = new JButton();
		buttonIniciarEleicao = new JButton();
		buttonCriarEleicao = new JButton();
		buttonGerirEleicao = new JButton();
		buttonInserirCaderno = new JButton();
		buttonSair = new JButton();
		buttonTerminarEleicao = new JButton();

		//======== this ========
		setTitle("Main Admin");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Elei\u00e7\u00e3o ativa:");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(10, 10, label1.getPreferredSize().width, 25);

		//---- labelEleicaoAtiva ----
		//labelEleicaoAtiva.setText(sge.eleicaoAtiva().toString());
		labelEleicaoAtiva.setEnabled(false);
		labelEleicaoAtiva.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(labelEleicaoAtiva);
		labelEleicaoAtiva.setBounds(105, 10, 480, 25);

		//======== tabbedPane1 ========
		{
			tabbedPane1.setFont(new Font("Arial", Font.PLAIN, 14));

			//======== panel1 ========
			{

				// JFormDesigner evaluation mark
				panel1.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				panel1.setLayout(null);

				//======== scrollPane1 ========
				{

					//---- table1 ----
					table1.setModel(new DefaultTableModel(
						new Object[][] {
							{"teste1", "Presid\u00eancia da Rep\u00fablica", null},
							{"teste2", "Assembleia da Rep\u00fablica", null},
						},
						new String[] {
							"Data da elei\u00e7\u00e3o", "Tipo de elei\u00e7\u00e3o", "id"
						}
					));
					table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
					scrollPane1.setViewportView(table1);
				}
				panel1.add(scrollPane1);
				scrollPane1.setBounds(0, 0, 560, 355);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel1.getComponentCount(); i++) {
						Rectangle bounds = panel1.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel1.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel1.setMinimumSize(preferredSize);
					panel1.setPreferredSize(preferredSize);
				}
			}
			tabbedPane1.addTab("Elei\u00e7\u00f5es criadas", panel1);

			//======== panel2 ========
			{
				panel2.setLayout(null);

				//======== scrollPane2 ========
				{
					scrollPane2.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							scrollPane2FocusGained(e);
						}
					});

					//---- table2 ----
					table2.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"Data da elei\u00e7\u00e3o", "Tipo de elei\u00e7\u00e3o", "id"
						}
					));
					table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table2.getColumnModel().getColumn(2).setPreferredWidth(0);
					table2.getColumnModel().getColumn(2).setMinWidth(0);
					table2.getColumnModel().getColumn(2).setWidth(0);
					table2.getColumnModel().getColumn(2).setMaxWidth(0);
					scrollPane2.setViewportView(table2);
				}
				panel2.add(scrollPane2);
				scrollPane2.setBounds(0, 0, 560, 355);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel2.getComponentCount(); i++) {
						Rectangle bounds = panel2.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel2.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel2.setMinimumSize(preferredSize);
					panel2.setPreferredSize(preferredSize);
				}
			}
			tabbedPane1.addTab("Hist\u00f3rico de elei\u00e7\u00f5es", panel2);
		}
		contentPane.add(tabbedPane1);
		tabbedPane1.setBounds(10, 55, 565, 385);
		contentPane.add(separator1);
		separator1.setBounds(10, 45, 745, 5);

		//---- buttonVerResultados ----
		buttonVerResultados.setText("Ver resultados");
		buttonVerResultados.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonVerResultados.setEnabled(false);
		buttonVerResultados.addActionListener(e -> buttonVerResultadosActionPerformed(e));
		contentPane.add(buttonVerResultados);
		buttonVerResultados.setBounds(590, 85, 155, 25);

		//---- buttonIniciarEleicao ----
		buttonIniciarEleicao.setText("Iniciar elei\u00e7\u00e3o");
		buttonIniciarEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonIniciarEleicao.addActionListener(e -> buttonIniciarEleicaoActionPerformed(e));
		contentPane.add(buttonIniciarEleicao);
		buttonIniciarEleicao.setBounds(590, 125, 155, 25);

		//---- buttonCriarEleicao ----
		buttonCriarEleicao.setText("Criar elei\u00e7\u00e3o");
		buttonCriarEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCriarEleicao.addActionListener(e -> buttonCriarEleicaoActionPerformed(e));
		contentPane.add(buttonCriarEleicao);
		buttonCriarEleicao.setBounds(590, 165, 155, 25);

		//---- buttonGerirEleicao ----
		buttonGerirEleicao.setText("Gerir elei\u00e7\u00e3o");
		buttonGerirEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonGerirEleicao.addActionListener(e -> buttonGerirEleicaoActionPerformed(e));
		contentPane.add(buttonGerirEleicao);
		buttonGerirEleicao.setBounds(590, 205, 155, 25);

		//---- buttonInserirCaderno ----
		buttonInserirCaderno.setText("<html><center>Inserir caderno<br />de recenseamento<center></html>");
		buttonInserirCaderno.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonInserirCaderno.addActionListener(e -> buttonInserirCadernoActionPerformed(e));
		contentPane.add(buttonInserirCaderno);
		buttonInserirCaderno.setBounds(590, 245, 155, 85);

		//---- buttonSair ----
		buttonSair.setText("Sair");
		buttonSair.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonSair.addActionListener(e -> buttonSairActionPerformed(e));
		contentPane.add(buttonSair);
		buttonSair.setBounds(590, 415, 155, 25);

		//---- buttonTerminarEleicao ----
		buttonTerminarEleicao.setText("Terminar elei\u00e7\u00e3o");
		buttonTerminarEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonTerminarEleicao.addActionListener(e -> buttonTerminarEleicaoActionPerformed(e));
		contentPane.add(buttonTerminarEleicao);
		buttonTerminarEleicao.setBounds(590, 10, 155, 25);

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
		setSize(780, 490);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JLabel labelEleicaoAtiva;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JPanel panel2;
	private JScrollPane scrollPane2;
	private JTable table2;
	private JSeparator separator1;
	private JButton buttonVerResultados;
	private JButton buttonIniciarEleicao;
	private JButton buttonCriarEleicao;
	private JButton buttonGerirEleicao;
	private JButton buttonInserirCaderno;
	private JButton buttonSair;
	private JButton buttonTerminarEleicao;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
