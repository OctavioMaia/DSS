/*
 * Created by JFormDesigner on Fri Dec 11 13:54:55 GMT 2015
 */

package Presentation;

import java.awt.*;
import javax.swing.*;

/**
 * @author Octavio Maia
 */
public class Login  {

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		frameLogin = new JFrame();
		panel1 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		textUsername = new JTextField();
		textPassword = new JPasswordField();
		buttonAutenticar = new JButton();
		buttonSair = new JButton();
		labelPasswordErrada = new JLabel();

		//======== frameLogin ========
		{
			frameLogin.setResizable(false);
			frameLogin.setTitle("Sistemas de Elei\u00e7\u00f5es");
			frameLogin.setType(Window.Type.UTILITY);
			frameLogin.setFont(new Font("Calibri", Font.PLAIN, 12));
			Container frameLoginContentPane = frameLogin.getContentPane();
			frameLoginContentPane.setLayout(new BoxLayout(frameLoginContentPane, BoxLayout.X_AXIS));

			//======== panel1 ========
			{

				// JFormDesigner evaluation mark
				panel1.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				panel1.setLayout(null);

				//---- label1 ----
				label1.setText("Username");
				label1.setFont(new Font("Calibri", Font.BOLD, 22));
				label1.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(label1);
				label1.setBounds(40, 15, 120, 40);

				//---- label2 ----
				label2.setText("Password");
				label2.setFont(new Font("Calibri", Font.BOLD, 22));
				label2.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(label2);
				label2.setBounds(40, 60, 120, 40);

				//---- textUsername ----
				textUsername.setFont(new Font("Calibri", Font.PLAIN, 14));
				textUsername.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(textUsername);
				textUsername.setBounds(160, 20, 190, 35);

				//---- textPassword ----
				textPassword.setFont(new Font("Calibri", Font.PLAIN, 14));
				textPassword.setHorizontalAlignment(SwingConstants.CENTER);
				panel1.add(textPassword);
				textPassword.setBounds(160, 60, 190, 35);

				//---- buttonAutenticar ----
				buttonAutenticar.setText("Autenticar");
				buttonAutenticar.setFont(new Font("Arial", Font.PLAIN, 14));
				panel1.add(buttonAutenticar);
				buttonAutenticar.setBounds(55, 125, 135, 35);

				//---- buttonSair ----
				buttonSair.setText("Sair");
				buttonSair.setFont(new Font("Arial", Font.PLAIN, 14));
				panel1.add(buttonSair);
				buttonSair.setBounds(220, 125, 135, 35);

				//---- labelPasswordErrada ----
				labelPasswordErrada.setText("Username/Password incorretos!");
				labelPasswordErrada.setFont(new Font("Arial", Font.BOLD, 11));
				labelPasswordErrada.setHorizontalAlignment(SwingConstants.CENTER);
				labelPasswordErrada.setForeground(Color.red);
				panel1.add(labelPasswordErrada);
				labelPasswordErrada.setBounds(new Rectangle(new Point(110, 105), labelPasswordErrada.getPreferredSize()));

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
			frameLoginContentPane.add(panel1);
			frameLogin.setSize(400, 205);
			frameLogin.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame frameLogin;
	private JPanel panel1;
	private JLabel label1;
	private JLabel label2;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JButton buttonAutenticar;
	private JButton buttonSair;
	private JLabel labelPasswordErrada;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
