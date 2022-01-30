package model.services.monitor;

import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.Monitor;

public class CreateExlFileMonitor {

	private List<Monitor> monitors;

	private String filePath = null;

	public CreateExlFileMonitor(List<Monitor> monitors, String path) {
		this.monitors = monitors;
		this.filePath = path;
		initComponentsMonitor();
	}

	private void initComponentsMonitor() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Monitors");

			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeight((short) 400);

			int rownum = 0;
			int cellnum = 0;
			Cell cell;
			Row row;

			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellValue("Serial Number");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Brand");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Model");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Patrimony Number");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Status");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Date Entry");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Reason");

			for (Monitor monitor : monitors) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getBrand());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getModel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getPatrimonyNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getStatus());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getDateEntry());

				cell = row.createCell(cellnum++);
				cell.setCellValue(monitor.getReason());
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
