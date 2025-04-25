package ra.edu.presentation;

import ra.edu.validate.ChoiceValidate;

import java.util.Scanner;

public class AccountUI {
    public static void printMenuAccount(Scanner scanner) {
        int choice;
        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m        ======== MENU ADMIN ========        \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Quản lý khoá học                        \u001B[0m");
            System.out.println("\u001B[34m2. Quản lý học viên                        \u001B[0m");
            System.out.println("\u001B[34m3. Quản lý đăng ký khoá học               \u001B[0m");
            System.out.println("\u001B[34m4. Thống kê                               \u001B[0m");
            System.out.println("\u001B[34m5. Thoát                                  \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn: \u001B[0m");

            choice = ChoiceValidate.validateChoice(scanner);

            switch (choice) {
                case 1:
                    CourseUI.printMenuCourse(scanner);
                    break;
                case 2:
                    StudentUI.showMenuStudent(scanner);
                    break;
                case 3:
                    EnrollmentUI.showMenuEnroll(scanner);
                    break;
                case 4:
                    StatisticUI.displayMenuStatistics(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }
}
