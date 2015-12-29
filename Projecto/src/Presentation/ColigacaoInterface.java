/*
 * Created by JFormDesigner on Tue Dec 29 09:37:33 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import Business.Coligacao;
import Business.Partido;
import Business.SGE;
import Exception.ExceptionColigacaoExiste;
import Exception.ExceptionPartidoExiste;
import Exception.ExceptionPartidoNaoExiste;
import com.jgoodies.forms.factories.*;

/**
 * @author Octavio Maia
 */
public class ColigacaoInterface extends JFrame {
	
	private SGE sge;
	
	public ColigacaoInterface(SGE s) {
		sge = s;
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

	private void buttonEliminarActionPerformed(ActionEvent e) {
		try {
			sge.eliminarPartido((Partido) tableColigacao.getValueAt(tableColigacao.getSelectedRow(), 2));
			povoarTabelaColigacao();
		} catch (ExceptionPartidoNaoExiste e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	private void buttonAdicionarActionPerformed(ActionEvent e) {
		Set<Partido> partidos = new HashSet<>();
		int[] selecionadas;
		
		if (tablePartidos.getSelectedRowCount() >= 2) {
			selecionadas = tablePartidos.getSelectedRows();
		
			for(Integer i : selecionadas){
				partidos.add((Partido) tablePartidos.getValueAt(i, 2));
			}
		
			try {
				sge.addColigacao(new Business.Coligacao(0, sigla.getText(), nomeCandidato.getText(), pathImagem.getText(), partidos));
				povoarTabelaColigacao();
			} catch (ExceptionColigacaoExiste e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "Seleciona no minimo dois partidos para criar uma coligação");
		}
	}
	
	private void povoarTabelaPartidos() {
		Set<Partido> set = sge.getPartidos();
		Object[][] data = new Object[set.size()][];
		int i = 0;

		if (tablePartidos.getRowCount() > 0) {
			for (int conta = tablePartidos.getRowCount() - 1; conta > -1; conta--) {
				((DefaultTableModel) tablePartidos.getModel()).removeRow(conta);
			}
		}

		for (Partido el : set) {
			data[i] = el.toTable();

			DefaultTableModel model = (DefaultTableModel) tablePartidos.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void povoarTabelaColigacao() {
		Set<Coligacao> set = sge.getColigacoes();
		Object[][] data = new Object[set.size()][];
		int i = 0;

		if (tableColigacao.getRowCount() > 0) {
			for (int conta = tableColigacao.getRowCount() - 1; conta > -1; conta--) {
				((DefaultTableModel) tableColigacao.getModel()).removeRow(conta);
			}
		}

		for (Coligacao el : set) {
			data[i] = el.toTable();

			DefaultTableModel model = (DefaultTableModel) tableColigacao.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		fileChooser1 = new JFileChooser();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		button1 = new JButton();
		sigla = new JTextField();
		nomeCandidato = new JTextField();
		pathImagem = new JTextField();
		scrollPane1 = new JScrollPane();
		tableColigacao = new JTable();
		buttonEliminar = new JButton();
		buttonAdicionar = new JButton();
		scrollPane2 = new JScrollPane();
		tablePartidos = new JTable();
		separator1 = compFactory.createSeparator("Coliga\u00e7\u00f5es", SwingConstants.CENTER);
		separator2 = compFactory.createSeparator("Partidos", SwingConstants.CENTER);

		//======== this ========
		setTitle("Gest\u00e3o de coliga\u00e7\u00f5es");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Nome:");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(20, 15, 84, 25);

		//---- label2 ----
		label2.setText("Sigla:");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(20, 45, 84, 25);

		//---- label3 ----
		label3.setText("S\u00edmbolo:");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(20, 75, 84, 25);

		//---- button1 ----
		button1.setText("Procurar");
		button1.setFont(new Font("Arial", Font.PLAIN, 14));
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(360, 75, 130, button1.getPreferredSize().height);

		//---- sigla ----
		sigla.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(sigla);
		sigla.setBounds(100, 45, 390, sigla.getPreferredSize().height);

		//---- nomeCandidato ----
		nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(nomeCandidato);
		nomeCandidato.setBounds(100, 15, 390, nomeCandidato.getPreferredSize().height);

		//---- pathImagem ----
		pathImagem.setFont(new Font("Arial", Font.PLAIN, 14));
		pathImagem.setEditable(false);
		pathImagem.setEnabled(false);
		contentPane.add(pathImagem);
		pathImagem.setBounds(100, 75, 255, 25);

		//======== scrollPane1 ========
		{

			//---- tableColigacao ----
			tableColigacao.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "Sigla", null
				}
			));
			tableColigacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableColigacao.getColumnModel().getColumn(2).setPreferredWidth(0);
			tableColigacao.getColumnModel().getColumn(2).setMinWidth(0);
			tableColigacao.getColumnModel().getColumn(2).setWidth(0);
			tableColigacao.getColumnModel().getColumn(2).setMaxWidth(0);
			povoarTabelaPartidos();
			povoarTabelaColigacao();
			scrollPane1.setViewportView(tableColigacao);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(20, 315, 470, 200);

		//---- buttonEliminar ----
		buttonEliminar.setText("Eliminar coliga\u00e7\u00e3o");
		buttonEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonEliminar.addActionListener(e -> buttonEliminarActionPerformed(e));
		contentPane.add(buttonEliminar);
		buttonEliminar.setBounds(140, 525, 155, 25);

		//---- buttonAdicionar ----
		buttonAdicionar.setText("Adicionar coliga\u00e7\u00e3o");
		buttonAdicionar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAdicionar.addActionListener(e -> buttonAdicionarActionPerformed(e));
		contentPane.add(buttonAdicionar);
		buttonAdicionar.setBounds(305, 525, 185, 25);

		//======== scrollPane2 ========
		{

			//---- tablePartidos ----
			tablePartidos.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "Sigla", null
				}
			));
			tablePartidos.getColumnModel().getColumn(2).setPreferredWidth(0);
			tablePartidos.getColumnModel().getColumn(2).setMinWidth(0);
			tablePartidos.getColumnModel().getColumn(2).setWidth(0);
			tablePartidos.getColumnModel().getColumn(2).setMaxWidth(0);
			povoarTabelaPartidos();
			povoarTabelaColigacao();
			scrollPane2.setViewportView(tablePartidos);
		}
		contentPane.add(scrollPane2);
		scrollPane2.setBounds(20, 130, 470, 160);
		contentPane.add(separator1);
		separator1.setBounds(20, 295, 465, separator1.getPreferredSize().height);
		contentPane.add(separator2);
		separator2.setBounds(20, 110, 465, 14);

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
		setSize(525, 600);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFileChooser fileChooser1;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JButton button1;
	private JTextField sigla;
	private JTextField nomeCandidato;
	private JTextField pathImagem;
	private JScrollPane scrollPane1;
	private JTable tableColigacao;
	private JButton buttonEliminar;
	private JButton buttonAdicionar;
	private JScrollPane scrollPane2;
	private JTable tablePartidos;
	private JComponent separator1;
	private JComponent separator2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
