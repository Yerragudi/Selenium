package com.dxc.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileHandler {

	public static List<String> getCellsAt(int rowNumber) {
		try {
			File file = new File(ConfigFileHandler.getProperty("excelFileLocation"));
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb
					.getSheetAt(Integer.parseInt(ConfigFileHandler.getProperty("excel_inputData_worksheet_Index")));
			XSSFRow row = sheet.getRow(rowNumber - 1);

			List<String> cells = new ArrayList<String>();
			for (Cell cell : row) {
//				cell.getCellType().toString().equals("NUMERIC")
				DataFormatter dataFormatter = new DataFormatter();
				cells.add(dataFormatter.formatCellValue(cell).trim());
			}
			wb.close();
			return cells;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	private static void writeDataAt(String excelFileLocation, int workSheetIndex, int rowNumber, int cellNumber,
			String data) {
		try {
			File file = new File(excelFileLocation);
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb.getSheetAt(workSheetIndex);
			XSSFRow row = sheet.getRow(rowNumber - 1);

			Cell cell = row.createCell(cellNumber);
			cell.setCellValue(data);

			FileOutputStream outputStream = new FileOutputStream(file);
			wb.write(outputStream);
			outputStream.close();
			wb.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void writeInputDataAt(int rowNumber, int cellNumber, String data) {
		String excelFileLocation = ConfigFileHandler.getProperty("excelFileLocation");
		int workSheetIndex = Integer.parseInt(ConfigFileHandler.getProperty("excel_inputData_worksheet_Index"));
		ExcelFileHandler.writeDataAt(excelFileLocation, workSheetIndex, rowNumber, cellNumber, data);
	}

	public static void logTestResult(int rowNumber, int cellNumber, String data) {
		String excelFileLocation = ConfigFileHandler.getProperty("excelFileLocation");
		int workSheetIndex = Integer.parseInt(ConfigFileHandler.getProperty("excel_logData_worksheet_Index"));
		ExcelFileHandler.writeDataAt(excelFileLocation, workSheetIndex, rowNumber, cellNumber, data);
	}
}
