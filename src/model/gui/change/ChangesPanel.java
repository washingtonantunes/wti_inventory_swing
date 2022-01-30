package model.gui.change;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.entities.Change;
import model.services.change.ChangeTableModel;
import model.services.change.TableChange;

public class ChangesPanel extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private JScrollPane scrollPane;

	private ChangeTableModel model;

	private List<Change> changes;

	public ChangesPanel(List<Change> changes) {
		this.changes = changes;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createTable());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Changes");
		setPreferredSize(new Dimension(948, 350));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JScrollPane createTable() {
		model = new ChangeTableModel(changes);

		table = new TableChange(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int lineSelected = table.getSelectedRow();
				String info = (String) table.getValueAt(lineSelected, 2);
				if (info.length() > 80) {
					new ShowInfoChange(info).setVisible(true);
				}
			}
		});

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}
}
