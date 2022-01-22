package model.gui.equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.util.SearchUtils;

public class SearchEquipment extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(500, 120);

	private JTextField textField_Search;
	private JLabel labelError_Search;

	private JTable table;

	public SearchEquipment(JTable table) {
		this.table = table;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Search Equipment");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabelsandTextFields(panel);
		addButtons(panel);

		return panel;
	}
	
	private void addLabelsandTextFields(JPanel panel) {
		final JLabel label_Search = new JLabel("Enter to search");
		label_Search.setBounds(20, 15, 130, 25);
		panel.add(label_Search);

		textField_Search = new JTextField();
		textField_Search.setBounds(170, 15, 130, 25);
		panel.add(textField_Search);

		labelError_Search = new JLabel();
		labelError_Search.setForeground(Color.RED);
		labelError_Search.setBounds(170, 45, 200, 25);
		panel.add(labelError_Search);
	}
	
	private void addButtons(JPanel panel) {
		final JButton buttonSearch = new JButton("Search");
		buttonSearch.setBounds(320, 15, 130, 25);
		buttonSearch.addActionListener(new buttonSearchListener());
		getRootPane().setDefaultButton(buttonSearch);
		panel.add(buttonSearch);
	}

	private class buttonSearchListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			labelError_Search.setText("");
			repaint();

			int index = 0;

			if (textField_Search.getText() == null || textField_Search.getText().trim().equals("")) {
				labelError_Search.setText("Enter to search");
			} 
			else {
				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {

						if (table.getValueAt(i, j) != null) {
							if (table.getModel().getValueAt(i, j).equals(textField_Search.getText().toUpperCase())) {
								table.setRowSelectionInterval(i, i);
								index = -1;
								SearchUtils.selectAndScroll(table, i);
								dispose();
							}
						}
					}
				}
				if (index != -1) {
					labelError_Search.setText("Not found");
				}
			}

		}
	}
}
