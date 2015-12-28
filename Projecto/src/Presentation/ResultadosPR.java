/*
 * Created by JFormDesigner on Fri Dec 11 17:15:43 GMT 2015
 */

package Presentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import Business.EleicaoPR;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class ResultadosPR extends JFrame {
	private SGE sge;
	private EleicaoPR eleicao;
	private String path;
	
	public ResultadosPR(SGE s, EleicaoPR el) {
		sge=s;
		eleicao = el;
		initComponents();
		ResultadosPR.setVisible(true);
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		ResultadosPR = new JFrame();
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
		separator3 = new JSeparator();
		label6 = new JLabel();
		voltaBox = new JComboBox<>();

		//======== ResultadosPR ========
		{
			ResultadosPR.setResizable(false);
			ResultadosPR.setTitle("Resultados Presidencia da Rep\u00fablica");
			Container ResultadosPRContentPane = ResultadosPR.getContentPane();
			ResultadosPRContentPane.setLayout(null);

			//---- label1 ----
			label1.setText("Resultados globais");
			label1.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosPRContentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(20, 85), label1.getPreferredSize()));

			//---- label2 ----
			label2.setText("Votos brancos:");
			label2.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label2);
			label2.setBounds(265, 60, 100, 17);

			//---- label3 ----
			label3.setText("Votos nulos:");
			label3.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label3);
			label3.setBounds(265, 85, 90, 17);

			//---- label4 ----
			label4.setText("Absten\u00e7\u00e3o:");
			label4.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label4);
			label4.setBounds(265, 110, 85, 17);
			ResultadosPRContentPane.add(separator1);
			separator1.setBounds(15, 350, 560, 2);

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
			ResultadosPRContentPane.add(scrollGlobais);
			scrollGlobais.setBounds(15, 140, 235, 195);

			//---- label5 ----
			label5.setText("Resultados c\u00edrculo");
			label5.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosPRContentPane.add(label5);
			label5.setBounds(15, 360, 134, 17);

			//---- globaisBrancosN ----
			globaisBrancosN.setText(""+sge.getResultadoGlobalPR(eleicao, voltaBox.getSelectedIndex()+1).getBrancos());
			globaisBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisBrancosN);
			globaisBrancosN.setBounds(380, 60, 75, 17);

			//---- globaisNulosN ----
			globaisNulosN.setText(""+sge.getResultadoGlobalPR(eleicao, voltaBox.getSelectedIndex()+1).getNulos());
			globaisNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisNulosN);
			globaisNulosN.setBounds(380, 85, 75, 17);

			//---- globaisAbstencaoN ----
			globaisAbstencaoN.setText(""+sge.getResultadoGlobalPR(eleicao, voltaBox.getSelectedIndex()+1).getAbstencao());
			globaisAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisAbstencaoN);
			globaisAbstencaoN.setBounds(380, 110, 75, 17);

			//---- globaisAbstencaoP ----
			globaisAbstencaoP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoGlobalPR(eleicao,voltaBox.getSelectedIndex()+1).getAbstencao())+"%");
			globaisAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisAbstencaoP);
			globaisAbstencaoP.setBounds(480, 110, 55, 17);

			//---- globaisNulosP ----
			globaisNulosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoGlobalPR(eleicao,voltaBox.getSelectedIndex()+1).getNulos())+"%");
			globaisNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisNulosP);
			globaisNulosP.setBounds(480, 85, 55, 17);

			//---- globaisBrancosP ----
			globaisBrancosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoGlobalPR(eleicao,voltaBox.getSelectedIndex()+1).getBrancos())+"%");
			globaisBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisBrancosP);
			globaisBrancosP.setBounds(480, 60, 55, 17);

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
					boolean[] columnEditable = new boolean[] {
						false, true
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
				tableCirculo.getColumnModel().getColumn(1).setPreferredWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setMinWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setWidth(0);
				tableCirculo.getColumnModel().getColumn(1).setMaxWidth(0);
				scrollCirculo.setViewportView(tableCirculo);
			}
			ResultadosPRContentPane.add(scrollCirculo);
			scrollCirculo.setBounds(15, 385, 130, 190);

			//---- label12 ----
			label12.setText("Votos brancos:");
			label12.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label12);
			label12.setBounds(265, 360, 100, 17);

			//---- circuloBrancosN ----
			circuloBrancosN.setText(""+sge.getResultadoCirculoPR(eleicao, voltaBox.getSelectedIndex()+1, (int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getBrancos());
			circuloBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloBrancosN);
			circuloBrancosN.setBounds(380, 360, 75, 17);

			//---- circuloBrancosP ----
			circuloBrancosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,voltaBox.getSelectedIndex()+1,(int) tableCirculo.getValueAt(tableCirculo.getSelectedRow(), 1)).getBrancos())+"%");
			circuloBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloBrancosP);
			circuloBrancosP.setBounds(480, 360, 55, 17);

			//---- circuloNulosP ----
			circuloNulosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,voltaBox.getSelectedIndex()+1,(int) tableCirculo.getValueAt(tableCirculo.getSelectedRow(), 1)).getNulos())+"%");
			circuloNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloNulosP);
			circuloNulosP.setBounds(480, 385, 55, 17);

			//---- circuloAbstencaoP ----
			circuloAbstencaoP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,voltaBox.getSelectedIndex()+1,(int) tableCirculo.getValueAt(tableCirculo.getSelectedRow(), 1)).getAbstencao())+"%");
			circuloAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloAbstencaoP);
			circuloAbstencaoP.setBounds(480, 410, 55, 17);

			//---- circuloAbstencaoN ----
			circuloAbstencaoN.setText(""+sge.getResultadoCirculoPR(eleicao, voltaBox.getSelectedIndex()+1, (int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getAbstencao());
			circuloAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloAbstencaoN);
			circuloAbstencaoN.setBounds(380, 410, 75, 17);

			//---- circuloNulosN ----
			circuloNulosN.setText(""+sge.getResultadoCirculoPR(eleicao, voltaBox.getSelectedIndex()+1, (int) tableCirculo.getModel().getValueAt(tableCirculo.getSelectedRow(), 1)).getNulos());
			circuloNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloNulosN);
			circuloNulosN.setBounds(380, 385, 75, 17);

			//---- label19 ----
			label19.setText("Votos nulos:");
			label19.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label19);
			label19.setBounds(265, 385, 90, 17);

			//---- label20 ----
			label20.setText("Absten\u00e7\u00e3o:");
			label20.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label20);
			label20.setBounds(265, 410, 85, 17);

			//======== scrollCirculoExpanded ========
			{
				scrollCirculoExpanded.setFont(new Font("Arial", Font.PLAIN, 12));

				//---- tableResultadosCirculo ----
				tableResultadosCirculo.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Percentagem", "Lista", "N\u00famero de Votos", null
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						String.class, Object.class, Object.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, false, true, true
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
				tableResultadosCirculo.getColumnModel().getColumn(3).setPreferredWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(3).setMinWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(3).setWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(3).setMaxWidth(0);
				scrollCirculoExpanded.setViewportView(tableResultadosCirculo);
			}
			ResultadosPRContentPane.add(scrollCirculoExpanded);
			scrollCirculoExpanded.setBounds(155, 440, 420, 135);
			ResultadosPRContentPane.add(separator2);
			separator2.setBounds(new Rectangle(new Point(195, 90), separator2.getPreferredSize()));

			//======== desktopPane1 ========
			{
				desktopPane1.setBackground(UIManager.getColor("Button.background"));
				desktopPane1.setBorder(new EtchedBorder());
			}
			ResultadosPRContentPane.add(desktopPane1);
			desktopPane1.setBounds(255, 55, 320, 80);

			//======== desktopPane2 ========
			{
				desktopPane2.setBackground(UIManager.getColor("Button.background"));
				desktopPane2.setBorder(new EtchedBorder());
			}
			ResultadosPRContentPane.add(desktopPane2);
			desktopPane2.setBounds(255, 355, 320, 80);

			//---- imagem ----
			imagem.setIcon(null);
			ResultadosPRContentPane.add(imagem);
			imagem.setBounds(335, 160, 165, 170);
			ResultadosPRContentPane.add(separator3);
			separator3.setBounds(15, 45, 560, 2);

			//---- label6 ----
			label6.setText("Selecione a volta:");
			label6.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosPRContentPane.add(label6);
			label6.setBounds(20, 15, 134, 17);

			//---- voltaBox ----
			voltaBox.setFont(new Font("Arial", Font.PLAIN, 14));
			voltaBox.setModel(new DefaultComboBoxModel<>(new String[] {
				"Primeira Volta",
				"Segunda Volta"
			}));
			ResultadosPRContentPane.add(voltaBox);
			voltaBox.setBounds(165, 15, 135, voltaBox.getPreferredSize().height);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < ResultadosPRContentPane.getComponentCount(); i++) {
					Rectangle bounds = ResultadosPRContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = ResultadosPRContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				ResultadosPRContentPane.setMinimumSize(preferredSize);
				ResultadosPRContentPane.setPreferredSize(preferredSize);
			}
			ResultadosPR.setSize(605, 625);
			ResultadosPR.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame ResultadosPR;
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
	private JSeparator separator3;
	private JLabel label6;
	private JComboBox<String> voltaBox;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
