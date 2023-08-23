package org.shopnow.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public static List<List<Object>> readSheet(String filename, String sheetName) {
        List<List<Object>> data = new ArrayList<>();
        Logger.Log(String.format("Reading File:%s Sheet:%s", filename, sheetName));

        try {
            FileInputStream fis = new FileInputStream(filename);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                Logger.Error("Sheet not found: " + sheetName);
                return data;
            }

            for (Row row : sheet) {
                List<Object> rowData = new ArrayList<>();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    rowData.add(cell.toString()); // Read cell value as string
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            Logger.Trace("Exception Occurred | message: " + e);
        }

        return data;
    }

    public static boolean writeTable(String filepath, String sheetName, List<List<Object>> table, boolean openAfterWrite) {
        try {
            File file = new File(filepath);

            File parentDir = file.getParentFile();
            if (parentDir == null) {
                parentDir = new File("");
            }

            parentDir.mkdirs();

            Workbook workbook;
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                if (sheetName.isEmpty()) {
                    sheet = workbook.createSheet();
                } else {
                    sheet = workbook.createSheet(sheetName);
                }
            } else {
                workbook.removeSheetAt(workbook.getSheetIndex(sheet));
                sheet = workbook.createSheet(sheetName);
            }

            for (int i = 0; i < table.size(); i++) {
                Row dataRow = sheet.getRow(i);
                if (dataRow == null) {
                    dataRow = sheet.createRow(i);
                }

                for (int j = 0; j < table.get(i).size(); j++) {
                    Cell cell = dataRow.getCell(j);
                    if (cell == null) {
                        cell = dataRow.createCell(j, CellType.STRING);
                    }
                    cell.setCellValue(table.get(i).get(j).toString());
                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            System.out.println("Results written to " + filepath);
            if (openAfterWrite) {
                Runtime.getRuntime().exec("cmd /c start " + filepath);
            }

            return true;
        } catch (Exception e) {
            Logger.Trace("Exception Occurred | message: " + e);
        }

        return false;
    }
}
