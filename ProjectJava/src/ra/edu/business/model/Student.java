package ra.edu.business.model;

import ra.edu.business.IApp;
import ra.edu.validate.CourseValidate;
import ra.edu.validate.StudentValidate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.CourseUI.courseService;
import static ra.edu.presentation.StudentUI.studentService;
import static ra.edu.validate.CourseValidate.isCourseNameDuplicate;
import static ra.edu.validate.StudentValidate.*;

public class Student implements IApp {
    private int id;
    private String name;
    private LocalDate dob;
    private String email;
    private Boolean sex;
    private String phone;
    private String password;
    private LocalDate create_at;

    // constructor
    public Student() {

    }

    public Student(int id, String name, LocalDate dob, String email, Boolean sex, String phone, String password, LocalDate create_at) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.create_at = create_at;
    }

    //getter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }
    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    @Override
    public void inputData(Scanner scanner) {
        // Nhập tên khóa học
        System.out.print("Nhập tên học viên: ");
        this.name = inputStudentName(scanner);

        // Yêu cầu người dùng nhập ngày sinh học viên
        this.dob = inputStudentDateOfBirth(scanner);

        System.out.print("Nhập giới tính học viên: ");
        this.sex = Boolean.parseBoolean(scanner.nextLine());

        this.email = inputStudentEmail(scanner);


        this.phone = inputStudentPhone(scanner);

        this.password = inputStudentPass(scanner);

    }

    // Display header
    public static void displayHeader() {
        System.out.printf("+----+------------------------+--------------+-------------+-----------+------------+------------+%n");
        System.out.printf("| ID | Tên học viên           | Ngày sinh    | Giới tính   | Email     | Phone      | Ngày tạo   |%n");
        System.out.printf("+----+------------------------+--------------+-------------+-----------+------------+------------+%n");
    }

    public void displayData() {
        String gender = (sex) ? "Nam" : "Nữ";
        System.out.printf("| %-2d | %-22s | %-12s | %-11s | %-10s | %-10s | %-10s |%n",
                id, name, dob, gender, email, phone, create_at);
    }

    // Display footer
    public static void displayFooter() {
        System.out.printf("+----+------------------------+--------------+-------------+-----------+------------+------------+%n");
    }

    public String inputStudentName(Scanner scanner) {
        do {
            String studentName = scanner.nextLine().trim();

            // Kiểm tra tên học sinh hợp lệ
            if (!StudentValidate.isValidStudentName(studentName)) {
                System.err.println("Tên không hợp lệ. Vui lòng nhập lại.");
                continue;
            }

            // Lấy danh sách tất cả học sinh để kiểm tra trùng tên
            List<Student> listStudent = studentService.getAllStudent();


            return studentName;
        } while (true);
    }

    // ngay sinh
    public LocalDate inputStudentDateOfBirth(Scanner scanner) {
        do {

            // Lấy giá trị ngày sinh hợp lệ từ validateDate
            LocalDate dateOfBirth = validateDate("Nhập ngày sinh của học sinh (yyyy-MM-dd): ", scanner);

            // Kiểm tra xem ngày sinh có hợp lệ với độ tuổi hay không
            if (dateOfBirth.isAfter(LocalDate.now())) {
                System.err.println("Ngày sinh không thể là trong tương lai. Vui lòng nhập lại.");
                continue;
            }

            return dateOfBirth;

        } while (true);
    }

    // email
    public String inputStudentEmail(Scanner scanner) {
        do {
            // Lấy giá trị email hợp lệ từ validateEmail
            String email = validateEmail("Nhập email của học sinh: ", scanner);

            // Kiểm tra xem email có trùng lặp không (nếu cần thiết)
            List<Student> listStudent = studentService.getAllStudent();
            if (isEmailDuplicate(email, listStudent)) {
                System.err.println("Email đã tồn tại. Vui lòng nhập email khác.");
                continue;
            }
            return email;

        } while (true);
    }

    // phone
    public String inputStudentPhone(Scanner scanner) {
        do {
            String phone = validatePhone("Nhập sđt của học sinh: ", scanner);

            List<Student> listStudent = studentService.getAllStudent();
            if (isPhoneDuplicate(phone, listStudent)) {
                System.err.println("Sđt đã tồn tại ! Vui lòng nhập sđt khác.");
                continue;
            }
            return phone;

        } while (true);
    }

    // mât khẩu ko dc để trống
    public String inputStudentPass(Scanner scanner) {
        do {
            String pass = validatePass("Nhập mật khẩu của học sinh: ", scanner);

            List<Student> listStudent = studentService.getAllStudent();
            return pass;
        } while (true);
    }

}
