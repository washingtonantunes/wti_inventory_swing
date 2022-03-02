package model.util;

import java.util.List;

import javax.swing.JOptionPane;

import application.LoadData;
import model.entities.Change;
import model.services.change.ChangeService;

public class SortListChange {
	
	private final ChangeService service = new ChangeService();

	private List<Change> listOriginal;
	private List<Change> listOrderly;

	public SortListChange() {
		listOriginal = LoadData.getChangesList();
		sort();
	}

	private void sort() {
		
		int larger = 0;

		for (Change change : listOriginal) {

			if (change.getId() > larger) {
				larger = change.getId();
			}
		}

		if (larger != listOriginal.size()) {
			for (Change change : listOriginal) {
				final int idOld = change.getId();
				change.setId(++larger);
				service.updateSort(idOld, change.getId());
			}

			listOrderly = listOriginal;
			listOrderly.sort((c1, c2) -> c1.getDate().compareTo(c2.getDate()));

			int index = 0;

			for (Change change : listOrderly) {
				final int idOld = change.getId();
				change.setId(++index);
				service.updateSort(idOld, change.getId());
			}
			
			service.toSetDefault(++larger);
			
			JOptionPane.showMessageDialog(null, "Ordered list", null, JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "List is already sorted", null, JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
