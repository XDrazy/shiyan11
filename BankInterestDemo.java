// 用户信息类
class User {
    private String accountId;
    private String name;
    private double balance;
    private String type; // "current" or "saving"

    public User(String accountId, String name, double balance, String type) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
        this.type = type;
    }
    public String getAccountId() { return accountId; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public String getType() { return type; }
}

// 模板方法抽象类
abstract class InterestCalculator {
    // 模板方法
    public final void calculateInterest(String accountId) {
        User user = queryUserInfo(accountId);
        if (user == null) {
            System.out.println("未找到该帐号的用户信息！");
            return;
        }
        double interest = computeInterest(user);
        displayInterest(interest);
    }

    // 钩子方法：查询用户信息（可连接数据库，这里用假数据）
    protected User queryUserInfo(String accountId) {
        // 假设有两个账户
        if ("1001".equals(accountId))
            return new User("1001", "张三", 10000.0, "current");
        if ("2001".equals(accountId))
            return new User("2001", "李四", 20000.0, "saving");
        return null;
    }

    // 抽象方法：不同账户计算方式不同
    protected abstract double computeInterest(User user);

    // 统一显示方式
    protected void displayInterest(double interest) {
        System.out.printf("计算得到的利息为：%.2f元\n", interest);
    }
}

// 活期账户利息计算
class CurrentAccountCalculator extends InterestCalculator {
    // 假设活期利率为0.35%
    protected double computeInterest(User user) {
        return user.getBalance() * 0.0035;
    }
}

// 定期账户利息计算
class SavingAccountCalculator extends InterestCalculator {
    // 假设定期利率为2.25%
    protected double computeInterest(User user) {
        return user.getBalance() * 0.0225;
    }
}

// 测试主程序
public class BankInterestDemo {
    public static void main(String[] args) {
        InterestCalculator calc1 = new CurrentAccountCalculator();
        InterestCalculator calc2 = new SavingAccountCalculator();

        System.out.print("当前账户：");
        calc1.calculateInterest("1001"); // 张三，10000元，活期
        System.out.print("定期账户：");
        calc2.calculateInterest("2001"); // 李四，20000元，定期
        System.out.print("不存在的账户：");
        calc2.calculateInterest("9999"); // 不存在
    }
}
