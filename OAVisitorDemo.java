import java.util.*;

// 员工抽象接口
interface Employee {
    void accept(DepartmentVisitor visitor);
    String getName();
    int getWorkTime();
}

// 正式员工
class FullTimeEmployee implements Employee {
    private String name;
    private String department;
    private String level;
    private double basicSalary;
    private int workTime; // 实际工作时间
    private int overtime; // 加班时间
    private int leave;    // 请假时间

    public FullTimeEmployee(String name, String department, String level, double basicSalary, int workTime) {
        this.name = name;
        this.department = department;
        this.level = level;
        this.basicSalary = basicSalary;
        this.workTime = workTime;
        // 计算加班和请假
        if (workTime > 40) {
            this.overtime = workTime - 40;
            this.leave = 0;
        } else {
            this.overtime = 0;
            this.leave = 40 - workTime;
        }
    }
    public String getName() { return name; }
    public int getWorkTime() { return workTime; }
    public int getOvertime() { return overtime; }
    public int getLeave() { return leave; }
    public double getBasicSalary() { return basicSalary; }
    @Override
    public void accept(DepartmentVisitor visitor) {
        visitor.visit(this);
    }
}

// 临时工
class PartTimeEmployee implements Employee {
    private String name;
    private String position;
    private double hourlyWage;
    private int workTime;

    public PartTimeEmployee(String name, String position, double hourlyWage, int workTime) {
        this.name = name;
        this.position = position;
        this.hourlyWage = hourlyWage;
        this.workTime = workTime;
    }
    public String getName() { return name; }
    public int getWorkTime() { return workTime; }
    public double getHourlyWage() { return hourlyWage; }
    @Override
    public void accept(DepartmentVisitor visitor) {
        visitor.visit(this);
    }
}

// 访问者接口
interface DepartmentVisitor {
    void visit(FullTimeEmployee e);
    void visit(PartTimeEmployee e);
}

// 人力资源部访问者，统计工作时间
class HRDepartmentVisitor implements DepartmentVisitor {
    private int totalFullTime = 0;
    private int totalPartTime = 0;
    @Override
    public void visit(FullTimeEmployee e) {
        System.out.printf("正式员工：%s，实际工作时长：%d小时，加班：%d小时，请假：%d小时\n",
                e.getName(), e.getWorkTime(), e.getOvertime(), e.getLeave());
        totalFullTime += e.getWorkTime();
    }
    @Override
    public void visit(PartTimeEmployee e) {
        System.out.printf("临时工：%s，实际工作时长：%d小时\n", e.getName(), e.getWorkTime());
        totalPartTime += e.getWorkTime();
    }
    public void reportTotal() {
        System.out.println("正式员工总工作时长：" + totalFullTime);
        System.out.println("临时工总工作时长：" + totalPartTime);
    }
}

// 财务部访问者，统计工资
class FinanceDepartmentVisitor implements DepartmentVisitor {
    @Override
    public void visit(FullTimeEmployee e) {
        double salary = e.getBasicSalary();
        if (e.getWorkTime() > 40) {
            salary += (e.getWorkTime() - 40) * 100;
        } else if (e.getWorkTime() < 40) {
            int leaveHours = 40 - e.getWorkTime();
            double deduct = leaveHours * 80;
            if (deduct > salary) {
                salary = 0;
            } else {
                salary -= deduct;
            }
        }
        System.out.printf("正式员工：%s，本周工资：%.2f元\n", e.getName(), salary);
    }
    @Override
    public void visit(PartTimeEmployee e) {
        double salary = e.getWorkTime() * e.getHourlyWage();
        System.out.printf("临时工：%s，本周工资：%.2f元\n", e.getName(), salary);
    }
}

// 员工列表（对象结构）
class EmployeeList {
    private List<Employee> employees = new ArrayList<>();
    public void addEmployee(Employee e) {
        employees.add(e);
    }
    public void accept(DepartmentVisitor visitor) {
        for (Employee e : employees) {
            e.accept(visitor);
        }
    }
}

// 测试
public class OAVisitorDemo {
    public static void main(String[] args) {
        EmployeeList empList = new EmployeeList();
        empList.addEmployee(new FullTimeEmployee("张三", "研发", "P5", 8000, 45));
        empList.addEmployee(new FullTimeEmployee("李四", "财务", "P4", 6000, 36));
        empList.addEmployee(new PartTimeEmployee("王五", "安保", 25, 30));
        empList.addEmployee(new PartTimeEmployee("赵六", "清洁", 20, 25));

        System.out.println("=== 人力资源部报表 ===");
        HRDepartmentVisitor hrVisitor = new HRDepartmentVisitor();
        empList.accept(hrVisitor);
        hrVisitor.reportTotal();

        System.out.println("\n=== 财务部报表 ===");
        FinanceDepartmentVisitor financeVisitor = new FinanceDepartmentVisitor();
        empList.accept(financeVisitor);
    }
}
