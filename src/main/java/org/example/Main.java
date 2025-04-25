package org.example;

import org.example.constants.Constants;
import org.example.entity.Employee;
import org.example.excel.Excel;
import org.example.excel.ExcelService;
import org.example.rpa.ConcurrentMap;
import org.example.rpa.Rpa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Excel excel = new ExcelService();
        ConcurrentHashMap<String, Employee> employeeConcurrentHashMap = excel.readExcelData(System.getProperty("user.dir") + "/rpa.xlsx");
        startRpaProcessing(employeeConcurrentHashMap);
    }

    private static void startRpaProcessing(ConcurrentHashMap<String, Employee> employeeMap) {
        if (employeeMap == null || employeeMap.isEmpty()) return;

        ConcurrentMap.putAll(employeeMap);
        while (true) {
            int chromeInstances = getChromeInstances();
            if (chromeInstances <= 0) break;

            ExecutorService executor = Executors.newFixedThreadPool(chromeInstances);

            List<Employee> employees = new ArrayList<>(ConcurrentMap.getEmployeeMap().values());
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < chromeInstances && i < employees.size(); i++) {
                futures.add(executor.submit(new Rpa(employees.get(i))));
            }

            executor.shutdown();

            for (Future<?> future : futures) {
                try {
                    future.get(); // This will block until the task completes
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            executor.shutdownNow();


        }
    }

    private static int getChromeInstances() {
        int mapSize = ConcurrentMap.getEmployeeMap().size();
        return Math.min(mapSize, Constants.NUMBER_OF_CHROME_INSTANCES);
    }
}

