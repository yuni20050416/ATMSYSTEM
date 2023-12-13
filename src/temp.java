import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class temp {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("==欢迎进入ATM系统==");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择");
            int command = sc.nextInt();
            switch (command) {
                case 1://用户登录
                    break;
                case 2://用户开户
                    createAccount();
            }
        }
    }

    private void createAccount() {
        Account acc = new Account();
        while (true) {
            System.out.println("请您输入账户用户名");
            String name = sc.next();
            acc.setUserName(name);
            System.out.println("请您输入您的性别");
            char sex = sc.next().charAt(0);
            if (sex == '男' || sex == '女') {
                acc.setSex(sex);
                break;
            } else {
                System.out.println("您输入的性别不符合要求，只能是男或者女");
            }
        }
        while (true) {
            System.out.println("请您输入您的账户密码");
            String passWord = sc.next();
            System.out.println("请您确认您的账户密码");
            String okPassWord = sc.next();
            if (passWord.equals(okPassWord)) {
                acc.setPassWord(okPassWord);
                break;
            } else {
                System.out.println("您输入的两次密码不一致,请重新输入");
            }
        }
        System.out.println("请您输入取现额度");
        double limit = sc.nextDouble();
        acc.setLimit(limit);
        String newCardId = createCardId();
        acc.setCardId(newCardId);
        accounts.add(acc);
        System.out.println("恭喜您，" + acc.getUserName() + "开户成功,您的卡号是" + acc.getCardId());
    }

    //返回一个8位数的卡号
    private String createCardId() {
        //1.定义一个String类型的变量记住8位数字
        while (true) {
            String cardId = "";
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                int data = r.nextInt(10);
                cardId += data;
            }
            //3.判断cardId记住的卡号是否重复，没有重复才可以作为一个新卡号返回
            Account acc = getAccountByCardId(cardId);
            //
            if (acc == null) {
                //说明IdCard没有存在过可以返回它，做为一个新卡号
            }
            return cardId;
        }
    }

    private Account getAccountByCardId(String cardId) {
        //遍历全部的账户对象
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            //判断这个账户对象acc中的卡号是否是我们要找的卡号
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }

        }
        return null;//查无此号，这个账户不存在
    }
}
