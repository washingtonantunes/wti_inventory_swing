package model.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(1300, 710);

	public MainWindow() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("wTI Inventory");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		add(createPanelMain(), BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel createPanelMain() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel label_text = new JLabel("Welcome to wTI Inventory");
		label_text.setFont(new Font("fontLabel", ALLBITS, 50));
		label_text.setForeground(new Color(4, 77, 92));
		label_text.setBounds(120, 305, 600, 50);
		panel.add(label_text);
		
		JLabel label_icon = new JLabel((new ImageIcon(getClass().getResource("/model/icon/iconMain.jpg"))));
		label_icon.setBounds(820, 220, 330, 210);
		panel.add(label_icon);
		
		JLabel label_author = new JLabel("Author: Washington Antunes");
		label_author.setFont(new Font("fontLabel", ALLBITS, 10));
		label_author.setForeground(new Color(4, 77, 92));
		label_author.setBounds(1100, 630, 600, 50);
		panel.add(label_author);
		
		return panel;
	}
}
