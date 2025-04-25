package ra.edu.business.model;

import ra.edu.business.IApp;
import ra.edu.validate.CourseValidate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.CourseUI.courseService;
import static ra.edu.validate.CourseValidate.isCourseNameDuplicate;

public class Course implements IApp {


    private int id;
    private String name;
    private int duration;
    private String instructor;
    private LocalDate create_at;

    public Course(int id, String name, int duration, String instructor, LocalDate create_at) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
        this.create_at = create_at;
    }

    public Course() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    @Override
    public void inputData(Scanner scanner) {
        // Nhập tên khóa học
        System.out.print("Nhập tên khóa học: ");
        this.name = inputCourseName(scanner);

        // Nhập thời gian khóa học (số tháng)

        this.duration = (inputCourseDuration(scanner));

        // Nhập tên giảng viên
        this.instructor = inputInstructorName(scanner);

    }

    public static void displayHeader(String role) {

        if (role.equals("ADMIN")) {
            // Bảng header với màu xanh dương
            System.out.println("\u001B[34m+----+------------------------+----------+---------------------+------------+%n\u001B[0m");
            System.out.println("\u001B[34m| ID | Tên khoá học           | Thời lượng | Giảng viên           | Ngày tạo |%n\u001B[0m");
            System.out.println("\u001B[34m+----+------------------------+----------+---------------------+------------+%n\u001B[0m");
        } else {
            // Bảng header cho người dùng với màu xanh dương
            System.out.println("\u001B[34m+------------------------+----------+---------------------+------------+%n\u001B[0m");
            System.out.println("\u001B[34m| Tên khoá học           | Thời lượng | Giảng viên           | Ngày tạo   |%n\u001B[0m");
            System.out.println("\u001B[34m+------------------------+----------+---------------------+------------+%n\u001B[0m");
        }
    }

    //  In 1 dòng dữ liệu
    public void displayData(String role) {
        if (role.equals("ADMIN")) {
            // Dòng dữ liệu với màu xanh dương cho ADMIN
            System.out.printf("\u001B[34m| %-2d | %-22s | %-8d | %-19s | %-10s |\u001B[0m%n",
                    id, name, duration, instructor, create_at);
        } else {
            // Dòng dữ liệu với màu xanh dương cho người dùng
            System.out.printf("\u001B[34m| %-22s | %-8d | %-19s | %-10s |\u001B[0m%n",
                    name, duration, instructor, create_at);
        }
    }

    //  In dòng phân cách cuối
    public static void displayFooter(String role) {
        if (role.equals("ADMIN")) {
            // Footer với màu xanh dương cho ADMIN
            System.out.println("\u001B[34m+----+------------------------+----------+---------------------+------------+%n\u001B[0m");
        }
    }

    public String inputCourseName(Scanner scanner) {
        do {
            String courseName = scanner.nextLine();
            if (!CourseValidate.isValidCourseName(courseName)) {
                System.err.println("Tên ko hợp lệ");
                continue;
            }
            List<Course> listCourse = courseService.getAllCourse("ADMIN");
            if (isCourseNameDuplicate(courseName, listCourse, id)) {
                System.err.println("Tên khóa học đã tồn tại. Vui lòng nhập tên khác.");
                continue;
            }
            return courseName;
        } while (true);
    }

    public int inputCourseDuration(Scanner scanner) {
        do {
            System.out.println("Nhập thời lượng khoá học: ");
            String input = scanner.nextLine().trim();

            // Kiểm tra nếu đầu vào là chuỗi rỗng
            if (input.isEmpty()) {
                System.err.println("Thời gian không được để trống. Vui lòng nhập lại!");
                continue;
            }

            try {
                int duration = Integer.parseInt(input);
                if (!CourseValidate.isValidDuration(duration)) {
                    System.err.println("Thời gian không hợp lệ. Vui lòng nhập lại!");
                    continue;
                }
                return duration;
            } catch (NumberFormatException e) {
                System.err.println("Thời gian phải là một số nguyên. Vui lòng nhập lại!");
            }
        } while (true);
    }

    // ten giảng vien k dc rônng
    public String inputInstructorName(Scanner scanner) {
        do {
            System.out.print("Nhập tên giảng viên: ");
            String instructorName = scanner.nextLine().trim();

            // Kiểm tra tính hợp lệ của tên giảng viên
            if (CourseValidate.isValidInstructor(instructorName)) {

                return instructorName;
            }
            System.err.println("Tên giảng viên không hợp lệ. Vui lòng nhập lại.");
        } while (true);
    }

    // Phương thức validate lựa chọn hợp lệ (chọn 1 trong các lựa chọn)
    public static int validateChoice(Scanner scanner, int minChoice, int maxChoice) {
        int choice = -1;
        while (true) {
            System.out.print("Lựa chọn của bạn (" + minChoice + " đến " + maxChoice + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập một số hợp lệ.");
                continue;
            }

            if (choice >= minChoice && choice <= maxChoice) {
                return choice;
            } else {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

}

