package model.util;

import java.util.List;

import javax.swing.JOptionPane;

import application.LoadData;
import model.entities.Option;
import model.services.OptionService;

public class SortListOption {
	
	private final OptionService service = new OptionService();

	private List<Option> listOriginal;
	private List<Option> listOrderly;

	public SortListOption() {
		listOriginal = LoadData.getOptionsList();
		sort();
	}

	private void sort() {
		
		int larger = 0;

		for (Option option : listOriginal) {

			if (option.getId() > larger) {
				larger = option.getId();
			}
		}

		if (larger != listOriginal.size()) {
			for (Option option : listOriginal) {
				final int idOld = option.getId();
				option.setId(++larger);
				service.updateSort(idOld, option.getId());
			}

			listOrderly = listOriginal;
			listOrderly.sort((c1, c2) -> c1.getType().compareTo(c2.getType()));

			int index = 0;

			for (Option option : listOrderly) {
				final int idOld = option.getId();
				option.setId(++index);
				service.updateSort(idOld, option.getId());
			}
			
			service.toSetDefault(++larger);
			JOptionPane.showMessageDialog(null, "Ordered list", null, JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "List is already sorted", null, JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
