/*
 * Created by JFormDesigner on Tue Dec 15 17:48:19 GMT 2015
 */

package Presentation;

import java.awt.*;
import javax.swing.*;

/**
 * @author Octavio Maia
 */
public class GerirPR {
	public GerirPR() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		GerirPR = new JFrame();

		//======== GerirPR ========
		{
			GerirPR.setResizable(false);
			GerirPR.setFont(new Font("Arial", Font.PLAIN, 12));
			GerirPR.setTitle("Gerir Elei\u00e7\u00e3o Presid\u00eancia da R\u00e9publica");
			Container GerirPRContentPane = GerirPR.getContentPane();
			GerirPRContentPane.setLayout(null);

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
			GerirPR.setSize(650, 500);
			GerirPR.setLocationRelativeTo(null);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JFrame GerirPR;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
