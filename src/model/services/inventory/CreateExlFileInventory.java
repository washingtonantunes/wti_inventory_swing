package model.services.inventory;

import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import model.entities.Inventory;

public class CreateExlFileInventory {

	private List<Inventory> inventories;

	private String filePath = null;

	public CreateExlFileInventory(List<Inventory> inventories_, String path) {
		this.inventories = inventories_;
		this.filePath = path;
		initComponentsEquipment();
	}

	private void initComponentsEquipment() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Inventory");

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
			cell.setCellValue("Name Project");

			cell = row.createCell(cellnum++);
			cell.setCellValue("City Project");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Registration User");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Name User");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Serial Number Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Name Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Type Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Patimony Number Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Brand Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Model Equipment");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Serial Number Monitor 1");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Model Monitor 1");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Patimony Number Monitor 1");
			
			cell = row.createCell(cellnum++);
			cell.setCellValue("Serial Number Monitor 2");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Model Monitor 2");

			cell = row.createCell(cellnum++);
			cell.setCellValue("Patimony Number Monitor 2");

			for (Inventory inventory : inventories) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getWorkPosition().getWorkPoint());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getProject().getName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getProject().getCity());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getUser().getRegistration());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getUser().getName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getHostName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getType());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getPatrimonyNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getBrand());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getEquipment().getModel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor1().getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor1().getModel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor1().getPatrimonyNumber());
				
				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor2().getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor2().getModel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(inventory.getMonitor2().getPatrimonyNumber());
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
