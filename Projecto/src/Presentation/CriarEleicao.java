/*
 * Created by JFormDesigner on Tue Dec 22 18:11:49 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import com.toedter.calendar.*;

import Business.EleicaoPR;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class CriarEleicao extends JFrame {
	
	private SGE sge;
	private GregorianCalendar dataInicio;
	
	public CriarEleicao(SGE s) {
		sge=s;
		initComponents();
		this.setVisible(true);
	}

	private void buttonDataActionPerformed(ActionEvent e) {
		dialogoCalendar.setVisible(true);
	}

	private void buttonConfirmarDataActionPerformed(ActionEvent e) {
		Calendar cal = calendar1.getCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH)+1;
		int ano = cal.get(Calendar.YEAR);
		data.setText(dia+"/"+mes+"/"+ano);
		
		dataInicio = new GregorianCalendar(ano, mes, dia);
		//TODO implementar dataInicio
		
		dialogoCalendar.setVisible(false);
		buttonAvancar.setEnabled(true);
	}

	private void buttonCancelarDataActionPerformed(ActionEvent e) {
		dialogoCalendar.setVisible(false);
	}

	private void buttonCancelarActionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void buttonAvancarActionPerformed(ActionEvent e) {
		if(comboBox1.getSelectedIndex()==0){
			GerirAR gui = new GerirAR();
		}else{
			GerirPR gui = new GerirPR(sge, null); //TODO temos que substituir null por getCirculos
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		label1 = new JLabel();
		comboBox1 = new JComboBox<>();
		label2 = new JLabel();
		data = new JLabel();
		buttonDataNascimento = new JButton();
		buttonCancelar = new JButton();
		buttonAvancar = new JButton();
		separator1 = new JSeparator();
		dialogoCalendar = new JDialog();
		calendar1 = new JCalendar();
		buttonConfirmarDataInicio = new JButton();
		buttonCancelarData = new JButton();

		//======== this ========
		setTitle("Criar Elei\u00e7\u00e3o");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Tipo de elei\u00e7\u00e3o:");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(10, 10, label1.getPreferredSize().width, 25);

		//---- comboBox1 ----
		comboBox1.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
			"Assembleia da Rep\u00fablica",
			"Presid\u00eancia da Rep\u00fablica"
		}));
		contentPane.add(comboBox1);
		comboBox1.setBounds(125, 10, 215, 25);

		//---- label2 ----
		label2.setText("Data:");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(10, 50, 101, 25);

		//---- data ----
		data.setText("dd/mm/aa");
		data.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(data);
		data.setBounds(125, 50, 115, 25);

		//---- buttonDataNascimento ----
		buttonDataNascimento.setText("Alterar");
		buttonDataNascimento.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonDataNascimento.addActionListener(e -> buttonDataActionPerformed(e));
		contentPane.add(buttonDataNascimento);
		buttonDataNascimento.setBounds(260, 50, 80, 25);

		//---- buttonCancelar ----
		buttonCancelar.setText("Cancelar");
		buttonCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCancelar.addActionListener(e -> buttonCancelarActionPerformed(e));
		contentPane.add(buttonCancelar);
		buttonCancelar.setBounds(100, 90, 115, 25);

		//---- buttonAvancar ----
		buttonAvancar.setText("Avancar");
		buttonAvancar.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonAvancar.setEnabled(false);
		buttonAvancar.addActionListener(e -> buttonAvancarActionPerformed(e));
		contentPane.add(buttonAvancar);
		buttonAvancar.setBounds(230, 90, 110, buttonAvancar.getPreferredSize().height);
		contentPane.add(separator1);
		separator1.setBounds(5, 80, 340, 5);

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
		setSize(365, 160);
		setLocationRelativeTo(null);

		//======== dialogoCalendar ========
		{
			dialogoCalendar.setTitle("Calendario");
			dialogoCalendar.setResizable(false);
			dialogoCalendar.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			Container dialogoCalendarContentPane = dialogoCalendar.getContentPane();
			dialogoCalendarContentPane.setLayout(null);
			dialogoCalendarContentPane.add(calendar1);
			calendar1.setBounds(0, 0, 210, 155);

			//---- buttonConfirmarDataInicio ----
			buttonConfirmarDataInicio.setText("Confirmar");
			buttonConfirmarDataInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonConfirmarDataInicio.addActionListener(e -> buttonConfirmarDataActionPerformed(e));
			dialogoCalendarContentPane.add(buttonConfirmarDataInicio);
			buttonConfirmarDataInicio.setBounds(5, 155, 90, 28);

			//---- buttonCancelarData ----
			buttonCancelarData.setText("Cancelar");
			buttonCancelarData.setFont(new Font("Arial", Font.PLAIN, 12));
			buttonCancelarData.addActionListener(e -> buttonCancelarDataActionPerformed(e));
			dialogoCalendarContentPane.add(buttonCancelarData);
			buttonCancelarData.setBounds(110, 155, 90, 28);

			dialogoCalendarContentPane.setPreferredSize(new Dimension(225, 235));
			dialogoCalendar.setSize(225, 235);
			dialogoCalendar.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JComboBox<String> comboBox1;
	private JLabel label2;
	private JLabel data;
	private JButton buttonDataNascimento;
	private JButton buttonCancelar;
	private JButton buttonAvancar;
	private JSeparator separator1;
	private JDialog dialogoCalendar;
	private JCalendar calendar1;
	private JButton buttonConfirmarDataInicio;
	private JButton buttonCancelarData;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
