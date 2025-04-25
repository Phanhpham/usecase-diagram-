package ra.edu.validate;

import ra.edu.business.model.Course;
import ra.edu.business.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentValidate {

        private static final Pattern EMAIL_PATTERN =
                Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        private static final Pattern PHONE_PATTERN =
                Pattern.compile("^\\+?\\d{9,15}$");

        private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Tên khóa học không được rỗng, tối đa 100 ký tự
    public static boolean isValidStudentName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

        // Kiểm tra số nguyên
        public static int validateInteger(String message, Scanner sc) {
            while (true) {
                System.out.print(message);
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Không được để trống. Vui lòng nhập lại.");
                    continue;
                }
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Số nguyên không hợp lệ. Vui lòng thử lại.");
                }
            }
        }


        // Hàm kiểm tra email có bị trùng hay không
        public static boolean isEmailDuplicate(String email, List<Student> listStudent) {
            for (Student student : listStudent) {
                if (student.getEmail().equals(email)) {
                    return true; // Trùng email
                }
            }
            return false; // Không trùng email
        }
    // Kiểm tra email hợp lệ
        public static String validateEmail(String message, Scanner sc) {
            while (true) {
                System.out.print(message);
                String email = sc.nextLine().trim();
                if (email.isEmpty()) {
                    System.err.println("Email không được để trống. Vui lòng nhập lại.");
                    continue;
                }
                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    System.err.println("Email không hợp lệ. Vui lòng nhập lại.");
                    continue;
                }
                return email;
            }
        }
        // check sdt bị trùng
        public static boolean isPhoneDuplicate(String phone, List<Student> listStudent) {
            for (Student student : listStudent) {
                if (student.getPhone().equals(phone)) {
                    return true;
                }
            }
            return false; // Không trùng email
        }
        // Kiểm tra số điện thoại hợp lệ
        public static String validatePhone(String message, Scanner sc) {
            while (true) {
                System.out.print(message);
                String phone = sc.nextLine().trim();
                if (phone.isEmpty()) {
                    System.err.println("Số điện thoại không được để trống. Vui lòng nhập lại.");
                    continue;
                }
                if (!PHONE_PATTERN.matcher(phone).matches()) {
                    System.err.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                    continue;
                }
                return phone;
            }
        }
        // mật khẩu ko đc để trống
        public static String validatePass(String message, Scanner sc) {
            while (true) {
                System.out.print(message);
                String password = sc.nextLine().trim();
                if (password.isEmpty()) {
                    System.err.println("Mật khẩu không được để trống. Vui lòng nhập lại.");
                    continue;
                }
                return password;
            }
        }
        // Kiểm tra ngày tháng hợp lệ (theo định dạng yyyy-MM-dd)
        public static LocalDate validateDate(String message, Scanner sc) {
            while (true) {
                System.out.print(message);
                String dateStr = sc.nextLine().trim();
                if (dateStr.isEmpty()) {
                    System.err.println("Ngày không được để trống. Vui lòng nhập lại.");
                    continue;
                }
                try {
                    return LocalDate.parse(dateStr, DATE_FORMAT);
                } catch (DateTimeParseException e) {
                    System.err.println("Ngày không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
                }
            }
        }

    }
