import java.util.Scanner;

public class chatGPT {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入年份: ");
        int year = scanner.nextInt();

        if (isLeapYear(year)) {
            System.out.println(year + "年是闰年。");
        } else {
            System.out.println(year + "年是平年。");
        }

        System.out.println(year + "年每个月的天数如下：");
        for (int month = 1; month <= 12; month++) {
            int days = getDaysInMonth(year, month);
            System.out.println(month + "月: " + days + "天");
        }
    }

    // 判断是否为闰年
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 获取某年某月的天数
    public static int getDaysInMonth(int year, int month) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                throw new IllegalArgumentException("无效的月份：" + month);
        }
    }
}
