/*
 * Created by JFormDesigner on Fri Dec 11 17:15:43 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Octavio Maia
 */
public class ResultadosPR extends JFrame {
	public ResultadosPR() {
		initComponents();
	}

	public static void main(String args[]){
		ResultadosPR gui = new ResultadosPR();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.pack();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		ResultadosAR = new JFrame();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		separator1 = new JSeparator();
		scrollGlobais = new JScrollPane();
		tableResultadosGlobais = new JTable();
		label5 = new JLabel();
		globaisBrancosN = new JLabel();
		globaisNulosN = new JLabel();
		globaisAbstencaoN = new JLabel();
		globaisAbstencaoP = new JLabel();
		globaisNulosP = new JLabel();
		globaisBrancosP = new JLabel();
		scrollCirculo = new JScrollPane();
		tableCirculo = new JTable();
		label12 = new JLabel();
		circuloBrancosN = new JLabel();
		circuloBrancosP = new JLabel();
		circuloNulosP = new JLabel();
		circuloAbstencaoP = new JLabel();
		circuloAbstencaoN = new JLabel();
		circuloNulosN = new JLabel();
		label19 = new JLabel();
		label20 = new JLabel();
		scrollCirculoExpanded = new JScrollPane();
		tableResultadosCirculo = new JTable();
		separator2 = new JToolBar.Separator();
		desktopPane1 = new JDesktopPane();
		desktopPane2 = new JDesktopPane();
		imagem = new JLabel();

		//======== ResultadosAR ========
		{
			ResultadosAR.setResizable(false);
			ResultadosAR.setTitle("Resultados Presidente da Rep\u00fablica");
			Container ResultadosARContentPane = ResultadosAR.getContentPane();
			ResultadosARContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Resultados globais");
			label1.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosARContentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(20, 35), label1.getPreferredSize()));

			//---- label2 ----
			label2.setText("Votos brancos:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label2);
			label2.setBounds(265, 10, 100, 17);

			//---- label3 ----
			label3.setText("Votos nulos:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label3);
			label3.setBounds(265, 35, 90, 17);

			//---- label4 ----
			label4.setText("Absten\u00e7\u00e3o:");
			label4.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label4);
			label4.setBounds(265, 60, 85, 17);
			ResultadosARContentPane.add(separator1);
			separator1.setBounds(15, 300, 560, 2);

			//======== scrollGlobais ========
			{
				scrollGlobais.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosGlobais ----
				tableResultadosGlobais.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Percentagem", "Lista"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, Object.class
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
					TableColumnModel cm = tableResultadosGlobais.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableResultadosGlobais.setFont(new Font("Arial", Font.PLAIN, 12));
				scrollGlobais.setViewportView(tableResultadosGlobais);
			}
			ResultadosARContentPane.add(scrollGlobais);
			scrollGlobais.setBounds(15, 90, 235, 195);

			//---- label5 ----
			label5.setText("Resultados c\u00edrculo");
			label5.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosARContentPane.add(label5);
			label5.setBounds(15, 310, 134, 17);

			//---- globaisBrancosN ----
			globaisBrancosN.setText("N");
			globaisBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosN);
			globaisBrancosN.setBounds(405, 10, 40, 17);

