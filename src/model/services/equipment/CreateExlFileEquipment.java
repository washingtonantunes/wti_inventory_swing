package model.services.equipment;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.Equipment;

public class CreateExlFileEquipment {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private List<Equipment> equipments;

	private String filePath = null;

	public CreateExlFileEquipment(List<Equipment> equipments, String path) {
		this.equipments = equipments;
		this.filePath = path;
		initComponents();
	}

	private void initComponents() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Equipments");

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
			cell.setCellValue("Host Name");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Address MAC");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Type");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Patrimony Number");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Brand");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Model");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Memory RAM");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Hard Disk");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Cost Type");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Value");

			cell = row.createCell(cellnum++);
			cell.setCellValue("NoteEntry");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Note");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Location");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Status");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Date Entry");
			
			cell = row.createCell(cellnum++);
			cell.setCellValue("User");
			
			cell = row.createCell(cellnum++);
			cell.setCellValue("Work Position");

			for (Equipment equipment : equipments) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getHostName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getAddressMAC());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getType());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getPatrimonyNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getBrand());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getModel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getMemoryRam());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getHardDisk());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getCostType());

				cell = row.createCell(cellnum++);
				cell.setCellValue(String.format("R$ %.2f", equipment.getValue()));

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getNote());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getNoteEntry());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getLocation());

				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getStatus());

				cell = row.createCell(cellnum++);
				cell.setCellValue(sdf.format(equipment.getDateEntry()));
				
				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getUser().getName());
				
				cell = row.createCell(cellnum++);
				cell.setCellValue(equipment.getWorkPosition().getWorkPoint());
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
