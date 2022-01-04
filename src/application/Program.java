package application;

import javax.swing.SwingUtilities;

import model.gui.MainWindow;

public class Program {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}
}
