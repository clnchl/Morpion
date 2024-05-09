package vue;

import java.awt.Color;

import javax.swing.JPanel;

public class PanelPrincipal extends JPanel{
	public PanelPrincipal() {
		//toutes les propriétés communes à toutes les panels
		this.setBounds(20, 80, 860, 460 );
		this.setLayout(null);
		this.setBackground(new Color(255, 145, 77));
		
		this.setVisible(false);
	}

} 