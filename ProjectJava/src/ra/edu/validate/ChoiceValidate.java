package ra.edu.validate;

import java.util.Scanner;

public class ChoiceValidate {
    // lựa chọn hợp lệ
    public static int validateChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                // Chuyển đổi thành số nguyên
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                // Nếu không phải là số nguyên
                System.err.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }
}
