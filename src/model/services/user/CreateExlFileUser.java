package model.services.user;

import java.io.*;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.User;

public class CreateExlFileUser {

	private List<User> users;

	private String filePath = null;

	public CreateExlFileUser(List<User> users, String path) {
		this.users = users;
		this.filePath = path;
		initComponentsUser();
	}

	private void initComponentsUser() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Users");

			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeight((short) 400);

			int rownum = 0;
			int cellnum = 0;
			Cell cell;
			Row row;

			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellValue("Registration");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Name");

			cell = row.createCell(cellnum++);
			cell.setCellValue("CPF");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Phone");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Project");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Email");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Department");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Status");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Date Entry");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Reason");

			for (User user : users) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getRegistration());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getCPF());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getPhone());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getProject());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getEmail());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getDepartment());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getStatus());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getDateEntry());

				cell = row.createCell(cellnum++);
				cell.setCellValue(user.getReason());
			}

			FileOutputStream fileOut = new FileOutputStream(filePath.contains(".xls") ? filePath : filePath + ".xls");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			JOptionPane.showMessageDialog(null, "Excel file generated successfully!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error exporting data: " + e.getMessage());
		}
	}
}