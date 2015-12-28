/*
 * Created by JFormDesigner on Tue Dec 22 18:35:58 GMT 2015
 */

package Presentation;

import java.awt.*;
import javax.swing.*;

import Business.Eleicao;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class GerirAR extends JFrame {
	
	private SGE sge;
	private Eleicao eleicao;
	
	public GerirAR(SGE s, Eleicao e) {
		eleicao=e;
		sge=s;
		initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		comboBox1 = new JComboBox<>();

		//======== this ========
		setTitle("Gerir Elei\u00e7\u00e3o Assembleia da Rep\u00fablica");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Circulo pertencente");
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(20, 20, label1.getPreferredSize().width, 25);

		//---- label2 ----
		label2.setText("Nome da lista");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(20, 50, 124, 25);

		//---- label3 ----
		label3.setText("Sigla");
		label3.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(20, 80, 124, 25);

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
		comboBox1.setBounds(155, 20, 75, 25);

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
		setSize(755, 460);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JComboBox<String> comboBox1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
