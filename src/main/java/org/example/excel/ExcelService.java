package org.example.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelService implements Excel {
    @Override
    public ConcurrentHashMap<String, Employee> readExcelData(String filePath) {
        ConcurrentHashMap<String, Employee> employees = new ConcurrentHashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String id = String.valueOf(row.getCell(0).getNumericCellValue());
                String name = row.getCell(1).getStringCellValue();
                String email = row.getCell(2).getStringCellValue();
                employees.put(id, new Employee(id, name, email));
                System.out.println(" employees "+employees);
            }
        } catch (IOException e) {
           throw new RuntimeException(e);
        }

        return employees;
    }
}
