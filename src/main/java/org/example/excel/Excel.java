package org.example.excel;

import org.example.entity.Employee;

import java.util.concurrent.ConcurrentHashMap;

public interface Excel {
    public ConcurrentHashMap<String, Employee> readExcelData(String filePath);
}
