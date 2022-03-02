package model.gui.equipment;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Equipment;
import model.gui.change.ChangesPanel;
import model.gui.project.ViewProjectForm;
import model.gui.user.ViewUserForm;
import model.gui.workposition.ViewWorkPositionForm;
import model.util.MyButton;
import model.util.MyLabel;

public class ViewEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZE_LABELS = 2;
	private final int SIZE_LABELS_SHOW = 6;

	private final int SIZEBUTTONS = 1;

	private final int COLOR_LABEL = 1;
	private final int COLOR_LABEL_SHOW = 2;

	private final int FONT = 1;

	private final int WIDTH_INTERNAL_PANEL = (150 + 300) + 20;

	private final int HEIGHT_TOP_PANEL = 10;
	private final int HEIGHT_FIELD_PANEL = 36 * 19;
	private final int HEIGHT_BUTTON_PANEL = 50;

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50;
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 84;

	private Equipment equipment;

	public ViewEquipmentForm(Equipment equipment) {
		this.equipment = equipment;
		initComponents();
	}
	
	private void initComponents() {
		setTitle("View Equipment");
		setModal(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
		setPreferredSize(new Dimension(WIDTH_MAIN_PANEL, HEIGHT_MAIN_PANEL));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		add(createTopPanel());
		add(createFieldsPanel());
		add(createButtonPanel());

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createTopPanel() {
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_TOP_PANEL));
		panel.setBackground(COLOR1);

		return panel;
	}
	
	private JPanel createFieldsPanel() {
		final JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		fieldsPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));

		final JLabel label_SerialNumber = new MyLabel("Serial Number:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_SerialNumber);

		final JLabel label_Show_SerialNumber = new MyLabel(equipment.getSerialNumber(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_SerialNumber);

		final JLabel label_HostName = new MyLabel("Host Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_HostName);

		final JLabel label_Show_HostName = new MyLabel(equipment.getHostName(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_HostName);

		final JLabel label_AddressMAC = new MyLabel("Address MAC:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_AddressMAC);

		final JLabel label_Show_AddressMAC = new MyLabel(equipment.getAddressMAC(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_AddressMAC);

		final JLabel label_Type = new MyLabel("Type:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Type);

		final JLabel label_Show_Type = new MyLabel(equipment.getType(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Type);

		final JLabel label_PatrimonyNumber = new MyLabel("Patrimony Number:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_PatrimonyNumber);

		final JLabel label_Show_PatrimonyNumber = new MyLabel(equipment.getPatrimonyNumber(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_PatrimonyNumber);

		final JLabel label_Brand = new MyLabel("Brand:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Brand);

		final JLabel label_Show_Brand = new MyLabel(equipment.getBrand(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Brand);

		final JLabel label_Model = new MyLabel("Model:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Model);

		final JLabel label_Show_Model = new MyLabel(equipment.getModel(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Model);
		
		final JLabel label_MemoryRam = new MyLabel("Memory Ram:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_MemoryRam);

		final JLabel label_Show_MemoryRam = new MyLabel(equipment.getMemoryRam(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_MemoryRam);
		
		final JLabel label_HardDisk = new MyLabel("Hard Disk:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_HardDisk);

		final JLabel label_Show_HardDisk = new MyLabel(equipment.getHardDisk(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_HardDisk);
		
		final JLabel label_CostType = new MyLabel("Cost Type:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_CostType);

		final JLabel label_Show_CostType = new MyLabel(equipment.getCostType(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_CostType);
		
		final JLabel label_Value = new MyLabel("Value:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Value);

		final JLabel label_Show_Value = new MyLabel(String.valueOf(equipment.getValue()), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Value);
		
		final JLabel label_NoteEntry = new MyLabel("Note Entry:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_NoteEntry);

		final JLabel label_Show_NoteEntry = new MyLabel(equipment.getNoteEntry(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_NoteEntry);
		
		final JLabel label_Note = new MyLabel("Note:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Note);

		final JLabel label_Show_Note = new MyLabel(equipment.getNote(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Note);
		
		final JLabel label_Location = new MyLabel("Location:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Location);

		final JLabel label_Show_Location = new MyLabel(equipment.getLocation(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Location);

		JLabel label_Status = new MyLabel("Status:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Status);

		final JLabel label_Show_Status = new MyLabel(equipment.getStatus(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Status);

		JLabel label_DateEntry = new MyLabel("Date Entry:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_DateEntry);

		final JLabel label_Show_DateEntry = new MyLabel(sdf.format(equipment.getDateEntry()), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_DateEntry);
		
		final JLabel label_Project = new MyLabel("Project:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Project);

		final JLabel label_Show_Project = new MyLabel(equipment.getProject() != null ? equipment.getProject().getName() : "", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		label_Show_Project.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_Project.addMouseListener(new MouseListenerProject());
		fieldsPanel.add(label_Show_Project);
		
		final JLabel label_User = new MyLabel("User:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_User);

		final JLabel label_Show_User = new MyLabel(equipment.getUser() != null ? equipment.getUser().getName() : "", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		label_Show_User.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_User.addMouseListener(new MouseListenerUser());
		fieldsPanel.add(label_Show_User);
		
		final JLabel label_WorkPosition = new MyLabel("Work Position:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_WorkPosition);

		final JLabel label_Show_WorkPosition = new MyLabel(equipment.getWorkPosition() != null ? equipment.getWorkPosition().getWorkPoint() : "", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		label_Show_WorkPosition.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_WorkPosition.addMouseListener(new MouseListenerWorkPosition());
		fieldsPanel.add(label_Show_WorkPosition);

		return fieldsPanel;
	}
	
	private JPanel createButtonPanel() {
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_BUTTON_PANEL));
		buttonPanel.setBackground(COLOR2);

		final JButton buttonChanges = new MyButton("Changes", SIZEBUTTONS);
		buttonChanges.addActionListener(new buttonChangesListener());
		buttonPanel.add(buttonChanges);

		final JButton buttonClose = new MyButton("Close", SIZEBUTTONS);
		buttonClose.addActionListener(new buttonCloseListener());
		buttonPanel.add(buttonClose);

		return buttonPanel;
	}

	private class buttonChangesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(equipment.getChanges());
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
	
	private class MouseListenerProject extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				if (equipment.getProject() != null) {
					new ViewProjectForm(equipment.getProject());
				}
			}
		}
	}

	private class MouseListenerUser extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				if (equipment.getUser() != null) {
					new ViewUserForm(equipment.getUser());
				}
			}
		}
	}

	private class MouseListenerWorkPosition extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				if (equipment.getWorkPosition() != null) {
					new ViewWorkPositionForm(equipment.getWorkPosition()).setVisible(true);
				}
			}
		}
	}
}
