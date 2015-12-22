/*
 * Created by JFormDesigner on Tue Dec 22 14:48:43 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.*;

import Business.Eleitor;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class CadernoRecenseamento extends JFrame {
	
	private SGE sge;
	private String path;
	
	public CadernoRecenseamento(SGE s) {
		sge=s;
		initComponents();
		this.setVisible(true);
	}

	private void buttonProcurarActionPerformed(ActionEvent e) {
		dialogGestorFicheiros.setVisible(true);
		int result = fileChooser1.showOpenDialog(fileChooser1);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser1.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    pathImagem.setText(path);
		    dialogGestorFicheiros.dispose();
		}else
			dialogGestorFicheiros.dispose();
	}

	private void buttonAdicionarFotoActionPerformed(ActionEvent e) {
		Map<Integer, List<Eleitor>> map = sge.lerCadernoRecenciamento(path);
	}

	private void buttonApagarInfoActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		label1 = new JLabel();
		buttonProcurar = new JButton();
		pathImagem = new JTextField();
		buttonAdicionarCandidato = new JButton();
		separator1 = new JSeparator();
		label2 = new JLabel();
		comboBox1 = new JComboBox<>();
		label3 = new JLabel();
		numeroEleitores = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		buttonConfirmar = new JButton();
		buttonCancelar = new JButton();
		dialogGestorFicheiros = new JDialog();
		fileChooser1 = new JFileChooser();

		//======== this ========
		setTitle("Caderno de recenseamento");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Localiza\u00e7\u00e3o de ficheiro:");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(10, 25, label1.getPreferredSize().width, 25);

		//---- buttonProcurar ----
		buttonProcurar.setText("Procurar");
		buttonProcurar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonProcurar.addActionListener(e -> buttonProcurarActionPerformed(e));
		contentPane.add(buttonProcurar);
		buttonProcurar.setBounds(345, 65, 95, buttonProcurar.getPreferredSize().height);

		//---- pathImagem ----
		pathImagem.setEditable(false);
		contentPane.add(pathImagem);
		pathImagem.setBounds(170, 25, 375, 25);

		//---- buttonAdicionarCandidato ----
		buttonAdicionarCandidato.setText("Adicionar");
		buttonAdicionarCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAdicionarCandidato.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
		contentPane.add(buttonAdicionarCandidato);
		buttonAdicionarCandidato.setBounds(450, 65, 95, buttonAdicionarCandidato.getPreferredSize().height);
		contentPane.add(separator1);
		separator1.setBounds(10, 100, 535, 5);

		//---- label2 ----
		label2.setText("C\u00edrculo n\u00ba:");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(10, 125, 75, 25);

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
		contentPane.add(comboBox1);
		comboBox1.setBounds(95, 125, 75, 25);

		//---- label3 ----
		label3.setText("Total de eleitores:");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(245, 125, 120, 25);

		//---- numeroEleitores ----
		numeroEleitores.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(numeroEleitores);
		numeroEleitores.setBounds(370, 125, 120, 25);

		//======== scrollPane1 ========
		{

			//---- table1 ----
			table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table1.setRowSelectionAllowed(false);
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "B.I. / C.C."
				}
			) {
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false
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
				cm.getColumn(1).setResizable(false);
			}
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(10, 170, 535, 255);

		//---- buttonConfirmar ----
		buttonConfirmar.setText("Confirmar");
		buttonConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(buttonConfirmar);
		buttonConfirmar.setBounds(320, 435, 120, 25);

		//---- buttonCancelar ----
		buttonCancelar.setText("Cancelar");
		buttonCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCancelar.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
		contentPane.add(buttonCancelar);
		buttonCancelar.setBounds(450, 435, 95, 25);

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
		setSize(575, 505);
		setLocationRelativeTo(null);

		//======== dialogGestorFicheiros ========
		{
			dialogGestorFicheiros.setResizable(false);
			dialogGestorFicheiros.setFont(new Font("Arial", Font.PLAIN, 12));
			dialogGestorFicheiros.setTitle("Gestor de Ficheiros");
			Container dialogGestorFicheirosContentPane = dialogGestorFicheiros.getContentPane();
			dialogGestorFicheirosContentPane.setLayout(null);

			//---- fileChooser1 ----
			fileChooser1.setFont(new Font("Arial", Font.PLAIN, 11));
			dialogGestorFicheirosContentPane.add(fileChooser1);
			fileChooser1.setBounds(0, 0, 437, 315);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < dialogGestorFicheirosContentPane.getComponentCount(); i++) {
					Rectangle bounds = dialogGestorFicheirosContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = dialogGestorFicheirosContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				dialogGestorFicheirosContentPane.setMinimumSize(preferredSize);
				dialogGestorFicheirosContentPane.setPreferredSize(preferredSize);
			}
			dialogGestorFicheiros.pack();
			dialogGestorFicheiros.setLocationRelativeTo(dialogGestorFicheiros.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JButton buttonProcurar;
	private JTextField pathImagem;
	private JButton buttonAdicionarCandidato;
	private JSeparator separator1;
	private JLabel label2;
	private JComboBox<String> comboBox1;
	private JLabel label3;
	private JLabel numeroEleitores;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton buttonConfirmar;
	private JButton buttonCancelar;
	private JDialog dialogGestorFicheiros;
	private JFileChooser fileChooser1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
