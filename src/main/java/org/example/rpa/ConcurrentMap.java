package org.example.rpa;

import org.example.entity.Employee;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMap {
    public static ConcurrentHashMap<String, Employee> employeeMap = new ConcurrentHashMap<>();

    public static void putMap(String employId, Employee employee) {
        employeeMap.put(employId, employee);
    }

    public static ConcurrentHashMap<String, Employee> getEmployeeMap() {
        return employeeMap;
    }

    public static void putAll(ConcurrentHashMap<String, Employee> map) {
        employeeMap.putAll(map);
    }

    public static void remove(String id) {
        employeeMap.remove(id);
    }
}
