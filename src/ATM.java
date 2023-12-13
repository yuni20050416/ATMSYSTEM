import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Account loginacc;

    public void start() {
        while (true) {
            System.out.println("==欢迎进入ATM系统==");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择");
            int command = sc.nextInt();
            switch (command) {
                case 1://用户登录
                    login();
                    break;
                case 2://用户开户
                    createAccount();
                    break;
                default:
                    System.out.println("没有该操作");
            }
        }
    }

    private void login() {
        System.out.println("系统登录");
        if (accounts.size() == 0) {
            System.out.println("当前系统无任何账户，请先开户");
            return;
        }
        while (true) {
            System.out.println("请您输入登录卡号");
            String cardId = sc.next();
            Account acc = getAccountByCardId(cardId);
            if (acc == null) {
                //说明该卡号不存在
                System.out.println("您输入的卡号不存在，请确认");
            } else {
                while (true) {
                    System.out.println("请输入您的登录密码");
                    String passWord = sc.next();
                    if (acc.getPassWord().equals(passWord)) {
                        loginacc = acc;
                        System.out.println("恭喜您，" + acc.getUserName() + "登录成功您的卡号是:   " + acc.getCardId());
                        showUserCommand();
                        return;
                    } else {
                        System.out.println("密码不正确，请重新输入");
                    }
                }
            }
        }
    }

    private void showUserCommand() {
        while (true) {
            System.out.println(loginacc.getUserName() + "您可以进行以下操作");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、退出");
            System.out.println("7、销户");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //  查询当前账户
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    depositMoney();
                    break;
                case 3:
                    drawMoney();
                    //取款
                    break;
                case 4:
                    transferMoney();
                    //转账
                    return;
                case 5:
                    updatePassWord();
                    //密码修改
                    break;
                case 6:
                    //退出
                    System.out.println(loginacc.getUserName() + "您退出系统成功");
                    return;//退出并结束当前方法
                case 7:
                    //注销当前账户
                    if (deleteAccount()) {
                        //销户成功了回到欢迎界面
                        return;
                    }
                    break;
                default:
                    System.out.println("您输入的操作命令有误,请重新输入");

            }
        }
    }

    //账户密码修改
    private void updatePassWord() {
        System.out.println("===账户密码修改===");
        System.out.println("请您输入当前账户的密码");
        String PassWord = sc.next();
        //认证当前密码是否正确
        while (true) {
            if (loginacc.getPassWord().equals(PassWord)) {
                //认证成功了
                //开始真正修改密码了
                while (true) {
                    System.out.println("请您输入新密码");
                    String newPassWord = sc.next();
                    System.out.println("请您再次输入确认密码");
                    String okPassWord = sc.next();
                    if (okPassWord.equals(newPassWord)) {
                        loginacc.setPassWord(okPassWord);
                        System.out.println("您的密码修改成功，您的新密码为: " + okPassWord);
                        return;
                    } else {
                        System.out.println("您输入的两次密码不一致");
                    }
                }
            } else {
                System.out.println("您当前输入的密码不正确");
            }
        }
    }

    private boolean deleteAccount() {
        System.out.println("==进行销户操作");
        //1.询问是否确认销户
        System.out.println("请问您确认销户吗?  y/n");
        String command = sc.next();
        switch (command) {
            case "y":
                //判断账户中是否有钱，有的话则不能销户
                if (loginacc.getMoney() == 0) {
                    //真的销户了
                    accounts.remove(loginacc);
                    System.out.println("您好，您的账户已经销户");
                    return true;
                } else {
                    System.out.println("对不起，您的账户中有余额，不允许销户");
                    return false;

                }
            default:
                System.out.println("好的，您的账户保留");
                return false;
        }

    }

    //转账方法
    private void transferMoney() {
        System.out.println("==用户转账==");
        //判断系统中是否有俩账户
        if (accounts.size() < 2) {
            System.out.println("当前系统中只有你一个账户，无法为其他账户转账");
            return;
        }
        //判断账户中是否有钱
        if (loginacc.getMoney() == 0) {
            System.out.println("您自己都没钱，就别转了");
            return;
        }
        //真正开始转账了
        while (true) {
            System.out.println("请您输入对方的卡号");
            String cardId = sc.next();

            //4.判断这个卡号是否正确
            Account acc = getAccountByCardId(cardId);
            if (acc == null) {
                System.out.println("您输入的卡号不存在");
            } else {
                //对方的账户存在,你需要验证卡号的姓氏
                String name = "*" + acc.getUserName().substring(1);

                System.out.println("请您输入【" + name + "】的姓氏");
                String preName = sc.next();
                if (acc.getUserName().startsWith(preName)) {
                    //认证通过了,真正转账了
                    while (true) {
                        System.out.println("请您输入转账给对方的转账金额");
                        double money = sc.nextDouble();
                        if (money <= loginacc.getMoney()) {
                            //转给对方了
                            //更新自己的账户余额
                            loginacc.setMoney(loginacc.getMoney() - money);
                            //更新对方的账户余额
                            acc.setMoney(acc.getMoney() + money);
                            return;//跳出转账
                        } else {
                            System.out.println("您的余额不足，转账失败,最多可转:" + loginacc.getMoney());
                        }
                    }
                } else {
                    System.out.println("对不起，您认证的姓氏有问题");
                }

            }
        }
    }

    // 取钱方法
    private void drawMoney() {
        System.out.println("==取钱操作==");
        //判断账户余额是否大于100元，如果不大于100就不让取钱操作
        if (loginacc.getMoney() < 100) {
            System.out.println("您的账户余额不足100元，不允许取钱");
            return;
        }
        //让用户输入取款金额
        while (true) {
            System.out.println("请您输入取款金额");
            double money = sc.nextDouble();
            //判断账户余额是否足够
            if (loginacc.getMoney() >= money) {
                //账户中的余额足够的
                //判断取款金额不能超过限额
                if (loginacc.getLimit() < money) {
                    System.out.println("您当前取款金额超过了每次的限额，您每次最多可取款" + loginacc.getLimit());
                } else {
                    //代表可以取钱
                    //更新当前的余额
                    loginacc.setMoney(loginacc.getMoney() - money);
                    System.out.println("您取款金额为:" + money + "成功，取款后您剩余" + loginacc.getMoney());
                    break;
                }
            } else {
                System.out.println("余额不足,您账户的余额是:" + loginacc.getMoney());
            }
        }
    }

    private void depositMoney() {
        System.out.println("==存钱操作==");
        System.out.println("请您输入存款金额");
        double money = sc.nextDouble();
        //分析当前账户的余额
        loginacc.setMoney(loginacc.getMoney() + money);
        System.out.println("恭喜您存钱" + money + "成功，存钱后的余额为：" + loginacc.getMoney());

    }
    //存钱


    private void showLoginAccount() {
        System.out.println("==当前您的账户信息如下:==");
        System.out.println("卡号:" + loginacc.getCardId());
        System.out.println("户主:" + loginacc.getUserName());
        System.out.println("性别:" + loginacc.getSex());
        System.out.println("余额:" + loginacc.getMoney());
        System.out.println("每次取现额度:" + loginacc.getLimit());
    }

    private void createAccount() {
        Account acc = new Account();
        System.out.println("请您输入账户用户名");
        String name = sc.next();
        acc.setUserName(name);
        while (true) {
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
        System.out.println("恭喜您，" + acc.getUserName() + "开户成功,您的卡号是:   " + acc.getCardId());
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
