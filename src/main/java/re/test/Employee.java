package re.test;

class Employee {
    int id;
    String firstName;
    String lastName;
    int salary;
    Integer managerId;
    
    public Employee(int id, String firstName, String lastName, int salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }
}