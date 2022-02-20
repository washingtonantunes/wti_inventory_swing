package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String PATHTXT = "";

	public ObjectException(String msg) {
		super(msg);
	}

	private void writeError(String txt, int line, List<String> list) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATHTXT, true))) {
			for (int i = 0; i < list.size(); i++) {
				if (i == line) {
					bw.write(txt);
					bw.newLine();
				} else {
					bw.write(list.get(i));
					bw.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
