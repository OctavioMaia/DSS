/*
 * Created by JFormDesigner on Wed Dec 23 20:01:18 GMT 2015
 */

package Presentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author Octavio Maia
 */
public class MainAdmin extends JFrame {
	
	public MainAdmin() {
		initComponents();
		this.setVisible(true);
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
		contentPane.add(labelEleicaoAtiva);
		labelEleicaoAtiva.setBounds(105, 10, 625, 25);

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
							{null, null},
						},
						new String[] {
							"Data de in\u00edcio", "Tipo de elei\u00e7\u00e3o"
						}
					));
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

					//---- table2 ----
					table2.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null},
						},
						new String[] {
							"Data da elei\u00e7\u00e3o", "Tipo de elei\u00e7\u00e3o", "Vencedor"
						}
					));
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
		separator1.setBounds(10, 45, 720, 5);

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
		setSize(755, 490);
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
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
