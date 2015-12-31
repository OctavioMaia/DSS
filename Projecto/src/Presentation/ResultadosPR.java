/*
 * Created by JFormDesigner on Fri Dec 11 17:15:43 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import Business.EleicaoPR;
import Business.ListaPR;
import Business.ListavelVotos;
import Business.ResultadoCirculoPR;
import Business.ResultadoGlobalAR;
import Business.ResultadoGlobalPR;
import Business.SGE;

/**
 * @author Octavio Maia
 */
public class ResultadosPR extends JFrame {
	private SGE sge;
	private EleicaoPR eleicao;
	
	public ResultadosPR(SGE s, EleicaoPR el) {
		sge=s;
		eleicao = el;
		initComponents();
		ResultadosPR.setVisible(true);
	}
	
	private void povoarListaGlobais(int volta){
		ResultadoGlobalPR res_global = sge.getResultadoGlobalPR(eleicao,volta);
		Set<ListavelVotos> set = res_global.getValidos();
		Object[][] data = new Object[set.size()][]; 
		int i=0;
		
		if (tableResultadosGlobais.getRowCount() > 0) {
            for (int conta = tableResultadosGlobais.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableResultadosGlobais.getModel()).removeRow(conta);
            }
        }
		int toteleitores = res_global.getTotEleitores();
		for(ListavelVotos listavot : set){	
			Object[] dataaux = {((ListaPR)listavot.getLista()).getCandidato().getNome(),listavot.getVotos()/toteleitores*100,listavot.getVotos()};
			data[i] =dataaux;
			
			DefaultTableModel model = (DefaultTableModel) tableResultadosGlobais.getModel();
			model.addRow(data[i]);
			i++;
		}
	}
	private void povoarListaCriculo(int volta,int circulo){
		ResultadoCirculoPR res_cir = sge.getResultadoCirculoPR(eleicao,volta,circulo);
		Set<ListavelVotos> set = sge.ordenarListaPR(res_cir.getValidos());
		Object[][] data = new Object[set.size()][]; 
		int i=0;
		
		if (tableResultadosGlobais.getRowCount() > 0) {
            for (int conta = tableResultadosGlobais.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableResultadosGlobais.getModel()).removeRow(conta);
            }
        }
		int toteleitoresCirculo = res_cir.getTotEleitores();
		for(ListavelVotos listavot : set){	
			Object[] dataaux = {((ListaPR)listavot.getLista()).getCandidato().getNome(),listavot.getVotos()/toteleitoresCirculo*100,listavot.getVotos()};
			data[i] =dataaux;
			
			DefaultTableModel model = (DefaultTableModel) tableResultadosGlobais.getModel();
			model.addRow(data[i]);
			i++;
		}
	}

	
	private void comboBox1ItemStateChanged(ItemEvent e) {
		povoarListaCriculo(voltaBox.getSelectedIndex()+1, comboBox1.getSelectedIndex()+1);
	}

	private void voltaBoxItemStateChanged(ItemEvent e) {
		if(voltaBox.getSelectedIndex()==1){
			if(sge.temSegundaVolta(eleicao)){
				povoarListaGlobais(2);
				povoarListaCriculo(2, 1);
			}else{
				JOptionPane.showMessageDialog(null, "Houve maioria absoluta na primeira volta");
				voltaBox.removeItemAt(1);
			}
		}
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
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
		comboBox1 = new JComboBox<>();

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
						"Lista", "Percentagem", "Votos"
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
					TableColumnModel cm = tableResultadosGlobais.getColumnModel();
					cm.getColumn(0).setResizable(false);
				}
				tableResultadosGlobais.setFont(new Font("Arial", Font.PLAIN, 12));
				tableResultadosGlobais.setRowSelectionAllowed(false);
				povoarListaGlobais(1);
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
			globaisBrancosN.setText(""+sge.getResultadoGlobalPR(eleicao,1).getBrancos());
			globaisBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisBrancosN);
			globaisBrancosN.setBounds(380, 60, 75, 17);

			//---- globaisNulosN ----
			globaisNulosN.setText(""+sge.getResultadoGlobalPR(eleicao, 1).getNulos());
			globaisNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisNulosN);
			globaisNulosN.setBounds(380, 85, 75, 17);

			//---- globaisAbstencaoN ----
			globaisAbstencaoN.setText(""+sge.getResultadoGlobalPR(eleicao,1).getAbstencao());
			globaisAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisAbstencaoN);
			globaisAbstencaoN.setBounds(380, 110, 75, 17);

			//---- globaisAbstencaoP ----
			globaisAbstencaoP.setText(Math.round(sge.getResultadoGlobalPR(eleicao,1).getAbstencao() / eleicao.numeroEleitores() * 100.0)+"%");
			globaisAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisAbstencaoP);
			globaisAbstencaoP.setBounds(480, 110, 55, 17);

			//---- globaisNulosP ----
			globaisNulosP.setText(Math.round(sge.getResultadoGlobalPR(eleicao,1).getNulos() / eleicao.numeroEleitores() * 100.0)+"%");
			globaisNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisNulosP);
			globaisNulosP.setBounds(480, 85, 55, 17);

			//---- globaisBrancosP ----
			globaisBrancosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoGlobalPR(eleicao,1).getBrancos())+"%");
			globaisBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(globaisBrancosP);
			globaisBrancosP.setBounds(480, 60, 55, 17);

			//---- label12 ----
			label12.setText("Votos brancos:");
			label12.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(label12);
			label12.setBounds(265, 360, 100, 17);

			//---- circuloBrancosN ----
			circuloBrancosN.setText(""+sge.getResultadoCirculoPR(eleicao, 1, 1).getBrancos());
			circuloBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloBrancosN);
			circuloBrancosN.setBounds(380, 360, 75, 17);

			//---- circuloBrancosP ----
			circuloBrancosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,1,1).getBrancos())+"%");
			circuloBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloBrancosP);
			circuloBrancosP.setBounds(480, 360, 55, 17);

			//---- circuloNulosP ----
			circuloNulosP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,1,1).getNulos())+"%");
			circuloNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloNulosP);
			circuloNulosP.setBounds(480, 385, 55, 17);

			//---- circuloAbstencaoP ----
			circuloAbstencaoP.setText(Math.round(100.0 / eleicao.numeroEleitores() * sge.getResultadoCirculoPR(eleicao,1,1).getAbstencao())+"%");
			circuloAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloAbstencaoP);
			circuloAbstencaoP.setBounds(480, 410, 55, 17);

			//---- circuloAbstencaoN ----
			circuloAbstencaoN.setText(""+sge.getResultadoCirculoPR(eleicao, 1, 1).getAbstencao());
			circuloAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosPRContentPane.add(circuloAbstencaoN);
			circuloAbstencaoN.setBounds(380, 410, 75, 17);

			//---- circuloNulosN ----
			circuloNulosN.setText(""+sge.getResultadoCirculoPR(eleicao, 1,1).getNulos());
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
						"Lista", "Percentagem", "Votos"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						Object.class, String.class, Object.class
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
					cm.getColumn(1).setResizable(false);
				}
				tableResultadosCirculo.setFont(new Font("Arial", Font.PLAIN, 12));
				tableResultadosCirculo.setRowSelectionAllowed(false);
				povoarListaCriculo(1,1);
				scrollCirculoExpanded.setViewportView(tableResultadosCirculo);
			}
			ResultadosPRContentPane.add(scrollCirculoExpanded);
			scrollCirculoExpanded.setBounds(15, 440, 560, 135);
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
			imagem.setText("imagem");
			ResultadosPRContentPane.add(imagem);
			imagem.setBounds(335, 160, 165, 170);
			ResultadosPRContentPane.add(separator3);
			separator3.setBounds(15, 45, 560, 2);

			//---- label6 ----
			label6.setText("Selecione a volta:");
			label6.setFont(new Font("Arial", Font.BOLD, 14));
			ResultadosPRContentPane.add(label6);
			label6.setBounds(20, 15, 134, 25);

			//---- voltaBox ----
			voltaBox.setFont(new Font("Arial", Font.PLAIN, 14));
			voltaBox.setModel(new DefaultComboBoxModel<>(new String[] {
				"Primeira Volta",
				"Segunda Volta"
			}));
			voltaBox.addItemListener(e -> voltaBoxItemStateChanged(e));
			ResultadosPRContentPane.add(voltaBox);
			voltaBox.setBounds(165, 15, 135, 25);

			//---- comboBox1 ----
			comboBox1.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
				"Aveiro",
				"Beja",
				"Braga",
				"Braganca",
				"Castelo Branco",
				"Coimbra",
				"Evora",
				"Faro",
				"Guarda",
				"Leiria",
				"Lisboa",
				"Portalegre",
				"Porto",
				"Santarem",
				"Setubal",
				"Viana do Castelo",
				"Vila Real",
				"Viseu",
				"Acores",
				"Madeira",
				"Europa",
				"Fora da Europa"
			}));
			comboBox1.addItemListener(e -> comboBox1ItemStateChanged(e));
			ResultadosPRContentPane.add(comboBox1);
			comboBox1.setBounds(15, 390, 185, 25);

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
	// Generated using JFormDesigner Evaluation license - Rui Freitas
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
	private JComboBox<String> comboBox1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
