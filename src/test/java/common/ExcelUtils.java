package common;

import commonPackage.ConfigLoader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static final ConfigLoader configLoader = new ConfigLoader();
    public static Map<String, Map<String, String>> completeSheetData;
    public static String Excel_Path = System.getProperty("user.dir") + configLoader.getProperty("excelfilepath");

    public static Map<String, Map<String, String>> readExcelData(String sheetName, String testCase) throws IOException {
        Map<String, Map<String, String>> completeSheetData = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(new File(Excel_Path));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            List<String> columnHeaders = getColumnHeaders(sheet);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Start from 1 to skip header
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < columnHeaders.size(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        rowData.put(columnHeaders.get(j), getCellValueAsString(cell));
                    }
                }

                // Use the test case name from the first column as the key in completeSheetData
                String testCaseName = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "Unknown";
                completeSheetData.put(testCaseName, rowData);

                // Check if this row corresponds to the specific test case we're looking for
                if (testCaseName.equals(testCase)) {
//                    System.out.println("Data for test case: " + testCase);
                    for (int k = 1; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        String columnData = cell != null ? cell.toString() : "NULL";
//                        System.out.println("Column " + (k + 1) + ": " + columnData);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return completeSheetData;
    }

    private static List<String> getColumnHeaders(XSSFSheet sheet) {
        Row headerRow = sheet.getRow(0);
        List<String> columnHeaders = new ArrayList<>();

        for (Cell cell : headerRow) {
            columnHeaders.add(cell.getStringCellValue());
        }

        return columnHeaders;
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
