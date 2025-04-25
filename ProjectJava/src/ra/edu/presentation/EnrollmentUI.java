package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.model.RegisterStatus;
import ra.edu.business.model.SaveStudentForCourse;
import ra.edu.validate.ChoiceValidate;
import java.util.List;
import java.util.Scanner;
import static ra.edu.presentation.UserUI.enrollmentService;

public class EnrollmentUI {
    public static void showMenuEnroll(Scanner scanner) {
        int choice;
        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m      ======= QUẢN LÝ ĐĂNG KÝ KHÓA HỌC =======      \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Hiển thị danh sách đăng ký                \u001B[0m");
            System.out.println("\u001B[34m2. Duyệt sinh viên đăng ký khoá học          \u001B[0m");
            System.out.println("\u001B[34m3. Xoá sinh viên khỏi khoá học              \u001B[0m");
            System.out.println("\u001B[34m4. Quay về menu chính                       \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn của bạn: \u001B[0m");

            choice = ChoiceValidate.validateChoice(scanner);
            switch (choice) {
                case 1:
                    pagination(scanner);
                    break;
                case 2:
                    confirmStudent(scanner);
                    break;
                case 3:
                    deleteStudentRegister(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }

    //phan trang hien thi dsach
    public static void pagination(Scanner scanner) {
        // Bước 1: Lấy danh sách khóa học từ dịch vụ và cho người dùng chọn
        List<Course> allCourses = CourseUI.courseService.getAllCourse("ADMIN");
        System.out.println("Danh sách các khóa học:");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, allCourses.get(i).getName());
        }
        // b2:
        System.out.printf("Chọn một khóa học (nhập số từ 1 đến %d): ", allCourses.size());
        int courseChoice = ChoiceValidate.validateChoice(scanner);

        if (courseChoice < 1 || courseChoice > allCourses.size()) {
            System.err.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            return;
        }
        // Lấy khóa học đã chọn
        Course selectedCourse = allCourses.get(courseChoice - 1);

        // Bước 3: Lấy danh sách sinh viên đăng ký khóa học đã chọn
        List<SaveStudentForCourse> saveStuForCourse = enrollmentService.getAllStudentForCourse(selectedCourse.getId());
        List<SaveStudentForCourse> filterStudent = saveStuForCourse.stream().filter(student -> student.getStatus() == RegisterStatus.CONFIRM).toList();
        int totalRecords = filterStudent.size();
        // ktra
        if (filterStudent.isEmpty()) {
            System.err.println("Chưa có sinh viên đăng ký khoá học");
            return;
        }
        // Bước 4: Tính toán tổng số trang (chia tổng số bản ghi cho số bản ghi mỗi trang)
        int recordsPerPage = 5;
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // Bước 5: Hiển thị các trang có sẵn
        for (int i = 1; i <= totalPages; i++) {
            System.out.printf("Trang %d\n", i);
        }
        // Bước 6: Cho người dùng chọn trang và hiển thị dữ liệu
        while (true) {
            System.out.printf("Lựa chọn của bạn (Nhấn 0 để thoát): ");
            int currentPage = Integer.parseInt(scanner.nextLine());

            // Bước 7: Nếu người dùng nhập 0 thì thoát
            if (currentPage == 0) {
                break;
            }

            // Kiểm tra xem người dùng có chọn trang hợp lệ không
            if (currentPage < 1 || currentPage > totalPages) {
                System.err.println("Trang bạn chọn không hợp lệ. Vui lòng chọn lại.");
                continue;
            }
            // Bước 8: Lấy danh sách sinh viên của trang hiện tại
            int fromIndex = (currentPage - 1) * recordsPerPage;
            int toIndex = Math.min(fromIndex + recordsPerPage, filterStudent.size());
            List<SaveStudentForCourse> paginatedList = filterStudent.subList(fromIndex, toIndex);

            // Hiển thị dữ liệu
            SaveStudentForCourse student = new SaveStudentForCourse();
            student.printTableHeaders();
            paginatedList.forEach(s -> {
                s.displayTable();
            });
            student.printTableFooters();
        }
    }

    // duyet danh sách
    public static void confirmStudent(Scanner scanner) {
        List<Course> allCourses = CourseUI.courseService.getAllCourse("ADMIN");
        System.out.println("Danh sách các khóa học:");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, allCourses.get(i).getName());
        }
        // b2:
        System.out.printf("Chọn một khóa học (nhập số từ 1 đến %d): ", allCourses.size());
        int courseChoice = Integer.parseInt(scanner.nextLine());

