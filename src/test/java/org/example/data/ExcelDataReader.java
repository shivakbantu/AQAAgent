package org.example.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelDataReader {
    private ExcelDataReader() {
    }

    public static List<Map<String, String>> readSheetAsMaps(String classpathResource, String sheetName) {
        try (InputStream is = ExcelDataReader.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) {
                throw new IllegalArgumentException("Excel resource not found on classpath: " + classpathResource);
            }

            try (Workbook workbook = new XSSFWorkbook(is)) {
                Sheet sheet = sheetName == null ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
                if (sheet == null) {
                    throw new IllegalArgumentException("Sheet not found: " + sheetName);
                }

                DataFormatter formatter = new DataFormatter();
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    return List.of();
                }

                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(formatter.formatCellValue(cell));
                }

                List<Map<String, String>> out = new ArrayList<>();
                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    Map<String, String> map = new LinkedHashMap<>();
                    for (int c = 0; c < headers.size(); c++) {
                        Cell cell = row.getCell(c);
                        map.put(headers.get(c), cell == null ? "" : formatter.formatCellValue(cell));
                    }
                    out.add(map);
                }
                return out;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel test data: " + classpathResource + " (sheet=" + sheetName + ")", e);
        }
    }
}

