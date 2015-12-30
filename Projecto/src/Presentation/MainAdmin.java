/*
 * Created by JFormDesigner on Wed Dec 23 20:01:18 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import Business.Eleicao;
import Business.EleicaoAR;
import Business.EleicaoPR;
import Business.Eleitor;
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
		new Login(sge);
	}

	private void buttonIniciarEleicaoActionPerformed(ActionEvent e) {
		try {
			sge.iniciarEleicao((Eleicao) table1.getValueAt(table1.getSelectedRow(), 2));
			labelEleicaoAtiva.setText(sge.eleicaoAtivaString());
		} catch (ExceptionEleicaoAtiva e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionIniciarEleicao e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
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
			if(table1.getValueAt(table1.getSelectedRow(), 2).getClass().getSimpleName().equals("EleicaoAR")){
				new GerirAR(sge, (Eleicao) table1.getValueAt(table1.getSelectedRow(), 2));
			}else{
				new GerirPR(sge, (EleicaoPR) table1.getValueAt(table1.getSelectedRow(), 2));
			}
		}
	}

	private void buttonVerResultadosActionPerformed(ActionEvent e) {
		if(panel2.isShowing()){
			if(table2.getValueAt(table2.getSelectedRow(), 2).getClass().getSimpleName().equals("EleicaoAR")){
				new ResultadosAR(sge, (EleicaoAR) table2.getValueAt(table2.getSelectedRow(), 2));
			}else{
				new ResultadosPR(sge, (EleicaoPR) table2.getValueAt(table2.getSelectedRow(), 2));
			}
		}
	}

	
	
	private void iniarSegundaVolta(Eleicao el1){
		if(el1!=null){
			if(el1.getClass().getSimpleName().equals("EleicaoPR")){
				if(((EleicaoPR)el1).isVolta2() && !((EleicaoPR)el1).isPermitirVotar()){
					buttonIniciarSegundaVolta.setEnabled(true);
				}
			}
		}
		
	}
	
	
	private void buttonTerminarEleicaoActionPerformed(ActionEvent e) {
		try {
			if(sge.eleicaoAtiva()!=null){
				if(sge.eleicaoAtiva().getClass().getSimpleName().equals("EleicaoPR")) 
					labelEleicaoAtiva.setText(sge.eleicaoAtivaString() + " (2ª volta)");
				else 
					labelEleicaoAtiva.setText(sge.eleicaoAtivaString());
			}
			Eleicao ativaEL = sge.eleicaoAtiva();
			sge.terminarEleicao(ativaEL);
			iniarSegundaVolta(ativaEL);
			if(sge.eleicaoAtiva()==null) labelEleicaoAtiva.setText("");
		} catch (ExceptionEleicaoAtiva e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionTerminarEleicao e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	private void table1FocusGained(FocusEvent e) {
		if(panel1.isShowing()){
			buttonVerResultados.setEnabled(false);
			buttonGerirEleicao.setEnabled(true);
			buttonIniciarEleicao.setEnabled(true);
		}
	}

	private void table2FocusGained(FocusEvent e) {
		if(panel2.isShowing()){
			buttonGerirEleicao.setEnabled(false);
			buttonIniciarEleicao.setEnabled(false);
			for (int i = 0; i < table2.getRowCount(); i++) {
				if(table2.isRowSelected(i)){
					buttonVerResultados.setEnabled(true);
					buttonGerirEleicao.setEnabled(false);
					buttonIniciarEleicao.setEnabled(false);
				}
			}
		}
	}
	
	public void povoarTabelaCriadas() {
		Set<Eleicao> criadas = sge.getEleicoesCriadas();
		Object[][] data = new Object[criadas.size()][]; 
		int i=0;
		
		if (table1.getRowCount() > 0) {
            for (int conta = table1.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) table1.getModel()).removeRow(conta);;
            }
        }
		
		for(Eleicao el : criadas){	
			data[i] = el.toTable();
			
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void povoarTabelaHistorico(){
		Set<Eleicao> historico = sge.getEleicoesTerminadas();
		Object[][] data = new Object[historico.size()][]; 
		int i=0;
		
		if (table2.getRowCount() > 0) {
            for (int conta = table2.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) table2.getModel()).removeRow(conta);
            }
        }
		
		for(Eleicao el : historico){	
			data[i] = el.toTable();
			
			DefaultTableModel model = (DefaultTableModel) table2.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void button1ActionPerformed(ActionEvent e) {
		if(panel1.isShowing())
			povoarTabelaCriadas();
		else 
			povoarTabelaHistorico();
	}

	private void buttonGerirPartidosActionPerformed(ActionEvent e) {
		new PartidosInterface(sge);
	}

	private void buttonGerirPartidos2ActionPerformed(ActionEvent e) {
		new ColigacaoInterface(sge);
	}

	private void buttonIniciarSegundaVoltaActionPerformed(ActionEvent e) {
		try {
			sge.iniciarEleicao(sge.eleicaoAtiva());
			labelEleicaoAtiva.setText(sge.eleicaoAtivaString()+" 2º volta");
			buttonIniciarSegundaVolta.setEnabled(false);
		} catch (ExceptionEleicaoAtiva e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (ExceptionIniciarEleicao e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}	
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
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
		buttonGerirEleicao = new JButton();
		buttonInserirCaderno = new JButton();
		buttonSair = new JButton();
		buttonTerminarEleicao = new JButton();
		buttonCriarEleicao = new JButton();
		button1 = new JButton();
		buttonGerirPartidos = new JButton();
		buttonGerirPartidos2 = new JButton();
		buttonIniciarSegundaVolta = new JButton();

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
		labelEleicaoAtiva.setEnabled(false);
		labelEleicaoAtiva.setFont(new Font("Arial", Font.PLAIN, 14));
		labelEleicaoAtiva.setText(sge.eleicaoAtivaString());
		contentPane.add(labelEleicaoAtiva);
		labelEleicaoAtiva.setBounds(105, 10, 305, 25);

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
					povoarTabelaCriadas();
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
			tabbedPane1.addTab("Elei\u00e7\u00f5es n\u00e3o iniciadas", panel1);

			//======== panel2 ========
			{
				panel2.setLayout(null);

				//======== scrollPane2 ========
				{

					//---- table2 ----
					table2.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"Data da elei\u00e7\u00e3o", "Tipo de elei\u00e7\u00e3o", "id"
						}
					));
					table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table2.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							table2FocusGained(e);
						}
					});
					table2.getColumnModel().getColumn(2).setPreferredWidth(0);
					table2.getColumnModel().getColumn(2).setMinWidth(0);
					table2.getColumnModel().getColumn(2).setWidth(0);
					table2.getColumnModel().getColumn(2).setMaxWidth(0);
					povoarTabelaHistorico();
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
		buttonVerResultados.setBounds(590, 345, 155, 25);

		//---- buttonIniciarEleicao ----
		buttonIniciarEleicao.setText("Iniciar elei\u00e7\u00e3o");
		buttonIniciarEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonIniciarEleicao.setEnabled(false);
		buttonIniciarEleicao.addActionListener(e -> buttonIniciarEleicaoActionPerformed(e));
		contentPane.add(buttonIniciarEleicao);
		buttonIniciarEleicao.setBounds(590, 275, 155, 25);

		//---- buttonGerirEleicao ----
		buttonGerirEleicao.setText("Gerir elei\u00e7\u00e3o");
		buttonGerirEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonGerirEleicao.setEnabled(false);
		buttonGerirEleicao.addActionListener(e -> buttonGerirEleicaoActionPerformed(e));
		contentPane.add(buttonGerirEleicao);
		buttonGerirEleicao.setBounds(590, 310, 155, 25);

		//---- buttonInserirCaderno ----
		buttonInserirCaderno.setText("<html><center>Inserir caderno<br />de recenseamento<center></html>");
		buttonInserirCaderno.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonInserirCaderno.addActionListener(e -> buttonInserirCadernoActionPerformed(e));
		contentPane.add(buttonInserirCaderno);
		buttonInserirCaderno.setBounds(590, 70, 155, 85);

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

		//---- buttonCriarEleicao ----
		buttonCriarEleicao.setText("Criar elei\u00e7\u00e3o");
		buttonCriarEleicao.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCriarEleicao.addActionListener(e -> buttonCriarEleicaoActionPerformed(e));
		contentPane.add(buttonCriarEleicao);
		buttonCriarEleicao.setBounds(590, 235, 155, buttonCriarEleicao.getPreferredSize().height);

		//---- button1 ----
		button1.setText("Atualizar tabelas");
		button1.setFont(new Font("Arial", Font.PLAIN, 14));
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(590, 380, 155, 25);

		//---- buttonGerirPartidos ----
		buttonGerirPartidos.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonGerirPartidos.setText("Gest\u00e3o partidos");
		buttonGerirPartidos.addActionListener(e -> buttonGerirPartidosActionPerformed(e));
		contentPane.add(buttonGerirPartidos);
		buttonGerirPartidos.setBounds(590, 165, 155, 25);

		//---- buttonGerirPartidos2 ----
		buttonGerirPartidos2.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonGerirPartidos2.setText("Gest\u00e3o coliga\u00e7\u00f5es");
		buttonGerirPartidos2.addActionListener(e -> buttonGerirPartidos2ActionPerformed(e));
		contentPane.add(buttonGerirPartidos2);
		buttonGerirPartidos2.setBounds(590, 200, 155, 25);

		//---- buttonIniciarSegundaVolta ----
		buttonIniciarSegundaVolta.setText("Iniciar 2\u00ba Volta");
		buttonIniciarSegundaVolta.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonIniciarSegundaVolta.setEnabled(false);
		buttonIniciarSegundaVolta.addActionListener(e -> buttonIniciarSegundaVoltaActionPerformed(e));
		iniarSegundaVolta(sge.eleicaoAtiva());
		contentPane.add(buttonIniciarSegundaVolta);
		buttonIniciarSegundaVolta.setBounds(425, 10, 155, 25);

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
	// Generated using JFormDesigner Evaluation license - Rui Freitas
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
	private JButton buttonGerirEleicao;
	private JButton buttonInserirCaderno;
	private JButton buttonSair;
	private JButton buttonTerminarEleicao;
	private JButton buttonCriarEleicao;
	private JButton button1;
	private JButton buttonGerirPartidos;
	private JButton buttonGerirPartidos2;
	private JButton buttonIniciarSegundaVolta;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
