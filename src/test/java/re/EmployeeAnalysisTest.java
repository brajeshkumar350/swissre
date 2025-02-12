package re;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import re.test.Employee;
import re.test.EmployeeAnalysis;

public class EmployeeAnalysisTest {
    

    @Test
    void testReadEmployeeData() {
        EmployeeAnalysis.readEmployeeData("test_employees.csv");
        assertFalse(EmployeeAnalysis.employees.isEmpty(), "Employees should be loaded from CSV");
    }

    @Test
    void testAnalyzeSalaries() {
        Employee manager = new Employee(1, "Manager", "One", 60000, null);
        Employee emp1 = new Employee(2, "Employee", "Two", 40000, 1);
        Employee emp2 = new Employee(3, "Employee", "Three", 42000, 1);
        
        EmployeeAnalysis.employees.put(1, manager);
        EmployeeAnalysis.employees.put(2, emp1);
        EmployeeAnalysis.employees.put(3, emp2);
        EmployeeAnalysis.managerToSubordinates.put(1, Arrays.asList(emp1, emp2));

        // Capture console output (if needed) to validate salary violations
        EmployeeAnalysis.analyzeSalaries();
        assertTrue(manager.salary >= 1.2 * 41000 && manager.salary <= 1.5 * 41000, "Manager's salary should be within limits");
    }

    @Test
    void testAnalyzeReportingLines() {
        Employee ceo = new Employee(1, "CEO", "Boss", 100000, null);
        Employee m1 = new Employee(2, "Manager1", "M1", 80000, 1);
        Employee m2 = new Employee(3, "Manager2", "M2", 70000, 2);
        Employee m3 = new Employee(4, "Manager3", "M3", 60000, 3);
        Employee m4 = new Employee(5, "Manager4", "M4", 50000, 4);
        Employee emp = new Employee(6, "Employee", "E1", 40000, 5);

        EmployeeAnalysis.employees.put(1, ceo);
        EmployeeAnalysis.employees.put(2, m1);
        EmployeeAnalysis.employees.put(3, m2);
        EmployeeAnalysis.employees.put(4, m3);
        EmployeeAnalysis.employees.put(5, m4);
        EmployeeAnalysis.employees.put(6, emp);

        int depth = EmployeeAnalysis.getReportingDepth(6);
        assertEquals(5, depth, "Employee should have a reporting depth of 5");
    }
}
