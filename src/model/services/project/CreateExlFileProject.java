package model.services.project;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.Project;

public class CreateExlFileProject {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private List<Project> projects;

	private String filePath = null;

	public CreateExlFileProject(List<Project> projects, String path) {
		this.projects = projects;
		this.filePath = path;
		initComponentsProject();
	}

	private void initComponentsProject() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Projects");

			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeight((short) 400);

			int rownum = 0;
			int cellnum = 0;
			Cell cell;
			Row row;

			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellValue("Cost Center");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Name");

			cell = row.createCell(cellnum++);
			cell.setCellValue("City");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Status");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Date Entry");

			for (Project project : projects) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(project.getCostCenter());

				cell = row.createCell(cellnum++);
				cell.setCellValue(project.getName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(project.getCity());

				cell = row.createCell(cellnum++);
				cell.setCellValue(project.getStatus());

				cell = row.createCell(cellnum++);
				cell.setCellValue(sdf.format(project.getDateEntry()));
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
