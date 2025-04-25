package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.model.SaveCourseRegister;
import ra.edu.business.model.SaveStudentForCourse;
import ra.edu.business.model.Student;
import ra.edu.business.service.Student.StudentService;
import ra.edu.business.service.Student.StudentServiceImp;
import ra.edu.validate.ChoiceValidate;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.UserUI.enrollmentService;


public class StudentUI {
    public static StudentService studentService = new StudentServiceImp();

    public static void showMenuStudent(Scanner scanner) {
        int choice;

        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m       ======= QUẢN LÝ HỌC VIÊN =======       \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Hiển thị danh sách học viên                \u001B[0m");
            System.out.println("\u001B[34m2. Thêm mới học viên                         \u001B[0m");
            System.out.println("\u001B[34m3. Chỉnh sửa thông tin học viên              \u001B[0m");
            System.out.println("\u001B[34m4. Xóa học viên (xác nhận trước khi xóa)    \u001B[0m");
            System.out.println("\u001B[34m5. Tìm kiếm theo tên, email hoặc ID         \u001B[0m");
            System.out.println("\u001B[34m6. Sắp xếp theo tên hoặc ID (tăng/giảm dần) \u001B[0m");
            System.out.println("\u001B[34m7. Quay về menu chính                       \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn của bạn: \u001B[0m");

            choice = ChoiceValidate.validateChoice(scanner);

            switch (choice) {
                case 1:
                    paginationStudent(scanner);
                    break;
                case 2:
                    addAllStudent(scanner);
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    searchStudent(scanner);
                    break;
                case 6:
                    sortStudent(scanner);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }

    //phan trang hoc vien
    public static void paginationStudent(Scanner scanner) {
        // b1 :  tổng số bản gì trong danh sách và tổng số trang
        // b2: hiển thi số trag
        // b3 : cho ng dùng chọn trang muốn xem
        // b4 :in ra ds bản ghi của trang đó
        do {
            List<Student> listStudent = studentService.getAllStudent();
            // tổng số trang = tổng số bản ghi ttong ds / số lương bản ghi tren 1 trag
            int totalPages = (int) Math.ceil((double) listStudent.size() / 5);
            for (int i = 1; i <= totalPages; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn cua bạn là(Hoặc nhấn 0 để thoát): ");
            int currentPages = ChoiceValidate.validateChoice(scanner);
            if (currentPages == 0) {
                break;
            }
            if (currentPages < 1 || currentPages > totalPages) {
                System.err.println("Trang của bạn ko hợp lệ.");
                continue;
            }
            List<Student> separatePages = studentService.separatePage(currentPages);
            Student student = new Student();
            student.displayHeader();
            separatePages.forEach(courses -> {
                courses.displayData();
            });
            student.displayFooter();
        } while (true);
    }

    // thêm học viên
    public static void addAllStudent(Scanner scanner) {
        System.out.println("Nhập số học viên cần thêm:");
        int size = ChoiceValidate.validateChoice(scanner);
        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin hoc viên thư: " + (i + 1));
            Student student = new Student();
            student.inputData(scanner);
            boolean result = studentService.addStudent(student);

            if (result) {
                Student findStudent = studentService.findIdStudent(student.getId());

                if (findStudent != null) {
                    // Tạo tài khoản cho học viên
                    boolean createAccount = studentService.createAccount(findStudent);

                    if (createAccount) {
                        System.out.println("Thêm học viên và tạo tài khoản thành công!");
                    } else {
                        System.err.println("Tạo tài khoản học viên thất bại!");
                    }
                } else {
                    System.err.println("Không tìm thấy học viên vừa thêm!");
                }
            } else {
                System.err.println("thêm học viên thất baị!");
            }
        }
    }

    // xoá học viên
    public static void deleteStudent(Scanner scanner) {
        System.out.println("Nhập id học viên cần xoá:");
        int id = ChoiceValidate.validateChoice(scanner);
        List<SaveCourseRegister> saveRegister = enrollmentService.saveCourse(id);
        if(!saveRegister.isEmpty()){
            System.err.println("Sinh viên có đang ký khoá học rồi. Không thể xoá !");
            return;
        }
        if (studentService.findIdStudent(id) != null) {
            System.out.println("Bạn có chắc chăn muốn xoá không ?");
            String confirm = scanner.nextLine();
            if (confirm.equals("y")) {
                Student student = new Student();
                student.setId(id);
                boolean result = studentService.deleteStudent(student);
                if (result) {
                    System.out.println("xoá thành công!");
                } else {
                    System.err.println("xoá thất bại!");
                }
            } else if (confirm.equals("n")) {
                System.out.println("Huỷ");
            }
        } else {

            System.err.println("Không tìm thấy sinh viên");
        }
    }

    // tìm kiếm học viên
    public static void searchStudent(Scanner scanner) {
        System.out.println("Nhập tên học viên cần tìm :");
        String name = scanner.nextLine().trim();
        System.out.println("Nhập email học viên cần tìm :");
        String email = scanner.nextLine().trim();
        System.out.println("Nhập id học viên cần tìm :");
        String idStr = scanner.nextLine().trim();
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);

        List<Student> students = studentService.searchStudent(name, email, id);

        if (students != null && !students.isEmpty()) {
            System.out.println("Danh sách học viên tìm được:");
            Student student = new Student();
            student.displayHeader();
            students.forEach(s -> {
                s.displayData();
            });
            student.displayFooter();

            // Phân trang kết quả tìm kiếm
            do {
                int totalPages = (int) Math.ceil((double) students.size() / 5);
                for (int i = 1; i <= totalPages; i++) {
                    System.out.printf("Trang %d\n", i);
                }
                System.out.printf("Lựa chọn của bạn là (hoặc nhấn 0 để thoát): ");
                int currentPage = Integer.parseInt(scanner.nextLine());

                if (currentPage == 0) {
                    break;
                }

                if (currentPage < 1 || currentPage > totalPages) {
                    System.err.println("Trang của bạn không hợp lệ.");
                    continue;
                }

                int startIndex = (currentPage - 1) * 5;
                int endIndex = Math.min(startIndex + 5, students.size());

                // Lấy danh sách con để hiển thị cho trang hiện tại
                List<Student> pageStudents = students.subList(startIndex, endIndex);
                Student pageStudent = new Student();
                pageStudent.displayHeader();
                pageStudents.forEach(studentData -> {
                    studentData.displayData();
                });
                pageStudent.displayFooter();
            } while (true);
        } else {
            System.err.println("Không tìm thấy học viên với tên: " + name + ", email: " + email + " và id: " + id);
        }
    }

    // tìm kiếm hoc viên
    public static void sortStudent(Scanner scanner) {
        // Giả sử danh sách các khóa học đã có trong một list
        List<Student> student = studentService.getAllStudent();

        if (student != null && !student.isEmpty()) {
            // Sắp xếp khóa học
            System.out.println("Chọn cách sắp xếp:");
            System.out.println("1. Sắp xếp theo tên tăng dần");
            System.out.println("2. Sắp xếp theo tên giảm dần");
            System.out.println("3. Sắp xếp theo ID tăng dần");
            System.out.println("4. Sắp xếp theo ID giảm dần");
            System.out.print("Lựa chọn của bạn: ");
            int sortOption = ChoiceValidate.validateChoice(scanner);

            // Sắp xếp khóa học theo lựa chọn
            switch (sortOption) {
                case 1:
                    student.sort(Comparator.comparing(Student::getName));
                    break;
                case 2:
                    student.sort(Comparator.comparing(Student::getName).reversed());
                    break;
                case 3:
                    student.sort(Comparator.comparingInt(Student::getId)); // Tăng dần theo ID
                    break;
                case 4:
                    student.sort(Comparator.comparingInt(Student::getId).reversed());
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Sử dụng mặc định (tăng dần theo tên).");
                    student.sort(Comparator.comparing(Student::getName));
                    break;
            }
            // Hiển thị kết quả sau khi sắp xếp
            System.out.println("Danh sách khóa học sau khi sắp xếp:");
            Course course = new Course();
            course.displayHeader("ADMIN");
            student.forEach(c -> c.displayData());
            course.displayFooter("ADMIN");

            // Phân trang
            int pageSize = 5;
            int totalPages = (int) Math.ceil((double) student.size() / pageSize);

            do {
                System.out.printf("Tổng cộng có %d trang. Chọn trang (Hoặc nhấn 0 để thoát): ", totalPages);
                int currentPage = Integer.parseInt(scanner.nextLine());
                if (currentPage == 0) {
                    break;
                }
                if (currentPage < 1 || currentPage > totalPages) {
                    System.err.println("Trang của bạn không hợp lệ.");
                    continue;
                }

                // Tính chỉ số bắt đầu và kết thúc của khóa học trong trang hiện tại
                int startIndex = (currentPage - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, student.size());
                List<Student> pageCourses = student.subList(startIndex, endIndex);

                // Hiển thị danh sách khóa học trên trang hiện tại
                course.displayHeader("ADMIN");
                pageCourses.forEach(c -> c.displayData());
                course.displayFooter("ADMIN");
            } while (true);
        } else {
            System.err.println("Danh sách khóa học trống.");
        }
    }

    // update hoc vien
    public static void updateStudent(Scanner scanner) {
        System.out.println("nhập id hoc vien cần cập nhật:");
        int id = ChoiceValidate.validateChoice(scanner);
        Student student = studentService.findIdStudent(id);
        if (student != null) {
            do {
                System.out.println("========== Lựa chọn Cập Nhật ==========");
                System.out.println("1. Cập nhật theo tên hoc viên");
                System.out.println("2. Cập nhật ngày sinh hoc viên ");
                System.out.println("3. Cập nhật giới tính hoc viên ");
                System.out.println("4. Cập nhật email hoc viên ");
                System.out.println("5. Cập nhật sđt của hoc viên ");
                System.out.println("6. Cập nhật mật khẩu của hoc viên ");
                System.out.println("7. Trở về menu quản lý");
                System.out.print("Nhập lựa chọn của bạn: ");

                int choice = ChoiceValidate.validateChoice(scanner);
                switch (choice) {
                    case 1:
                        System.out.println("Nhập tên hoc viên mới: ");
                        student.setName(student.inputStudentName(scanner));
                        boolean result1 = studentService.updateStudent(student, 1);
                        if (result1) {
                            System.out.println("cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 2:
                        System.out.println("Nhập ngày sinh hoc viên mới: ");
                        String dobInput = scanner.nextLine();
                        try {
                            java.sql.Date dob = java.sql.Date.valueOf(dobInput);
                            student.setDob(student.inputStudentDateOfBirth(scanner));
                            boolean result2 = studentService.updateStudent(student, 2);
                            if (result2) {
                                System.out.println("Cập nhật thành công!");
                            } else {
                                System.err.println("Cập nhật thất bại!");
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Ngày sinh không hợp lệ!");
                        }
                        break;
                    case 3:
                        System.out.println("Nhập giới tính học viên mới ");
                        boolean sex = scanner.nextInt() == 1;
                        student.setSex(sex);
                        boolean result3 = studentService.updateStudent(student, 3);
                        if (result3) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 4:
                        System.out.println("Nhập email học viên mới: ");
                        student.setEmail(student.inputStudentEmail(scanner));
                        boolean result4 = studentService.updateStudent(student, 4);
                        if (result4) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 5:
                        System.out.println("Nhập số điện thoại học viên mới: ");
                        student.setPhone(student.inputStudentPhone(scanner));
                        boolean result5 = studentService.updateStudent(student, 5);
                        if (result5) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 6:
                        System.out.println("Nhập mật khẩu học viên mới: ");
                        student.setPassword(student.inputStudentPass(scanner));
                        boolean result6 = studentService.updateStudent(student, 6);
                        if (result6) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                        break;
                }
            } while (true);
        } else {
            System.err.println("Lỗi! Không tìm thấy khoá học");
        }
    }
}