        if (courseChoice < 1 || courseChoice > allCourses.size()) {
            System.err.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            return;
        }
        // Lấy khóa học đã chọn
        Course selectedCourse = allCourses.get(courseChoice - 1);

        // Bước 3: Lấy danh sách sinh viên đăng ký khóa học đã chọn
        List<SaveStudentForCourse> saveStuForCourse = enrollmentService.getAllStudentForCourse(selectedCourse.getId());
        List<SaveStudentForCourse> filterStudent = saveStuForCourse.stream().filter(student -> student.getStatus() == RegisterStatus.WAITING).toList();

        int totalRecords = saveStuForCourse.size();
        // ktra
        if (filterStudent.isEmpty()) {
            System.err.println("Chưa có sinh viên đăng ký khoá học");
            return;
        }
        for (int i = 0; i < filterStudent.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, filterStudent.get(i).getStudent_name());
        }
        System.out.printf("0.Thoát ");
        int choice = Integer.parseInt(scanner.nextLine());

        // lấy svien da chon
        SaveStudentForCourse selectStudent = filterStudent.get(choice - 1);
        System.out.println("Bạn có xác nhận duyệt ko ?");
        String confirms = scanner.nextLine();
        if (confirms.equals("y")) {
            boolean confirmStudent = enrollmentService.confirmStudent(selectStudent.getStudent_id(), selectedCourse.getId());
            if (confirmStudent) {
                System.out.println("duyệt thành công");
            }
        } else if (confirms.equals("n")) {
            System.err.println("Huỷ xác nhận duyệt!");
        }
    }

    // xoá svien đã đky
    public static void deleteStudentRegister(Scanner scanner) {
        List<Course> allCourses = CourseUI.courseService.getAllCourse("ADMIN");
        System.out.println("Danh sách các khóa học:");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, allCourses.get(i).getName());
        }
        // b2:
        System.out.printf("Chọn một khóa học (nhập số từ 1 đến %d): ", allCourses.size());
        int courseChoice = Integer.parseInt(scanner.nextLine());

        if (courseChoice < 1 || courseChoice > allCourses.size()) {
            System.err.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            return;
        }
        // Lấy khóa học đã chọn
        Course selectedCourse = allCourses.get(courseChoice - 1);

        // Bước 3: Lấy danh sách sinh viên đăng ký khóa học đã chọn
        List<SaveStudentForCourse> saveStuForCourse = enrollmentService.getAllStudentForCourse(selectedCourse.getId());
        List<SaveStudentForCourse> filterStudent = saveStuForCourse.stream().filter(student -> student.getStatus() == RegisterStatus.CANCELLED).toList();
        // ktra
        if (filterStudent.isEmpty()) {
            System.err.println("Chưa có sinh viên đăng ký khoá học");
            return;
        }
        for (int i = 0; i < filterStudent.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, filterStudent.get(i).getStudent_name());
        }
        System.out.printf("0.Thoát ");
        int choice = Integer.parseInt(scanner.nextLine());

        // lấy svien da chon
        SaveStudentForCourse selectStudent = filterStudent.get(choice - 1);
        System.out.printf("%d %d", selectStudent.getStudent_id(), selectedCourse.getId());
        System.out.println("Bạn có xác nhận xoá ko ?");
        String confirm = scanner.nextLine();
        if (confirm.equals("y")) {
            boolean confirmStudent = enrollmentService.deleteStudentRegister(selectStudent.getStudent_id(), selectedCourse.getId());
            if (confirmStudent) {
                System.out.println("Xoá thành công");
            }
        } else if (confirm.equals("n")) {
            System.err.println("Huỷ xác nhận xoá!");
        }
    }
}
