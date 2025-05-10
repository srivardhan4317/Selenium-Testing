package commonPackage;


import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActionHelper   {

    private static final ConfigLoader configLoader = new ConfigLoader();
    public static Map<String, Map<String, String>> completeSheetData;
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + configLoader.getProperty("/target/screenshots");
    public static String Excel_Path = System.getProperty("user.dir") + configLoader.getProperty("excelfilepath");


    public static Map<String, Map<String, String>> getExcelAsMap(String sheetName) throws IOException {
        System.out.println(Excel_Path);
        FileInputStream fis = new FileInputStream(new File(Excel_Path));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        completeSheetData = new HashMap<String, Map<String, String>>();
        List<String> columnHeader = new ArrayList<String>();
        Row row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            columnHeader.add(cellIterator.next().getStringCellValue());
        }
        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = row.getLastCellNum();
        for (int i = 0; i < rowCount; i++) {
            Map<String, String> singleRowData = new HashMap<String, String>();
            Row row1 = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row1.getCell(j);
                if (cell != null)
                    singleRowData.put(columnHeader.get(j), getCellValueAsString(cell));
            }
            completeSheetData.put(String.valueOf(i), singleRowData);
        }
        fis.close();
        return completeSheetData;
    }


    public static Map<String, Map<String, String>> readExcelData(String sheetName, String testCase) throws IOException {
        Map<String, Map<String, String>> completeSheetData = new HashMap<>();

        try (
                FileInputStream fis = new FileInputStream(new File(Excel_Path));
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            System.out.println(sheet);
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

                String testCaseName = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "Unknown";
                completeSheetData.put(testCaseName, rowData);

                if (testCaseName.equals(testCase)) {
                    for (int k = 1; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        String columnData = cell != null ? cell.toString() : "NULL";
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
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BLANK -> "";
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public static String captureScreenshot(WebDriver driver) {
        createScreenshotDir();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String screenshotName = "screenshot_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + screenshotName;

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(screenshot, new File(filePath));

            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    private static void createScreenshotDir() {
        Path path = Paths.get(SCREENSHOT_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.err.println("Failed to create screenshot directory: " + e.getMessage());
            }
        }
    }


    public void writeDataExcelColNum(int rowNum, int colNum, String value, String sheetName) throws IOException {
        FileInputStream inp = new FileInputStream(Excel_Path);
        Workbook wb = WorkbookFactory.create(inp);

        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet with name " + sheetName + " does not exist.");
        }

        inp.close();
        FileOutputStream fileOut = new FileOutputStream(Excel_Path);
        Row row = sheet.getRow(rowNum);
        row.createCell(colNum).setCellValue(value);
        wb.write(fileOut);
        fileOut.close();

    }

    public void writeDataExcel(int rowNum, String colName, String value, String sheetName) throws IOException {
        File file = new File(Excel_Path);
        if (!file.exists()) {
            throw new IllegalArgumentException("The specified file does not exist: " + Excel_Path);
        }
        try (FileInputStream inp = new FileInputStream(file)) {
            Workbook wb = WorkbookFactory.create(inp);

            Sheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet with name " + sheetName + " does not exist.");
            }

            Row headerRow = sheet.getRow(0);
            Integer colIndex = null;
            for (Cell cell : headerRow) {
                if (cell != null && cell.getStringCellValue().equalsIgnoreCase(colName)) {
                    colIndex = cell.getColumnIndex();
                    break;
                }
            }
            if (colIndex == null) {
                throw new IllegalArgumentException("Column name '" + colName + "' not found in the header row.");
            }
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            row.createCell(colIndex).setCellValue(value);
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                wb.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

