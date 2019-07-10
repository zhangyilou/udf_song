package Wanwan;

//public class Haha {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
////        scanner.nextLine():用户输入字符串
////        scanner.nextInt():用户输入整数
////        scanner.nextDouble():输入浮点型
//        System.out.print("input your last grade...");
//        int age1 = scanner.nextInt();
//        System.out.print("input your this grade...");
//        int age2 = scanner.nextInt();
//        float rate = (age2 - age1)/(float)age1 *100;
//        System.out.printf("提升百分比：%.2f%%",rate);
//    }
//}

//public class Haha{
//    public static void main(String[] args){
//        Scanner scanner =  new Scanner(System.in);
//            System.out.println("输入你的体重(kg)");
//            float weight = scanner.nextFloat();
//            System.out.println("输入你的身高(m)");
//            float height = scanner.nextFloat();
//        float BMI = weight * height* height;
//        if(BMI < 18.5){
//            System.out.println("过轻");
//        }else if(18.5<=BMI && BMI<25){
//            System.out.println("正常");
//        }else if(25<=BMI && BMI<28){
//            System.out.println("过重");
//        }else {
//            System.out.println("非常胖");
//        }
//
//    }
//}

//public class Haha{
//    public static void main(String[] args){
//        String option = "石头";
//        switch (option){
//            case "石头":
//                System.out.println("select 1");
//                break;
//            case "剪刀":
//                System.out.println("select 2");
//                break;
//            default:
//                System.out.println("select 3");
//                break;
//        String fruit = "apple";
//        switch (fruit){
//            case "apple" -> System.out.println("select apple");
//            case "pear" -> {
//                System.out.println("select");
//                System.out.println("hah");
//            }
//            default -> System.out.println("no fruit select");
//        }
//    }
//}

public class Haha {
    public static void main(String[] args) {
        // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
        Income[] incomes = new Income[] {
                new Income(3000),
                new Salary(7500),
                new StateCouncilSpecialAllowance(15000)
        };
        System.out.println(totalTax(incomes));
    }

    public static double totalTax(Income... incomes) {
        double total = 0;
        for (Income income: incomes) {
            total = total + income.getTax();
        }
        return total;
    }
}

class Income {
    protected double income;

    public Income(double income) {
        this.income = income;
    }

    public double getTax() {
        return income * 0.1; // 税率10%
    }
}

class Salary extends Income {
    public Salary(double income) {
        super(income);
    }

    @Override
    public double getTax() {
        if (income <= 5000) {
            return 0;
        }
        return (income - 5000) * 0.2;
    }
}

class StateCouncilSpecialAllowance extends Income {
    public StateCouncilSpecialAllowance(double income) {
        super(income);
    }

    @Override
    public double getTax() {
        return 0;
    }
}