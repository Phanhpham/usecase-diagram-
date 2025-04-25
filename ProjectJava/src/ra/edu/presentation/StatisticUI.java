package ra.edu.presentation;

import ra.edu.business.service.Enrollment.EnrollmentServiceImp;
import ra.edu.business.service.Statistic.StatisticService;
import ra.edu.business.service.Statistic.StatisticServiceImp;
import ra.edu.validate.ChoiceValidate;

import java.awt.*;
import java.util.Map;
import java.util.Scanner;

public class StatisticUI {
    public static StatisticService statisticService = new StatisticServiceImp();

    public static void displayMenuStatistics(Scanner scanner) {

        int choice;
        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m        ======= MENU THỐNG KÊ =======        \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Thống kê tổng số lượng khóa học và học viên   \u001B[0m");
            System.out.println("\u001B[34m2. Thống kê học viên theo từng khóa học          \u001B[0m");
            System.out.println("\u001B[34m3. Top 5 khóa học đông học viên nhất            \u001B[0m");
            System.out.println("\u001B[34m4. Liệt kê khóa học có trên 10 học viên         \u001B[0m");
            System.out.println("\u001B[34m5. Quay về menu chính                          \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn của bạn: \u001B[0m");
            choice = ChoiceValidate.validateChoice(scanner);

            switch (choice) {
                case 1:
                    getTotalCourseAndStudent();
                    break;
                case 2:
                    getTotalStudentCourse();
                    break;
                case 3:
                    getTop5StudentCourse();
                    break;
                case 4:
                    getMoreThan10Student();
                    break;
                case 5:
                   return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }

    public static void getTotalCourseAndStudent() {
        Map<Integer, Integer> totalCourseAndStudent = statisticService.getTotalCourseAndStudent();

        if (totalCourseAndStudent.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("\u001B[35m╔══════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[35m║       THỐNG KÊ KHÓA HỌC VÀ SINH VIÊN     ║\u001B[0m");
        System.out.println("\u001B[35m╠════════════════════════╦═════════════════╣\u001B[0m");
        System.out.printf("\u001B[35m║ %-22s ║ %-15s ║\n\u001B[0m", "Tổng số khóa học", "Tổng sinh viên");
        System.out.println("\u001B[35m╠════════════════════════╬═════════════════╣\u001B[0m");

        totalCourseAndStudent.forEach((key, value) -> {
            System.out.printf("║ %-22d ║ %-15d ║\n", key, value);

        });

        System.out.println("\u001B[35m╚════════════════════════╩═════════════════╝\u001B[0m");
    }

    public static void getTotalStudentCourse() {
        Map<String, Integer> totalCourseAndStudent = statisticService.getTotalStudentOfCourse();

        if (totalCourseAndStudent.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("\u001B[35m╔══════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[35m║       THỐNG KÊ TỔNG SỐ HỌC VIÊN          ║\u001B[0m");
        System.out.println("\u001B[35m╠════════════════════════╦═════════════════╣\u001B[0m");
        System.out.printf("\u001B[35m║ %-22s ║ %-15s ║\n\u001B[0m", "Tên khoá học", "Tổng sinh viên");
        System.out.println("\u001B[35m╠════════════════════════╬═════════════════╣\u001B[0m");

        totalCourseAndStudent.forEach((key, value) -> {
            System.out.printf("║ %-22s ║ %-15d ║\n", key, value);
        });

        System.out.println("\u001B[35m╚════════════════════════╩═════════════════╝\u001B[0m");
    }

    public static void getTop5StudentCourse() {
        Map<String, Integer> totalCourseAndStudent = statisticService.getTop5CourseMostStudent();

        if (totalCourseAndStudent.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("\u001B[35m╔══════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[35m║ THỐNG KÊ 5 KHOÁ HỌC ĐÔNG SINH VIÊN NHẤT  ║\u001B[0m");
        System.out.println("\u001B[35m╠════════════════════════╦═════════════════╣\u001B[0m");
        System.out.printf("\u001B[35m║ %-22s ║ %-15s ║\n\u001B[0m", "Tên khoá học", "Tổng sinh viên");
        System.out.println("\u001B[35m╠════════════════════════╬═════════════════╣\u001B[0m");

        totalCourseAndStudent.forEach((key, value) -> {
            System.out.printf("║ %-22s ║ %-15d ║\n", key, value);
        });

        System.out.println("\u001B[35m╚════════════════════════╩═════════════════╝\u001B[0m");
    }

    public static void getMoreThan10Student() {
        Map<String, Integer> totalCourseAndStudent = statisticService.getCourseMoreThan10Students();

        if (totalCourseAndStudent.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("\u001B[35m╔══════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[35m║  THỐNG KÊ KHOÁ HỌC CÓ TRÊN 10 SINH VIÊN  ║\u001B[0m");
        System.out.println("\u001B[35m╠════════════════════════╦═════════════════╣\u001B[0m");
        System.out.printf("\u001B[35m║ %-22s ║ %-15s ║\n\u001B[0m", "Tên khoá học", "Tổng sinh viên");
        System.out.println("\u001B[35m╠════════════════════════╬═════════════════╣\u001B[0m");

        totalCourseAndStudent.forEach((key, value) -> {
            System.out.printf("║ %-22s ║ %-15d ║\n", key, value);
        });

        System.out.println("\u001B[35m╚════════════════════════╩═════════════════╝\u001B[0m");
    }
}
