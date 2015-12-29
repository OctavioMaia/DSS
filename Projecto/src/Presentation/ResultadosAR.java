/*
 * Created by JFormDesigner on Fri Dec 11 15:46:28 GMT 2015
 */

package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import Business.CandidatoAR;
import Business.Eleicao;
import Business.EleicaoAR;
import Business.Lista;
import Business.Listavel;
import Business.ListavelVotos;
import Business.ResultadoCirculoAR;
import Business.ResultadoGlobalAR;
import Business.SGE;
import Business.VotavelVotos;

/**
 * @author Octavio Maia
 */
public class ResultadosAR extends JFrame{
	
	private EleicaoAR eleicao;
	private SGE sge;
	private ResultadoGlobalAR resultado_global;
	
	public ResultadosAR(SGE s, EleicaoAR el){
		sge = s;
		eleicao = el;
		resultado_global= eleicao.getResultadoGlobal();
		initComponents(eleicao);
		ResultadosAR.setVisible(true);
	}
	
	private void povoarGlobais() {
		ResultadoGlobalAR historico = resultado_global;
		Set<VotavelVotos> set = sge.ordenarVotavel(historico.getMandatos());
		Object[][] data = new Object[set.size()][]; 
		int i=0;
		
		if (tableResultadosGlobais.getRowCount() > 0) {
            for (int conta = tableResultadosGlobais.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableResultadosGlobais.getModel()).removeRow(conta);
            }
        }
		
		for(VotavelVotos el : set){	
			data[i] = el.toTable(historico.getTotEleitores());
			
			DefaultTableModel model = (DefaultTableModel) tableResultadosGlobais.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void comboBox1ItemStateChanged(ItemEvent e) {
		if (tableResultadosCirculo2.getRowCount() > 0) {
            for (int conta = tableResultadosCirculo2.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableResultadosCirculo2.getModel()).removeRow(conta);
            }
        }
		povoarResultadosCirculo();
		
		ResultadoCirculoAR r = eleicao.getResultadoCirculo(comboBox1.getSelectedIndex()+1);
		circuloBrancosN.setText(String.valueOf(r.getBrancos()));
		circuloBrancosP.setText(String.valueOf((int)Math.round(100.0 / r.getTotEleitores() * r.getBrancos())));
		circuloNulosP.setText(String.valueOf((int)Math.round(100.0 / r.getTotEleitores() * r.getNulos())));
		circuloAbstencaoP.setText(String.valueOf((int)Math.round(100.0 / r.getTotEleitores() * r.getAbstencao())));
		circuloAbstencaoN.setText(String.valueOf(r.getAbstencao()));
		circuloNulosN.setText(String.valueOf(r.getNulos()));

	}
	
	private void povoarResultadosCirculo(){
		ResultadoCirculoAR historico = sge.getResultadoCirculoAR(eleicao, comboBox1.getSelectedIndex()+1);
		Set<Lista> setkeys = historico.getValidos().keySet();
		Object[][] data = new Object[setkeys.size()][]; 
		Map<Lista,Integer> validos = historico.getValidos();
		Map<Lista,Integer> mandatos = historico.getMandatos();
		
		int i=0;
		
		if (tableResultadosCirculo.getRowCount() > 0) {
            for (int conta = tableResultadosCirculo.getRowCount() - 1; conta > -1; conta--) {
                ((DefaultTableModel) tableResultadosCirculo.getModel()).removeRow(conta);
            }
        }
		
		for(Lista lista : setkeys){			
			float percentagem=0;
			
			try{
				percentagem = (validos.get(lista)/historico.getTotEleitores())*100;
			}catch(Exception ex){}
			Object[] dataAux = {percentagem,lista.getNome(),mandatos.get(lista),validos.get(lista),lista};
			data[i] = dataAux;
			
			DefaultTableModel model = (DefaultTableModel) tableResultadosCirculo.getModel();
			model.addRow(data[i]);

			i++;
		}
	}

	private void scrollCirculoExpandedMouseEntered(MouseEvent e) {
		if(tableResultadosCirculo.getSelectedRowCount()==1){
			ArrayList<CandidatoAR> array = ((Lista) tableResultadosCirculo.getValueAt(tableResultadosCirculo.getSelectedRow(), 4)).getCandidatos();
			int mandatos = (sge.getResultadoCirculoAR(eleicao, comboBox1.getSelectedIndex()+1).getMandatos().get(tableResultadosCirculo.getValueAt(tableResultadosCirculo.getSelectedRow(), 4)));
			
			if (tableResultadosCirculo2.getRowCount() > 0) {
	            for (int conta = tableResultadosCirculo2.getRowCount() - 1; conta > -1; conta--) {
	                ((DefaultTableModel) tableResultadosCirculo2.getModel()).removeRow(conta);
	            }
	        }
			
			for(int i=0; i< mandatos;i++){
				DefaultTableModel model = (DefaultTableModel) tableResultadosCirculo.getModel();
				model.addRow(array.get(i).toTable());
			}
		}
	}

