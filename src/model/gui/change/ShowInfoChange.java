package model.gui.change;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowInfoChange extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private String info;
	private int size;
	
	public ShowInfoChange(String info) {
		this.info = info;
		this.size = (info.length() * 5) + 200;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Changes");
		setPreferredSize(new Dimension((size), 100));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);

		return panel;
	}
	
	private void addLabels(JPanel panel) {
		final JLabel label_Info = new JLabel(info);
		label_Info.setBounds(10, 20, size, 25);
		panel.add(label_Info);
	}
}
