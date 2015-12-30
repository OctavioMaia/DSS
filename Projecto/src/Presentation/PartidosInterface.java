/*
 * Created by JFormDesigner on Tue Dec 29 09:20:15 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import Business.Lista;
import Business.Partido;
import Business.SGE;
import Exception.ExceptionPartidoExiste;
import Exception.ExceptionPartidoNaoExiste;

/**
 * @author Octavio Maia
 */
public class PartidosInterface extends JFrame {
	
	private SGE sge;
	
	public PartidosInterface(SGE s) {
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
	
	private void povoarTabela() {
		Set<Partido> set = sge.getPartidos();
		Object[][] data = new Object[set.size()][];
		int i = 0;

		if (table1.getRowCount() > 0) {
			for (int conta = table1.getRowCount() - 1; conta > -1; conta--) {
				((DefaultTableModel) table1.getModel()).removeRow(conta);
			}
		}

		for (Partido el : set) {
			data[i] = el.toTable();

			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void buttonEliminarActionPerformed(ActionEvent e) {
		try {
			sge.eliminarPartido((Partido) table1.getValueAt(table1.getSelectedRow(), 2));
			povoarTabela();
		} catch (ExceptionPartidoNaoExiste e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	private void buttonAdicionarActionPerformed(ActionEvent e) {
		try {
			sge.addPartido(new Partido(0, sigla.getText(), nomeCandidato.getText(), pathImagem.getText()));
			povoarTabela();
		} catch (ExceptionPartidoExiste e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		button1 = new JButton();
		sigla = new JTextField();
		nomeCandidato = new JTextField();
		pathImagem = new JTextField();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		buttonEliminar = new JButton();
		buttonAdicionar = new JButton();
		fileChooser1 = new JFileChooser();

		//======== this ========
		setTitle("Gest\u00e3o de partidos");
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

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "Sigla", null
				}
			));
			table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table1.getColumnModel().getColumn(2).setPreferredWidth(0);
			table1.getColumnModel().getColumn(2).setMinWidth(0);
			table1.getColumnModel().getColumn(2).setWidth(0);
			table1.getColumnModel().getColumn(2).setMaxWidth(0);
			povoarTabela();
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(20, 115, 470, 200);

		//---- buttonEliminar ----
		buttonEliminar.setText("Eliminar partido");
		buttonEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonEliminar.addActionListener(e -> buttonEliminarActionPerformed(e));
		contentPane.add(buttonEliminar);
		buttonEliminar.setBounds(140, 325, 155, 25);

		//---- buttonAdicionar ----
		buttonAdicionar.setText("Adicionar partido");
		buttonAdicionar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAdicionar.addActionListener(e -> buttonAdicionarActionPerformed(e));
		contentPane.add(buttonAdicionar);
		buttonAdicionar.setBounds(300, 325, 190, 25);

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
		setSize(525, 400);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Rui Freitas
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JButton button1;
	private JTextField sigla;
	private JTextField nomeCandidato;
	private JTextField pathImagem;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton buttonEliminar;
	private JButton buttonAdicionar;
	private JFileChooser fileChooser1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
