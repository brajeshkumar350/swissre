package re.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class EmployeeAnalysis {
    private static Map<Integer, Employee> employees = new HashMap<>();
    private static Map<Integer, List<Employee>> managerToSubordinates = new HashMap<>();
    
    public static void main(String[] args) {
        String fileName = "D:\\swissre\\src\\main\\resources\\employees.csv";
        readEmployeeData(fileName);
        analyzeSalaries();
        analyzeReportingLines();
    }

    private static void readEmployeeData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                int salary = Integer.parseInt(parts[3]);
                Integer managerId = parts.length > 4 && !parts[4].isEmpty() ? Integer.parseInt(parts[4]) : null;
                
                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);
                
                if (managerId != null) {
                    managerToSubordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeSalaries() {
        for (Map.Entry<Integer, List<Employee>> entry : managerToSubordinates.entrySet()) {
            Employee manager = employees.get(entry.getKey());
            List<Employee> subordinates = entry.getValue();
            
            double avgSalary = subordinates.stream().mapToInt(emp -> emp.salary).average().orElse(0);
            double minAllowed = avgSalary * 1.2;
            double maxAllowed = avgSalary * 1.5;
            
            if (manager.salary < minAllowed) {
                System.out.println(manager.firstName + " " + manager.lastName + " earns less than allowed by " + (minAllowed - manager.salary));
            } else if (manager.salary > maxAllowed) {
                System.out.println(manager.firstName + " " + manager.lastName + " earns more than allowed by " + (manager.salary - maxAllowed));
            }
        }
    }

    private static void analyzeReportingLines() {
        for (Employee employee : employees.values()) {
            int depth = getReportingDepth(employee.id);
            if (depth > 4) {
                System.out.println(employee.firstName + " " + employee.lastName + " has a reporting line too long by " + (depth - 4));
            }
        }
    }

    private static int getReportingDepth(int employeeId) {
        int depth = 0;
        Employee employee = employees.get(employeeId);
        while (employee.managerId != null) {
            depth++;
            employee = employees.get(employee.managerId);
        }
        return depth;
    }
}
