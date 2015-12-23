/*
 * Created by JFormDesigner on Fri Dec 11 15:46:28 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import Business.EleicaoAR;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class ResultadosAR {
	
	private EleicaoAR eleicao;
	private SGE sge;
	
	public ResultadosAR(SGE s, int id){
		//sge = s;
		//eleicao = (EleicaoAR) sge.getEleicao(id);
		initComponents(eleicao);
		ResultadosAR.setVisible(true);
	}

	private void initComponents(EleicaoAR e) {
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
		scrollCirculoExpanded2 = new JScrollPane();
		tableResultadosCirculo2 = new JTable();
		globaisBrancosN2 = new JLabel();
		globaisNulosN2 = new JLabel();
		globaisAbstencaoN2 = new JLabel();

		//======== ResultadosAR ========
		{
			ResultadosAR.setResizable(false);
			ResultadosAR.setTitle("Resultados Assembleia da Rep\u00fablica");
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
			separator1.setBounds(15, 205, 560, 2);

			//======== scrollGlobais ========
			{
				scrollGlobais.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosGlobais ----
				tableResultadosGlobais.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Lista", "Percentagem", "Mandatos"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						Object.class, String.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, false, false
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
					cm.getColumn(1).setResizable(false);
				}
				tableResultadosGlobais.setFont(new Font("Arial", Font.PLAIN, 12));
				scrollGlobais.setViewportView(tableResultadosGlobais);
			}
			ResultadosARContentPane.add(scrollGlobais);
			scrollGlobais.setBounds(15, 90, 560, 105);

			//---- label5 ----
			label5.setText("Resultados c\u00edrculo");
			label5.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosARContentPane.add(label5);
			label5.setBounds(15, 215, 134, 17);

			//---- globaisBrancosN ----
			//globaisBrancosN.setText(String.valueOf(e.getResultadoGlobal().getBrancos()));
			globaisBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosN);
			globaisBrancosN.setBounds(385, 10, 75, 17);

			//---- globaisNulosN ----
			//globaisNulosN.setText(String.valueOf(e.getResultadoGlobal().getNulos()));
			globaisNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosN);
			globaisNulosN.setBounds(385, 35, 75, 17);

			//---- globaisAbstencaoN ----
			//globaisAbstencaoN.setText(String.valueOf(e.getResultadoGlobal().getAbstencao()));
			globaisAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoN);
			globaisAbstencaoN.setBounds(385, 60, 75, 17);

			//---- globaisAbstencaoP ----
			//globaisAbstencaoP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoGlobal().getTotEleitores() * e.getResultadoGlobal().getAbstencao())));
			globaisAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoP);
			globaisAbstencaoP.setBounds(470, 60, 55, 17);

			//---- globaisNulosP ----
			//globaisNulosP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoGlobal().getTotEleitores() * e.getResultadoGlobal().getNulos())));
			globaisNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosP);
			globaisNulosP.setBounds(470, 35, 55, 17);

			//---- globaisBrancosP ----
			//globaisBrancosP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoGlobal().getTotEleitores() * e.getResultadoGlobal().getBrancos())));
			globaisBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosP);
			globaisBrancosP.setBounds(470, 10, 55, 17);

			//======== scrollCirculo ========
			{
				scrollCirculo.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableCirculo ----
				tableCirculo.setFont(new Font("Arial", Font.PLAIN, 12));
				tableCirculo.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"C\u00edrculo", null
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						Object.class, String.class
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
					TableColumnModel cm = tableCirculo.getColumnModel();
					cm.getColumn(0).setResizable(false);
					cm.getColumn(1).setResizable(false);
				}
				tableCirculo.getColumnModel().getColumn(1).setPreferredWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setMinWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setMaxWidth(0);
				scrollCirculo.setViewportView(tableCirculo);
			}
			ResultadosARContentPane.add(scrollCirculo);
			scrollCirculo.setBounds(15, 240, 130, 190);

			//---- label12 ----
			label12.setText("Votos brancos:");
			label12.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label12);
			label12.setBounds(265, 215, 100, 17);

			//---- circuloBrancosN ----
			//circuloBrancosN.setText(String.valueOf(e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getBrancos()));
			circuloBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosN);
			circuloBrancosN.setBounds(385, 215, 80, 17);

			//---- circuloBrancosP ----
			//circuloBrancosP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getTotEleitores() * e.getResultadoGlobal().getBrancos())));
			circuloBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosP);
			circuloBrancosP.setBounds(480, 215, 60, 17);

			//---- circuloNulosP ----
			//circuloNulosP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getTotEleitores() * e.getResultadoGlobal().getNulos())));
			circuloNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloNulosP);
			circuloNulosP.setBounds(480, 240, 60, 17);

			//---- circuloAbstencaoP ----
			//circuloAbstencaoP.setText(String.valueOf((int)Math.round(100.0 / e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getTotEleitores() * e.getResultadoGlobal().getAbstencao())));
			circuloAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoP);
			circuloAbstencaoP.setBounds(480, 265, 60, 17);

			//---- circuloAbstencaoN ----
			//circuloAbstencaoN.setText(String.valueOf(e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getAbstencao()));
			circuloAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoN);
			circuloAbstencaoN.setBounds(385, 265, 80, 17);

			//---- circuloNulosN ----
			//circuloNulosN.setText(String.valueOf(e.getResultadoCirculo((int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getNulos()));
			circuloNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloNulosN);
			circuloNulosN.setBounds(385, 240, 80, 17);

			//---- label19 ----
			label19.setText("Votos nulos:");
			label19.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label19);
			label19.setBounds(265, 240, 90, 17);

			//---- label20 ----
			label20.setText("Absten\u00e7\u00e3o:");
			label20.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label20);
			label20.setBounds(265, 265, 85, 17);

			//======== scrollCirculoExpanded ========
			{
				scrollCirculoExpanded.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosCirculo ----
				tableResultadosCirculo.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Percentagem", "Lista", "Mandatos", "N\u00famero de votos", null
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, Object.class, Object.class, Object.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, false, false, false, false
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
					cm.getColumn(4).setResizable(false);
				}
				tableResultadosCirculo.setFont(new Font("Arial", Font.PLAIN, 12));
				tableResultadosCirculo.getColumnModel().getColumn(4).setPreferredWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setMinWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setMaxWidth(0);
				scrollCirculoExpanded.setViewportView(tableResultadosCirculo);
			}
			ResultadosARContentPane.add(scrollCirculoExpanded);
			scrollCirculoExpanded.setBounds(155, 295, 420, 135);
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
			desktopPane2.setBounds(255, 210, 320, 80);

			//======== scrollCirculoExpanded2 ========
			{
				scrollCirculoExpanded2.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosCirculo2 ----
				tableResultadosCirculo2.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"N\u00famero de ordem", "Nome do candidato"
					}
				) {
					boolean[] columnEditable = new boolean[] {
						false, false
					};
					@Override
					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return columnEditable[columnIndex];
					}
				});
				{
					TableColumnModel cm = tableResultadosCirculo2.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableResultadosCirculo2.setFont(new Font("Arial", Font.PLAIN, 12));
				scrollCirculoExpanded2.setViewportView(tableResultadosCirculo2);
			}
			ResultadosARContentPane.add(scrollCirculoExpanded2);
			scrollCirculoExpanded2.setBounds(15, 440, 560, 95);

			//---- globaisBrancosN2 ----
			globaisBrancosN2.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosN2);
			globaisBrancosN2.setBounds(385, 10, 60, 17);

			//---- globaisNulosN2 ----
			globaisNulosN2.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosN2);
			globaisNulosN2.setBounds(385, 35, 60, 17);

			//---- globaisAbstencaoN2 ----
			globaisAbstencaoN2.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoN2);
			globaisAbstencaoN2.setBounds(385, 60, 60, 17);

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
	private JScrollPane scrollCirculoExpanded2;
	private JTable tableResultadosCirculo2;
	private JLabel globaisBrancosN2;
	private JLabel globaisNulosN2;
	private JLabel globaisAbstencaoN2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}