	private void initComponents(EleicaoAR el) {
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
		comboBox1 = new JComboBox<>();

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
						"Lista", "Tipo", "Percentagem", "Mandatos"
					}
				) {
					Class<?>[] columnTypes = new Class<?>[] {
						Object.class, Object.class, String.class, Object.class
					};
					boolean[] columnEditable = new boolean[] {
						false, true, false, false
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
					cm.getColumn(2).setResizable(false);
				}
				tableResultadosGlobais.setFont(new Font("Arial", Font.PLAIN, 12));
				tableResultadosGlobais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableResultadosGlobais.setRowSelectionAllowed(false);
				povoarGlobais();
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
			globaisBrancosN.setText(String.valueOf(el.getResultadoGlobal().getBrancos()));
			globaisBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosN);
			globaisBrancosN.setBounds(385, 10, 75, 17);

			//---- globaisNulosN ----
			globaisNulosN.setText(String.valueOf(el.getResultadoGlobal().getNulos()));
			globaisNulosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosN);
			globaisNulosN.setBounds(385, 35, 75, 17);

			//---- globaisAbstencaoN ----
			globaisAbstencaoN.setText(String.valueOf(el.getResultadoGlobal().getAbstencao()));
			globaisAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoN);
			globaisAbstencaoN.setBounds(385, 60, 75, 17);

			//---- globaisAbstencaoP ----
			globaisAbstencaoP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoGlobal().getTotEleitores() * el.getResultadoGlobal().getAbstencao())));
			globaisAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisAbstencaoP);
			globaisAbstencaoP.setBounds(470, 60, 55, 17);

			//---- globaisNulosP ----
			globaisNulosP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoGlobal().getTotEleitores() * el.getResultadoGlobal().getNulos())));
			globaisNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisNulosP);
			globaisNulosP.setBounds(470, 35, 55, 17);

			//---- globaisBrancosP ----
			globaisBrancosP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoGlobal().getTotEleitores() * el.getResultadoGlobal().getBrancos())));
			globaisBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(globaisBrancosP);
			globaisBrancosP.setBounds(470, 10, 55, 17);

			//---- label12 ----
			label12.setText("Votos brancos:");
			label12.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(label12);
			label12.setBounds(265, 215, 100, 17);

			//---- circuloBrancosN ----
			circuloBrancosN.setText(String.valueOf(el.getResultadoCirculo(1).getBrancos()));
			circuloBrancosN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosN);
			circuloBrancosN.setBounds(385, 215, 80, 17);

			//---- circuloBrancosP ----
			circuloBrancosP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoCirculo(1).getTotEleitores() * el.getResultadoCirculo(1).getBrancos())));
			circuloBrancosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloBrancosP);
			circuloBrancosP.setBounds(480, 215, 60, 17);

			//---- circuloNulosP ----
			circuloNulosP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoCirculo(1).getTotEleitores() * el.getResultadoCirculo(1).getNulos())));
			circuloNulosP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloNulosP);
			circuloNulosP.setBounds(480, 240, 60, 17);

			//---- circuloAbstencaoP ----
			circuloAbstencaoP.setText(String.valueOf((int)Math.round(100.0 / el.getResultadoCirculo(1).getTotEleitores() * el.getResultadoCirculo(1).getAbstencao())));
			circuloAbstencaoP.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoP);
			circuloAbstencaoP.setBounds(480, 265, 60, 17);

			//---- circuloAbstencaoN ----
			circuloAbstencaoN.setText(String.valueOf(el.getResultadoCirculo(1).getAbstencao()));
			circuloAbstencaoN.setFont(new Font("Arial", Font.PLAIN, 14));
			ResultadosARContentPane.add(circuloAbstencaoN);
			circuloAbstencaoN.setBounds(385, 265, 80, 17);

			//---- circuloNulosN ----
			circuloNulosN.setText(String.valueOf(el.getResultadoCirculo(1).getNulos()));
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
				scrollCirculoExpanded.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						scrollCirculoExpandedMouseEntered(e);
					}
				});

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
				tableResultadosCirculo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableResultadosCirculo.getColumnModel().getColumn(4).setPreferredWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setMinWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setWidth(0);
				tableResultadosCirculo.getColumnModel().getColumn(4).setMaxWidth(0);
				scrollCirculoExpanded.setViewportView(tableResultadosCirculo);
			}
			ResultadosARContentPane.add(scrollCirculoExpanded);
			scrollCirculoExpanded.setBounds(15, 295, 560, 135);
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
						"Nome do candidato", "B.I./C.C.", "Sigla", "Tipo", null
					}
				) {
					boolean[] columnEditable = new boolean[] {
						false, true, true, true, true
					};
					@Override
					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return columnEditable[columnIndex];
					}
				});
				tableResultadosCirculo2.setFont(new Font("Arial", Font.PLAIN, 12));
				tableResultadosCirculo2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableResultadosCirculo2.getColumnModel().getColumn(4).setPreferredWidth(0);
				tableResultadosCirculo2.getColumnModel().getColumn(4).setMinWidth(0);
				tableResultadosCirculo2.getColumnModel().getColumn(4).setWidth(0);
				tableResultadosCirculo2.getColumnModel().getColumn(4).setMaxWidth(0);
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
			ResultadosARContentPane.add(comboBox1);
			comboBox1.setBounds(15, 245, 185, 25);

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
	private JComboBox<String> comboBox1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}