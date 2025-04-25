package ra.edu.presentation;

import ra.edu.business.model.*;
import ra.edu.business.service.Enrollment.EnrollmentService;
import ra.edu.business.service.Enrollment.EnrollmentServiceImp;
import ra.edu.validate.ChoiceValidate;
import ra.edu.validate.StudentValidate;

import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.CourseUI.courseService;
import static ra.edu.presentation.CourseUI.searchCourse;

public class UserUI {
    public static EnrollmentService enrollmentService = new EnrollmentServiceImp();

    public static void showMenuUser(Scanner scanner) {
        int choice;

        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m        ======= MENU HỌC VIÊN =======         \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Xem danh sách khóa học                     \u001B[0m");
            System.out.println("\u001B[34m2. Đăng ký khóa học                         \u001B[0m");
            System.out.println("\u001B[34m3. Xem khóa học đã đăng ký                  \u001B[0m");
            System.out.println("\u001B[34m4. Hủy đăng ký khóa học                     \u001B[0m");
            System.out.println("\u001B[34m5. Cập nhật mật khẩu                        \u001B[0m");
            System.out.println("\u001B[34m6. Thoát                                    \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn của bạn: \u001B[0m");

            choice = ChoiceValidate.validateChoice(scanner);
            switch (choice) {
                case 1:
                    pagination(scanner);
                    break;
                case 2:
                    registerCourse(scanner);
                    break;
                case 3:
                    showCourseRegister();
                    break;
                case 4:
                    cancelRegister(scanner);
                    break;
                case 5:
                    changePassWord(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }

    // phan trang
    public static void pagination(Scanner scanner) {
        int choice;
        // b1 :  tổng số bản gì trong danh sách và tổng số trang
        // b2: hiển thi số trag
        // b3 : cho ng dùng chọn trang muốn xem
        // b4 :in ra ds bản ghi của trang đó
        do {
            System.out.println("1.Hiển thị danh sách khoá học có phân trang");
            System.out.println("2.Tìm kiếm khoá học có phân trang");
            System.out.println("0.Thoát");
            choice = ChoiceValidate.validateChoice(scanner);
            switch (choice) {
                case 1:
                    List<Course> listCourse = courseService.getAllCourse("STUDENT");
                    // tổng số trang = tổng số bản ghi ttong ds / số lương bản ghi tren 1 trag
                    int totalPages = (int) Math.ceil((double) listCourse.size() / 5);
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
                    List<Course> separatePages = courseService.separatePages(currentPages, "STUDENT");
                    Course course = new Course();
                    course.displayHeader("STUDENT");
                    separatePages.forEach(courses -> {
                        courses.displayData("STUDENT");
                    });
                    course.displayFooter("STUDENT");
                    break;
                case 2:
                    searchCourse(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại 0-2: ");
            }
        } while (true);
    }


    // tim kiem
    public static void searchCourse(Scanner scanner) {
        System.out.println("Nhập tên khóa học cần tìm:");
        String name = scanner.nextLine();
        List<Course> courses = courseService.searchCourse(name);

        if (courses != null && !courses.isEmpty()) {
            System.out.println("Danh sách khóa học tìm được:");
            Course course = new Course();
            course.displayHeader("STUDENT");
            courses.forEach(c -> {
                c.displayData("STUDENT");
            });
            course.displayFooter("STUDENT");
            do {
                int totalPages = (int) Math.ceil((double) courses.size() / 5);
                for (int i = 1; i <= totalPages; i++) {
                    System.out.printf("Trang %d\n", i);
                }
                System.out.printf("Lựa chọn cua bạn là (Hoặc nhấn 0 để thoát): ");
                int currentPages = Integer.parseInt(scanner.nextLine());
                if (currentPages == 0) {
                    break;
                }
                if (currentPages < 1 || currentPages > totalPages) {
                    System.err.println("Trang của bạn ko hợp lệ.");
                    continue;
                }
                int startIndex = (currentPages - 1) * 5;
                int finalIndex = Math.min(startIndex + 5, courses.size());
                List<Course> courseList = courses.subList(startIndex, finalIndex);
                Course coursess = new Course();
                coursess.displayHeader("STUDENT");
                courseList.forEach(courseses -> {
                    courseses.displayData("STUDENT");
                });
                coursess.displayFooter("STUDENT");
            } while (true);
        } else {
            System.err.println("Không tìm thấy khóa học với tên: " + name);

        }
    }

    // đăng ký khoá học
    public static void registerCourse(Scanner scanner) {

        Account account = SaveAcc.currentAcc;
        System.out.println("Gán student_id = " + account.getStudent_id());

        List<Course> courses = courseService.getAllCourse("ADMIN");
        do {
            if (courses.isEmpty()) {
                System.err.println("Không có khoá học để đăng ký");
                return;
            }
            for (int i = 0; i < courses.size(); i++) {
                System.out.printf("%d. %s. \n", i + 1, courses.get(i).getName());
            }
            System.out.println("0. Thoát");
            System.out.println("Lựa chọn của bạn là: ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                return;
            }

            if (choice < 0 || choice > courses.size()) {
                System.err.println("Lựa chọn của bạn ko hợp lệ. Vui lòng nhập lại !");
                continue;
            }

            Course selectCourse = courses.get(choice - 1);
            int courseId = selectCourse.getId();
            boolean indexCourse = courses.stream().anyMatch(course -> course.getId() == courseId);
            if (indexCourse) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent_id(account.getStudent_id());
                enrollment.setCourse_id(courseId);
                boolean check = enrollmentService.checkCourse(account.getStudent_id(),courseId);
                if (check) {
                    System.err.println("Bạn đã đăng ký khoá học này rồi");
                    return;
                }
                boolean result = enrollmentService.course_register(enrollment);
                if (result) {
                    System.out.println("đăng ký thành công !");
                } else {
                    System.err.println("Đăng ký thất baị !");
                }
            }
            break;
        } while (true);
    }
    public static void showCourseRegister(){
        Account account = SaveAcc.currentAcc;
        List<SaveCourseRegister> saveRegister = enrollmentService.saveCourse(account.getStudent_id());
        if(saveRegister.isEmpty()){
            System.err.println("Bạn chưa đăng ký khoá học nào !");
            return;
        }
        SaveCourseRegister.printTableHeader();
        saveRegister.forEach(enrollCourse->{
            enrollCourse.displayCourseRegister();
            SaveCourseRegister.printTableFooter();
        });
    }
    // đổi mk
    public static void changePassWord(Scanner scanner) {
        Account account = SaveAcc.currentAcc;
        do {
            System.out.println("Nhập mật khẩu hiện tai của bạn: ");
            String currentPass = scanner.nextLine();
            StudentValidate.validatePass(currentPass,scanner);
            System.out.println("Nhập email hiện tai của bạn: ");
            String email = scanner.nextLine();
            StudentValidate.validateEmail(email,scanner);
            if (!account.getPassword().equals(currentPass) || !account.getEmail().equals(email)){
                System.err.println("Email hoặc mat khẩu không đúng. Vui lòng nhập lại !");
                continue;
            }
            System.out.println("Nhập mật khẩu mới: ");
            String newPassWord = scanner.nextLine();
            StudentValidate.validatePass(newPassWord,scanner);
            boolean result = StudentUI.studentService.changePass(account.getStudent_id(), newPassWord);
            if (result) {
                System.out.println("Cập nhật mật khẩu thành công");
            } else {
                System.err.println("Cập nhật mật khẩu thất bại !");
            }
            break;
        } while (true);

    }
    // huỷ đky
    public static void cancelRegister(Scanner scanner){
        Account account = SaveAcc.currentAcc;
        List<SaveCourseRegister> saveRegister = enrollmentService.saveCourse(account.getStudent_id());

        List<SaveCourseRegister> filterCourse = saveRegister.stream().filter(enrollment->
            enrollment.getStatus()==RegisterStatus.WAITING
        ).toList();

        if (filterCourse.isEmpty()) {

            System.err.println("Không có khoá học nào để huỷ!");
            return;
        }
        do{
            System.out.println("Danh sách đăng ký khoá học :");
            for(int i =0; i<filterCourse.size(); i++){
                System.out.printf("%d. %s \n", i+1, filterCourse.get(i).getName());
            }
            System.out.println("0.Thoát ");
            int choice= Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                return;
            }

            if (choice < 0 || choice > filterCourse.size()) {
                System.err.println("Lựa chọn của bạn ko hợp lệ. Vui lòng nhập lại !");
                continue;
            }

            SaveCourseRegister selectCourse = filterCourse.get(choice-1);
            int courseId = selectCourse.getId();
            boolean indexCourse = filterCourse.stream().anyMatch(course -> course.getId() == courseId);
            if (indexCourse) {
                boolean result = enrollmentService.cancelRegistration(account.getStudent_id(),courseId);

                if (result) {
                    System.out.println("Huỷ đăng ký thành công !");
                } else {
                    System.err.println("Huỷ Đăng ký thất baị !");
                }
            }
            break;
        }while(true);
    }
}

