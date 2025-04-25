package ra.edu;

import ra.edu.business.model.Account;
import ra.edu.business.model.Role;
import ra.edu.business.model.SaveAcc;
import ra.edu.business.service.Account.AccountService;
import ra.edu.business.service.Account.AccountServiceImp;
import ra.edu.validate.ChoiceValidate;

import java.util.Scanner;

import static ra.edu.presentation.AccountUI.printMenuAccount;
import static ra.edu.presentation.StudentUI.showMenuStudent;
import static ra.edu.presentation.UserUI.showMenuUser;

public class Main {
    public static AccountService accountService = new AccountServiceImp();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            String greenColor = "\u001B[32m";
            String resetColor = "\u001B[0m";

            System.out.println(greenColor + "==========================================");
            System.out.println("======== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ========");
            System.out.println("==========================================");
            System.out.println("1. Đăng nhập với tư cách Quản trị viên");
            System.out.println("2. Đăng nhập với tư cách Học viên");
            System.out.println("3. Thoát");
            System.out.println("==========================================");
            System.out.print("Nhập lựa chọn: " + resetColor);
            choice = ChoiceValidate.validateChoice(scanner);

            switch (choice) {
                case 1:
                    loginAccount(scanner);
                    break;
                case 2:
                    loginAccount(scanner);
                    break;
                case 3:
                    System.out.println("Thoát khỏi hệ thống. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);

    }

    public static void loginAccount(Scanner scanner) {
        do {
            Account account = new Account();
            account.inputData(scanner);
            Account serviceAccount = accountService.checkLogin(account.getEmail(),account.getPassword());
            if(serviceAccount != null){
                System.out.println("\u001B[33mĐăng nhập thành công!!\u001B[0m");
                if(serviceAccount.getRole() == Role.ADMIN){
                   printMenuAccount(scanner);
                   break;
               }else if(serviceAccount.getRole()== Role.STUDENT){
                   //gán gtri vào save acc
                   SaveAcc.currentAcc = serviceAccount;
                   showMenuUser(scanner);
                   break;
               }
            }
            System.err.println("Tài khoản sai tên hoặc mật khẩu. Vui lòng nhập lại nhé!");
        } while (true);
    }
}

