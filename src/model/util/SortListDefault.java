package model.util;

import java.util.List;

import javax.swing.JOptionPane;

import application.LoadData;
import model.entities.Option;
import model.services.OptionService;

public class SortListDefault {
	
	private final OptionService service = new OptionService();

	private List<Option> listOriginal;
	private List<Option> listOrderly;

	public SortListDefault() {
		listOriginal = LoadData.getOptionList();
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
				service.update(idOld, option.getId());
			}

			listOrderly = listOriginal;
			listOrderly.sort((o1, o2) -> o1.getType().compareTo(o2.getType()));

			int index = 0;

			for (Option option : listOrderly) {
				final int idOld = option.getId();
				option.setId(++index);
				service.update(idOld, option.getId());

			}
		} else {
			JOptionPane.showMessageDialog(null, "Lista já está ordenada", null, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new SortListDefault();
	}
}
