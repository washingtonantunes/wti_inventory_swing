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
import model.gui.user.ViewUserForm;
import model.gui.workposition.ViewWorkPositionForm;

public class ViewEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 160;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 110;
	private final int HEIGHT_LABEL = 30;

	private final int WIDTH_LABEL_SHOW = 300;
	private final int HEIGHT_LABEL_SHOW = 30;

	private final int widthPanel = WIDTH_LABEL + WIDTH_LABEL_SHOW + 50; // largura
	private final int heightPanel = (30 * 18) + 140; // altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);

	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private Equipment equipment;

	public ViewEquipmentForm(Equipment equipment) {
		this.equipment = equipment;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View Equipment");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addLabelsShow(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabels(JPanel panel) {
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setForeground(COLOR1);
		label_SerialNumber.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_SerialNumber);

		final JLabel label_HostName = new JLabel("Host Name:");
		label_HostName.setForeground(COLOR1);
		label_HostName.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_HostName);

		final JLabel label_AddressMAC = new JLabel("Address MAC:");
		label_AddressMAC.setForeground(COLOR1);
		label_AddressMAC.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_AddressMAC);

		final JLabel label_Type = new JLabel("Type:");
		label_Type.setForeground(COLOR1);
		label_Type.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Type);

		final JLabel label_PatrimonyNumber = new JLabel("Patrimony Number:");
		label_PatrimonyNumber.setForeground(COLOR1);
		label_PatrimonyNumber.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_PatrimonyNumber);

		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setForeground(COLOR1);
		label_Brand.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Brand);

		final JLabel label_Model = new JLabel("Model:");
		label_Model.setForeground(COLOR1);
		label_Model.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Model);

		final JLabel label_MemoryRam = new JLabel("Memory Ram:");
		label_MemoryRam.setForeground(COLOR1);
		label_MemoryRam.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_MemoryRam);

		final JLabel label_HardDisk = new JLabel("Hard Disk:");
		label_HardDisk.setForeground(COLOR1);
		label_HardDisk.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_HardDisk);

		final JLabel label_CostType = new JLabel("Cost Type:");
		label_CostType.setForeground(COLOR1);
		label_CostType.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_CostType);

		final JLabel label_Value = new JLabel("Value:");
		label_Value.setForeground(COLOR1);
		label_Value.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Value);

		final JLabel label_NoteEntry = new JLabel("Note Entry:");
		label_NoteEntry.setForeground(COLOR1);
		label_NoteEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_NoteEntry);

		final JLabel label_Note = new JLabel("Note:");
		label_Note.setForeground(COLOR1);
		label_Note.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Note);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setForeground(COLOR1);
		label_Location.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Location);

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("Date Entry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_DateEntry);

		final JLabel label_User = new JLabel("User:");
		label_User.setForeground(COLOR1);
		label_User.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_User);

		final JLabel label_WorkPosition = new JLabel("Work Position:");
		label_WorkPosition.setForeground(COLOR1);
		label_WorkPosition.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_WorkPosition);
	}

	private void addLabelsShow(JPanel panel) {
		final JLabel label_Show_SerialNumber = new JLabel(equipment.getSerialNumber());
		label_Show_SerialNumber.setForeground(COLOR2);
		label_Show_SerialNumber.setBounds(COLUMN2, line = 30, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_SerialNumber);

		final JLabel label_Show_HostName = new JLabel(equipment.getHostName());
		label_Show_HostName.setForeground(COLOR2);
		label_Show_HostName.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_HostName);

		final JLabel label_Show_AddressMAC = new JLabel(equipment.getAddressMAC());
		label_Show_AddressMAC.setForeground(COLOR2);
		label_Show_AddressMAC.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_AddressMAC);

		final JLabel label_Show_Type = new JLabel(equipment.getType());
		label_Show_Type.setForeground(COLOR2);
		label_Show_Type.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Type);

		final JLabel label_Show_PatrimonyNumber = new JLabel(equipment.getPatrimonyNumber());
		label_Show_PatrimonyNumber.setForeground(COLOR2);
		label_Show_PatrimonyNumber.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_PatrimonyNumber);

		final JLabel label_Show_Brand = new JLabel(equipment.getBrand());
		label_Show_Brand.setForeground(COLOR2);
		label_Show_Brand.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Brand);

		final JLabel label_Show_Model = new JLabel(equipment.getModel());
		label_Show_Model.setForeground(COLOR2);
		label_Show_Model.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Model);

		final JLabel label_Show_MemoryRam = new JLabel(equipment.getMemoryRam());
		label_Show_MemoryRam.setForeground(COLOR2);
		label_Show_MemoryRam.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_MemoryRam);

		final JLabel label_Show_HardDisk = new JLabel(equipment.getHardDisk());
		label_Show_HardDisk.setForeground(COLOR2);
		label_Show_HardDisk.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_HardDisk);

		final JLabel label_Show_CostType = new JLabel(equipment.getCostType());
		label_Show_CostType.setForeground(COLOR2);
		label_Show_CostType.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_CostType);

		final JLabel label_Show_Value = new JLabel(String.format("R$ %.2f", equipment.getValue()));
		label_Show_Value.setForeground(COLOR2);
		label_Show_Value.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Value);

		final JLabel label_Show_NoteEntry = new JLabel(equipment.getNoteEntry());
		label_Show_NoteEntry.setForeground(COLOR2);
		label_Show_NoteEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_NoteEntry);

		final JLabel label_Show_Note = new JLabel(equipment.getNote());
		label_Show_Note.setForeground(COLOR2);
		label_Show_Note.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Note);

		final JLabel label_Show_Location = new JLabel(equipment.getLocation());
		label_Show_Location.setForeground(COLOR2);
		label_Show_Location.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Location);

		final JLabel label_Show_Status = new JLabel(equipment.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(equipment.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_User = new JLabel(equipment.getUser() != null ? equipment.getUser().getName() : "");
		label_Show_User.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_User.setForeground(COLOR2);
		label_Show_User.addMouseListener(new MouseListenerUser());
		label_Show_User.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_User);

		final JLabel label_Show_WorkPosition = new JLabel(
				equipment.getWorkPosition() != null ? equipment.getWorkPosition().getWorkPoint() : "");
		label_Show_WorkPosition.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_WorkPosition.setForeground(COLOR2);
		label_Show_WorkPosition.addMouseListener(new MouseListenerWorkPosition());
		label_Show_WorkPosition.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_WorkPosition);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(positionButton, 600, 120, 30);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 600, 120, 30);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(equipment.getChanges());
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private class MouseListenerUser extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				String user = equipment.getUser().toString();
				if (user != null) {
					new ViewUserForm(equipment.getUser()).setVisible(true);
				}
			}
		}
	}

	private class MouseListenerWorkPosition extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				String workPosition = equipment.getWorkPosition().toString();
				if (workPosition != null) {
					new ViewWorkPositionForm(equipment.getWorkPosition()).setVisible(true);
				}
			}
		}
	}
}
