/*
 * Created by JFormDesigner on Tue Dec 15 18:17:45 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.*;

import Business.Eleicao;
import Business.EleicaoPR;
import com.toedter.calendar.*;

/**
 * @author Octavio Maia
 */
public class GerirPR {
	
	public GerirPR(EleicaoPR eleicao) {
		initComponents(eleicao);
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
		//voltar atras?
	}

	private void buttonAdicionarFotoActionPerformed(ActionEvent e) {
		// TODO parse data inicio eleicao
		/*int dia,mes,ano;
		
		dia = Integer.parseInt((String) diasInicio.getSelectedItem());
		ano = Integer.parseInt((String) anoInicio.getSelectedItem());
		
		mes = mesInicio.getSelectedIndex();
		
		
		GregorianCalendar data = new GregorianCalendar(ano,mes,dia);
		System.out.println("Ano " + data.get(Calendar.YEAR));
		System.out.println("Mes " + data.get(Calendar.MONTH));
		System.out.println("Dia " + data.get(Calendar.DAY_OF_MONTH));
		*/
		// TODO parse foto + filepath / nome candidato
		// TODO apos parse adicionar a tabela
	}

	private void button1ActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(true);
	}

	private void buttonConfirmarDataActionPerformed(ActionEvent e) {
		Calendar cal = calendar1.getCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH)+1;
		int ano = cal.get(Calendar.YEAR);
		System.out.println(dia + "/" + mes + "/"+ ano);
		dialogoCalendario.setVisible(false);
	}

	private void buttonCancelarDataActionPerformed(ActionEvent e) {
		dialogoCalendario.setVisible(false);
	}
	
	private void initComponents(Eleicao eleicao) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		GerirPR = new JFrame();
		label1 = new JLabel();
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
		buttonData = new JButton();
		data = new JLabel();
		dialog1 = new JDialog();
		fileChooser1 = new JFileChooser();
		dialogoCalendario = new JDialog();
		calendar1 = new JCalendar();
		buttonConfirmarData = new JButton();
		buttonCancelarData = new JButton();

		//======== GerirPR ========
		{
			GerirPR.setTitle("Gerir Elei\u00e7\u00e3o Presid\u00eancia da Rep\u00fablica");
			GerirPR.setResizable(false);
			GerirPR.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container GerirPRContentPane = GerirPR.getContentPane();
			GerirPRContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Data in\u00edcio da elei\u00e7\u00e3o:");
			label1.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(25, 25), label1.getPreferredSize()));

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
			tableCandidatos.setBounds(25, 60, 265, 215);

			//---- imagem ----
			imagem.setIcon(new ImageIcon(getClass().getResource("/PS.png")));
			GerirPRContentPane.add(imagem);
			imagem.setBounds(360, 60, 165, 170);

			//---- eliminar ----
			eliminar.setText("Eliminar");
			eliminar.setFont(new Font("Arial", Font.PLAIN, 14));
			eliminar.setEnabled(false);
			GerirPRContentPane.add(eliminar);
			eliminar.setBounds(360, 250, 165, eliminar.getPreferredSize().height);

			//---- label3 ----
			label3.setText("Nome do candidato:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label3);
			label3.setBounds(25, 290, 139, 22);

			//---- label4 ----
			label4.setText("Foto");
			label4.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(label4);
			label4.setBounds(25, 320, 35, 22);

			//---- nomeCandidato ----
			nomeCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(nomeCandidato);
			nomeCandidato.setBounds(165, 290, 375, nomeCandidato.getPreferredSize().height);

			//---- buttonProcurar ----
			buttonProcurar.setText("Procurar");
			buttonProcurar.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonProcurar.addActionListener(e -> buttonProcurarActionPerformed(e));
			GerirPRContentPane.add(buttonProcurar);
			buttonProcurar.setBounds(65, 320, 95, buttonProcurar.getPreferredSize().height);

			//---- pathImagem ----
			pathImagem.setEditable(false);
			GerirPRContentPane.add(pathImagem);
			pathImagem.setBounds(165, 320, 375, 25);

			//---- buttonAdicionarFoto ----
			buttonAdicionarFoto.setText("Adicionar");
			buttonAdicionarFoto.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonAdicionarFoto.setEnabled(false);
			buttonAdicionarFoto.addActionListener(e -> buttonAdicionarFotoActionPerformed(e));
			GerirPRContentPane.add(buttonAdicionarFoto);
			buttonAdicionarFoto.setBounds(65, 350, 95, 25);

			//---- buttonConfirmar ----
			buttonConfirmar.setText("Confirmar");
			buttonConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(buttonConfirmar);
			buttonConfirmar.setBounds(305, 380, 105, 25);

			//---- buttonSair ----
			buttonSair.setText("Sair");
			buttonSair.setFont(new Font("Arial", Font.PLAIN, 14));
			buttonSair.addActionListener(e -> buttonSairActionPerformed(e));
			GerirPRContentPane.add(buttonSair);
			buttonSair.setBounds(435, 380, 95, 25);

			//---- buttonData ----
			buttonData.setText("Alterar");
			buttonData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonData.addActionListener(e -> button1ActionPerformed(e));
			GerirPRContentPane.add(buttonData);
			buttonData.setBounds(305, 20, 80, 25);

			//---- data ----
			data.setText("dd/mm/aa");
			data.setFont(new Font("Arial", Font.PLAIN, 14));
			GerirPRContentPane.add(data);
			data.setBounds(170, 25, 115, 17);

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
			GerirPR.setSize(570, 450);
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

		//======== dialogoCalendario ========
		{
			dialogoCalendario.setTitle("Calendario");
			dialogoCalendario.setResizable(false);
			dialogoCalendario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container dialogoCalendarioContentPane = dialogoCalendario.getContentPane();
			dialogoCalendarioContentPane.setLayout(null);
			dialogoCalendarioContentPane.add(calendar1);
			calendar1.setBounds(0, 0, 210, 155);

			//---- buttonConfirmarData ----
			buttonConfirmarData.setText("Confirmar");
			buttonConfirmarData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonConfirmarData.addActionListener(e -> buttonConfirmarDataActionPerformed(e));
			dialogoCalendarioContentPane.add(buttonConfirmarData);
			buttonConfirmarData.setBounds(5, 155, 90, 28);

			//---- buttonCancelarData ----
			buttonCancelarData.setText("Cancelar");
			buttonCancelarData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonCancelarData.addActionListener(e -> buttonCancelarDataActionPerformed(e));
			dialogoCalendarioContentPane.add(buttonCancelarData);
			buttonCancelarData.setBounds(110, 155, 90, 28);

			dialogoCalendarioContentPane.setPreferredSize(new Dimension(225, 235));
			dialogoCalendario.setSize(225, 235);
			dialogoCalendario.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame GerirPR;
	private JLabel label1;
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
	private JButton buttonData;
	private JLabel data;
	private JDialog dialog1;
	private JFileChooser fileChooser1;
	private JDialog dialogoCalendario;
	private JCalendar calendar1;
	private JButton buttonConfirmarData;
	private JButton buttonCancelarData;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
