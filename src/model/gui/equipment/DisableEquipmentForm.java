package model.gui.equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.entities.Option;
import model.services.equipment.EquipmentService;
import model.services.equipment.EquipmentTableModel;

public class DisableEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(400, 150);

	private JComboBox<String> comboBox_Reason;

	private JLabel labelError_Reason;

	private EquipmentTableModel model;
	private Equipment equipment;
	private List<Option> options;
	private int lineSelected;

	public DisableEquipmentForm(EquipmentTableModel model, Equipment equipment, List<Option> options,
			int lineSelected) {
		this.model = model;
		this.equipment = equipment;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Reason");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		add(createPanelMain());

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		addLabelsAndComboBoxes(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabelsAndComboBoxes(JPanel panel) {
		final JLabel label_Reason = new JLabel("Reason:");
		label_Reason.setBounds(20, 20, 50, 25);
		panel.add(label_Reason);

		comboBox_Reason = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("REASON-EQUIPMENT") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Reason.setSelectedIndex(-1);
		comboBox_Reason.setBounds(80, 20, 90, 25);
		panel.add(comboBox_Reason);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Reason = new JLabel("");
		labelError_Reason.setForeground(Color.RED);
		labelError_Reason.setBounds(180, 20, 240, 25);
		panel.add(labelError_Reason);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(90, 70, 100, 25);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(200, 70, 100, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				equipment = getFormData();
				EquipmentService service = new EquipmentService();
				service.disable(equipment);
				model.updateEquipment(lineSelected, equipment);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Equipment successfully disabled", "Success disabling object",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error disabling object",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private Equipment getFormData() {
		Equipment equipment = this.equipment;

		ValidationException exception = new ValidationException("Validation error");

		// Insert Status
		equipment.setStatus("DISABLED");

		// Validation Reason
		if (comboBox_Reason.getSelectedIndex() < 0 || comboBox_Reason.getSelectedItem() == null) {
			exception.addError("reason", "It is necessary to select a reason!");
		} 
		else {
			equipment.setReason(comboBox_Reason.getSelectedItem().toString());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return equipment;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Reason.setText(fields.contains("reason") ? errors.get("reason") : "");
	}
}
