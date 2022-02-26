package model.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class ProgressBar extends JDialog {

	private static final long serialVersionUID = 1L;

	private JProgressBar bar = new JProgressBar();
	
	private int time;

	public ProgressBar(int time) {
		this.time = time;
		initComponents();
		initBar();
		new Temporizador().start();
	}

	private void initComponents() {
		setTitle("Loading Data");
		setLayout(null);
		setPreferredSize(new Dimension(400, 120));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		pack();
		setLocationRelativeTo(null);
	}
	
	private void initBar() {
		bar.setBounds(20, 20, 340, 50);
		bar.setStringPainted(true);
		bar.setValue(50);
		bar.setMaximum(1000);
		bar.setForeground(Color.GREEN);
		add(bar);
	}

	public class Temporizador extends Thread {
		public void run() {
			while (bar.getValue() < time) {
				try {
					sleep(10);
					bar.setValue(bar.getValue() + 7);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Runtime.getRuntime().exit(0);
				}
			}
			if (bar.getValue() == time) {
				dispose();
			}
		}
	}
}
