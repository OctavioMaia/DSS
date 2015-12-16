/*
 * Created by JFormDesigner on Tue Dec 15 18:17:45 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.table.*;

/**
 * @author Octavio Maia
 */
public class GerirPR {
	public GerirPR() {
		initComponents();
		GerirPR.setVisible(true);
	}

	private void buttonProcurarActionPerformed(ActionEvent e) {
		dialog1.setVisible(true);
		int result = fileChooser1.showOpenDialog(fileChooser1);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser1.getSelectedFile();
		    pathImagem.setText(selectedFile.getAbsolutePath());
		    buttonAdicionarFoto.setEnabled(true);
		    dialog1.dispose();
		}else
			dialog1.dispose();
	}

	private void buttonSairActionPerformed(ActionEvent e) {
		GerirPR.dispose();
	}

	private void buttonAdicionarFotoActionPerformed(ActionEvent e) {
		//stuff
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		GerirPR = new JFrame();
		label1 = new JLabel();
		label2 = new JLabel();
		tableCandidatos = new JScrollPane();
		tableCandidatos2 = new JTable();
		imagem = new JLabel();
		eliminar = new JButton();
		label3 = new JLabel();
		label4 = new JLabel();
		nomeCandidato = new JTextField();
		buttonProcurar = new JButton();
		pathImagem = new JTextField();
		buttonAdicionarFoto = new JButton();
		buttonConfirmar = new JButton();
		buttonSair = new JButton();
		diasInicio = new JComboBox<>();
		diasFim = new JComboBox<>();
		mesInicio = new JComboBox<>();
		mesFim = new JComboBox<>();
		dialog1 = new JDialog();
		fileChooser1 = new JFileChooser();

		//======== GerirPR ========
		{
			GerirPR.setTitle("Gerir Elei\u00e7\u00e3o Presid\u00eancia da Rep\u00fablica");
			GerirPR.setResizable(false);
			Container GerirPRContentPane = GerirPR.getContentPane();
			GerirPRContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Data in\u00edcio da elei\u00e7\u00e3o:");
			label1.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(25, 25), label1.getPreferredSize()));

			//---- label2 ----
			label2.setText("Data fim da elei\u00e7\u00e3o:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label2);
			label2.setBounds(25, 55, 139, 17);

			//======== tableCandidatos ========
			{
				tableCandidatos.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableCandidatos2 ----
				tableCandidatos2.setFont(new Font("Arial", Font.PLAIN, 12));
				tableCandidatos2.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Nome"
					}
				) {
					boolean[] columnEditable = new boolean[] {
						false
					};
					@Override
					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return columnEditable[columnIndex];
					}
				});
				{
					TableColumnModel cm = tableCandidatos2.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableCandidatos.setViewportView(tableCandidatos2);
			}
			GerirPRContentPane.add(tableCandidatos);
			tableCandidatos.setBounds(25, 95, 265, 215);

			//---- imagem ----
			imagem.setIcon(new ImageIcon(getClass().getResource("/img/PS.png")));
			GerirPRContentPane.add(imagem);
			imagem.setBounds(360, 95, 165, 170);

			//---- eliminar ----
			eliminar.setText("Eliminar");
			eliminar.setFont(new Font("Arial", Font.PLAIN, 14));
			eliminar.setEnabled(false);
			GerirPRContentPane.add(eliminar);
			eliminar.setBounds(360, 285, 165, eliminar.getPreferredSize().height);

			//---- label3 ----
			label3.setText("Nome do candidato:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label3);
			label3.setBounds(25, 330, 139, 17);

			//---- label4 ----
			label4.setText("Foto");
			label4.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label4);
			label4.setBounds(25, 360, 35, 17);

			//---- nomeCandidato ----
			nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(nomeCandidato);
			nomeCandidato.setBounds(165, 325, 375, nomeCandidato.getPreferredSize().height);

			//---- buttonProcurar ----
			buttonProcurar.setText("Procurar");
			buttonProcurar.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonProcurar.addActionListener(e -> buttonProcurarActionPerformed(e));
			GerirPRContentPane.add(buttonProcurar);
			buttonProcurar.setBounds(65, 355, 95, buttonProcurar.getPreferredSize().height);

			//---- pathImagem ----
			pathImagem.setEditable(false);
			GerirPRContentPane.add(pathImagem);
			pathImagem.setBounds(165, 355, 375, 25);

			//---- buttonAdicionarFoto ----
			buttonAdicionarFoto.setText("Adicionar");
			buttonAdicionarFoto.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonAdicionarFoto.setEnabled(false);
			buttonAdicionarFoto.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
			GerirPRContentPane.add(buttonAdicionarFoto);
			buttonAdicionarFoto.setBounds(65, 385, 95, 25);

			//---- buttonConfirmar ----
			buttonConfirmar.setText("Confirmar");
			buttonConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(buttonConfirmar);
			buttonConfirmar.setBounds(305, 415, 105, 25);

			//---- buttonSair ----
			buttonSair.setText("Sair");
			buttonSair.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonSair.addActionListener(e -> buttonSairActionPerformed(e));
			GerirPRContentPane.add(buttonSair);
			buttonSair.setBounds(435, 415, 95, 25);

			//---- diasInicio ----
			diasInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			diasInicio.setModel(new DefaultComboBoxModel<>(new String[] {
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
				"22",
				"23",
				"24",
				"25",
				"26",
				"27",
				"28",
				"29",
				"30",
				"31"
			}));
			GerirPRContentPane.add(diasInicio);
			diasInicio.setBounds(170, 20, 40, 25);

			//---- diasFim ----
			diasFim.setFont(new Font("Arial", Font.PLAIN, 12));
			diasFim.setModel(new DefaultComboBoxModel<>(new String[] {
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
				"22",
				"23",
				"24",
				"25",
				"26",
				"27",
				"28",
				"29",
				"30",
				"31"
			}));
			GerirPRContentPane.add(diasFim);
			diasFim.setBounds(170, 50, 40, 25);

			//---- mesInicio ----
			mesInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			mesInicio.setModel(new DefaultComboBoxModel<>(new String[] {
				"Janeiro",
				"Fevereiro",
				"Mar\u00e7o",
				"Abril",
				"Maio",
				"Junho",
				"Julho",
				"Agosto",
				"Setembro",
				"Outubro",
				"Novembro",
				"Dezembro"
			}));
			GerirPRContentPane.add(mesInicio);
			mesInicio.setBounds(215, 20, 120, 25);

			//---- mesFim ----
			mesFim.setFont(new Font("Arial", Font.PLAIN, 12));
			mesFim.setModel(new DefaultComboBoxModel<>(new String[] {
				"Janeiro",
				"Fevereiro",
				"Mar\u00e7o",
				"Abril",
				"Maio",
				"Junho",
				"Julho",
				"Agosto",
				"Setembro",
				"Outubro",
				"Novembro",
				"Dezembro"
			}));
			GerirPRContentPane.add(mesFim);
			mesFim.setBounds(215, 50, 120, 25);

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
			GerirPR.setSize(570, 485);
			GerirPR.setLocationRelativeTo(null);
		}

		//======== dialog1 ========
		{
			dialog1.setResizable(false);
			dialog1.setFont(new Font("Arial", Font.PLAIN, 12));
			dialog1.setTitle("Gestor de Ficheiros");
			Container dialog1ContentPane = dialog1.getContentPane();
			dialog1ContentPane.setLayout(null);

			//---- fileChooser1 ----
			fileChooser1.setFont(new Font("Arial", Font.PLAIN, 11));
			dialog1ContentPane.add(fileChooser1);
			fileChooser1.setBounds(0, 0, 437, 315);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < dialog1ContentPane.getComponentCount(); i++) {
					Rectangle bounds = dialog1ContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = dialog1ContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				dialog1ContentPane.setMinimumSize(preferredSize);
				dialog1ContentPane.setPreferredSize(preferredSize);
			}
			dialog1.pack();
			dialog1.setLocationRelativeTo(dialog1.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Rui Freitas
	private JFrame GerirPR;
	private JLabel label1;
	private JLabel label2;
	private JScrollPane tableCandidatos;
	private JTable tableCandidatos2;
	private JLabel imagem;
	private JButton eliminar;
	private JLabel label3;
	private JLabel label4;
	private JTextField nomeCandidato;
	private JButton buttonProcurar;
	private JTextField pathImagem;
	private JButton buttonAdicionarFoto;
	private JButton buttonConfirmar;
	private JButton buttonSair;
	private JComboBox<String> diasInicio;
	private JComboBox<String> diasFim;
	private JComboBox<String> mesInicio;
	private JComboBox<String> mesFim;
	private JDialog dialog1;
	private JFileChooser fileChooser1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
