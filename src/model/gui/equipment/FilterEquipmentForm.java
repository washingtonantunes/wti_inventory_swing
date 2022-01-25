package model.gui.equipment;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import model.services.equipment.EquipmentTableModel;

public class FilterEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(500, 120);

	private JTextField textField_Filter;

	private TableRowSorter<EquipmentTableModel> sorter;

	public FilterEquipmentForm(TableRowSorter<EquipmentTableModel> sorter) {
		this.sorter = sorter;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Filter Equipment");
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
		final JLabel label_Search = new JLabel("Enter to filter");
		label_Search.setBounds(20, 15, 130, 25);
		panel.add(label_Search);

		textField_Filter = new JTextField();
		textField_Filter.setBounds(170, 15, 130, 25);
		panel.add(textField_Filter);
	}
	
	private void addButtons(JPanel panel) {
		final JButton buttonSearch = new JButton("Filter");
		buttonSearch.setBounds(320, 15, 130, 25);
		buttonSearch.addActionListener(new buttonSearchListener());
		getRootPane().setDefaultButton(buttonSearch);
		panel.add(buttonSearch);
	}
	
	private class buttonSearchListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String text = textField_Filter.getText().toUpperCase();  
			if (text.length() == 0) {  
				sorter.setRowFilter(null);  
			} else {  
				sorter.setRowFilter(RowFilter.regexFilter(text));  
			} 
		}
	}
}