			//---- globaisNulosN ----
			globaisNulosN.setText("N");
			globaisNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosN);
			globaisNulosN.setBounds(405, 35, 40, 17);

			//---- globaisAbstencaoN ----
			globaisAbstencaoN.setText("N");
			globaisAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoN);
			globaisAbstencaoN.setBounds(405, 60, 40, 17);

			//---- globaisAbstencaoP ----
			globaisAbstencaoP.setText("%");
			globaisAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoP);
			globaisAbstencaoP.setBounds(470, 60, 40, 17);

			//---- globaisNulosP ----
			globaisNulosP.setText("%");
			globaisNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosP);
			globaisNulosP.setBounds(470, 35, 40, 17);

			//---- globaisBrancosP ----
			globaisBrancosP.setText("%");
			globaisBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosP);
			globaisBrancosP.setBounds(470, 10, 40, 17);

			//======== scrollCirculo ========
			{
				scrollCirculo.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableCirculo ----
				tableCirculo.setFont(new Font("Arial", Font.PLAIN, 12));
				tableCirculo.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"C\u00edrculo"
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
					TableColumnModel cm = tableCirculo.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				scrollCirculo.setViewportView(tableCirculo);
			}
			ResultadosARContentPane.add(scrollCirculo);
			scrollCirculo.setBounds(15, 335, 130, 190);

			//---- label12 ----
			label12.setText("Votos brancos:");
			label12.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label12);
			label12.setBounds(265, 310, 100, 17);

			//---- circuloBrancosN ----
			circuloBrancosN.setText("N");
			circuloBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosN);
			circuloBrancosN.setBounds(415, 310, 40, 17);

			//---- circuloBrancosP ----
			circuloBrancosP.setText("%");
			circuloBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosP);
			circuloBrancosP.setBounds(480, 310, 40, 17);

			//---- circuloNulosP ----
			circuloNulosP.setText("%");
			circuloNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloNulosP);
			circuloNulosP.setBounds(480, 335, 40, 17);

			//---- circuloAbstencaoP ----
			circuloAbstencaoP.setText("%");
			circuloAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoP);
			circuloAbstencaoP.setBounds(480, 360, 40, 17);

			//---- circuloAbstencaoN ----
			circuloAbstencaoN.setText("N");
			circuloAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoN);
			circuloAbstencaoN.setBounds(415, 360, 40, 17);

			//---- circuloNulosN ----
			circuloNulosN.setText("N");
			circuloNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloNulosN);
			circuloNulosN.setBounds(415, 335, 40, 17);

			//---- label19 ----
			label19.setText("Votos nulos:");
			label19.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label19);
			label19.setBounds(265, 335, 90, 17);

			//---- label20 ----
			label20.setText("Absten\u00e7\u00e3o:");
			label20.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label20);
			label20.setBounds(265, 360, 85, 17);

			//======== scrollCirculoExpanded ========
			{
				scrollCirculoExpanded.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosCirculo ----
				tableResultadosCirculo.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Percentagem", "Lista", "N\u00famero de Votos"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, Object.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, false, true
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
					TableColumnModel cm = tableResultadosCirculo.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableResultadosCirculo.setFont(new Font("Arial", Font.PLAIN, 12));
				scrollCirculoExpanded.setViewportView(tableResultadosCirculo);
			}
			ResultadosARContentPane.add(scrollCirculoExpanded);
			scrollCirculoExpanded.setBounds(155, 390, 420, 135);
			ResultadosARContentPane.add(separator2);
			separator2.setBounds(new Rectangle(new Point(195, 40), separator2.getPreferredSize()));

			//======== desktopPane1 ========
			{
				desktopPane1.setBackground(UIManager.getColor("Button.background"));
				desktopPane1.setBorder(new EtchedBorder());
			}
			ResultadosARContentPane.add(desktopPane1);
			desktopPane1.setBounds(255, 5, 320, 80);

			//======== desktopPane2 ========
			{
				desktopPane2.setBackground(UIManager.getColor("Button.background"));
				desktopPane2.setBorder(new EtchedBorder());
			}
			ResultadosARContentPane.add(desktopPane2);
			desktopPane2.setBounds(255, 305, 320, 80);

			//---- imagem ----
			imagem.setIcon(new ImageIcon(getClass().getResource("/img/PS.png")));
			ResultadosARContentPane.add(imagem);
			imagem.setBounds(335, 110, 165, 170);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < ResultadosARContentPane.getComponentCount(); i++) {
					Rectangle bounds = ResultadosARContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = ResultadosARContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				ResultadosARContentPane.setMinimumSize(preferredSize);
				ResultadosARContentPane.setPreferredSize(preferredSize);
			}
			ResultadosAR.setSize(605, 580);
			ResultadosAR.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame ResultadosAR;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JSeparator separator1;
	private JScrollPane scrollGlobais;
	private JTable tableResultadosGlobais;
	private JLabel label5;
	private JLabel globaisBrancosN;
	private JLabel globaisNulosN;
	private JLabel globaisAbstencaoN;
	private JLabel globaisAbstencaoP;
	private JLabel globaisNulosP;
	private JLabel globaisBrancosP;
	private JScrollPane scrollCirculo;
	private JTable tableCirculo;
	private JLabel label12;
	private JLabel circuloBrancosN;
	private JLabel circuloBrancosP;
	private JLabel circuloNulosP;
	private JLabel circuloAbstencaoP;
	private JLabel circuloAbstencaoN;
	private JLabel circuloNulosN;
	private JLabel label19;
	private JLabel label20;
	private JScrollPane scrollCirculoExpanded;
	private JTable tableResultadosCirculo;
	private JToolBar.Separator separator2;
	private JDesktopPane desktopPane1;
	private JDesktopPane desktopPane2;
	private JLabel imagem;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}