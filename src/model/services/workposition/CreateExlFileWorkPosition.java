package model.services.workposition;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.WorkPosition;

public class CreateExlFileWorkPosition {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private List<WorkPosition> workPositions;

	private String filePath = null;

	public CreateExlFileWorkPosition(List<WorkPosition> workPositions, String path) {
		this.workPositions = workPositions;
		this.filePath = path;
		initComponents();
	}

	private void initComponents() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Work Position");

			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeight((short) 400);

			int rownum = 0;
			int cellnum = 0;
			Cell cell;
			Row row;

			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellValue("Work Point");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Location");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Floor");

			cell = row.createCell(cellnum++);
			cell.setCellValue("NetPoint");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Status");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Date Entry");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Reason");

			for (WorkPosition workPosition : workPositions) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getWorkPoint());

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getLocation());

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getFloor());

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getNetPoint());

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getStatus());

				cell = row.createCell(cellnum++);
				cell.setCellValue(sdf.format(workPosition.getDateEntry()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(workPosition.getReason());
			}

			FileOutputStream fileOut = new FileOutputStream(filePath.contains(".xls") ? filePath : filePath + ".xls");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			JOptionPane.showMessageDialog(null, "Excel file generated successfully!");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error exporting data: " + e.getMessage());
		}
	}
}
