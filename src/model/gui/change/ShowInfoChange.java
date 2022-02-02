package model.gui.change;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowInfoChange extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final Color COLOR1 = new Color(0, 65, 83);
	
	private String info;
	private List<String> list;
	private int size;
	
	public ShowInfoChange(String info) {
		this.info = info;
		this.list = formatedString();
		this.size = (list.size() * 5) + 150;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Changes");
		setPreferredSize(new Dimension(350, size));
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
		for (int i =0; i < list.size(); i++) {
			final JLabel label_Info = new JLabel(list.get(i));
			label_Info.setForeground(COLOR1);
			label_Info.setBounds(20, (i * 15), 350, 25);
			panel.add(label_Info);
		}
	}
	
	private List<String> formatedString() {
		List<String> list = new ArrayList<String>();
		
		String[] fields = info.split(",");
		
		for (String s : fields) {
			if (s.contains("Fields Updated: ")) {
				
				list.add(s.substring(0, s.indexOf(":")+1));
				list.add(s.substring(s.indexOf(":")+1, s.length()));
			} else {
				list.add(s);
			}
		}
		
		return list;
	}
}
