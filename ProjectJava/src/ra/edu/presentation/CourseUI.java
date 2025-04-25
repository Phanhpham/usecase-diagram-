package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.model.SaveStudentForCourse;
import ra.edu.business.service.Course.CourseService;
import ra.edu.business.service.Course.CourseServiceImp;
import ra.edu.validate.ChoiceValidate;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.UserUI.enrollmentService;

public class CourseUI {
    public static CourseService courseService = new CourseServiceImp();

    public static void printMenuCourse(Scanner scanner) {
        int choice;

        do {
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m        ======= QUẢN LÝ KHÓA HỌC =======        \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.println("\u001B[34m1. Hiển thị danh sách khóa học               \u001B[0m");
            System.out.println("\u001B[34m2. Thêm mới khóa học                        \u001B[0m");
            System.out.println("\u001B[34m3. Chỉnh sửa thông tin khóa học             \u001B[0m");
            System.out.println("\u001B[34m4. Xóa khóa học (xác nhận trước khi xóa)   \u001B[0m");
            System.out.println("\u001B[34m5. Tìm kiếm theo tên                        \u001B[0m");
            System.out.println("\u001B[34m6. Sắp xếp theo tên hoặc ID (tăng/giảm dần)\u001B[0m");
            System.out.println("\u001B[34m7. Quay về menu chính                       \u001B[0m");
            System.out.println("\u001B[34m══════════════════════════════════════════════\u001B[0m");
            System.out.print("\u001B[34mNhập lựa chọn của bạn: \u001B[0m");

            choice = ChoiceValidate.validateChoice(scanner);

            switch (choice) {
                case 1:
                    pagination(scanner);
                    break;
                case 2:
                    addCourse(scanner);
                    break;
                case 3:
                    updateCourse(scanner);
                    break;
                case 4:
                    deleteCourse(scanner);
                    break;
                case 5:
                    searchCourse(scanner);
                    break;
                case 6:
                    sortCourse(scanner);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            System.out.println();
        } while (true);
    }

    // phan trang
    public static void pagination(Scanner scanner) {
        // b1 :  tổng số bản gì trong danh sách và tổng số trang
        // b2: hiển thi số trag
        // b3 : cho ng dùng chọn trang muốn xem
        // b4 :in ra ds bản ghi của trang đó
        do {
            List<Course> listCourse = courseService.getAllCourse("ADMIN");
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
            List<Course> separatePages = courseService.separatePages(currentPages,"ADMIN");
            Course course = new Course();
            course.displayHeader("ADMIN");
            separatePages.forEach(courses -> {
                courses.displayData("ADMIN");
            });
            course.displayFooter("ADMIN");
        } while (true);
    }

    //them khoa học--
    public static void addCourse(Scanner scanner) {
        System.out.println("Nhập số khoá học cần thêm:");
        int size = ChoiceValidate.validateChoice(scanner);
        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin khoá học thư: " + (i + 1));
            Course course = new Course();
            course.inputData(scanner);
            boolean result = courseService.addAllCourse(course);
            if (result) {
                System.out.println("them khoá học thành công");
            } else {
                System.err.println("thêm khoá học thất baị!");
            }

        }
    }

    //update khoa hoc
    public static void updateCourse(Scanner scanner) {
        System.out.println("nhập id khoá học cần cập nhật:");
        int id = ChoiceValidate.validateChoice(scanner);
        Course course = courseService.findIdCourse(id);
        if (course != null) {
            do {
                System.out.println("========== Lựa chọn Cập Nhật ==========");
                System.out.println("1. Cập nhật theo tên khoá học");
                System.out.println("2. Cập nhật theo thời lượng");
                System.out.println("3. Cập nhật theo tên giảng viên");
                System.out.println("4. Trở về menu quản lý");
                System.out.print("Nhập lựa chọn của bạn: ");

                int choice = ChoiceValidate.validateChoice(scanner);
                switch (choice) {
                    case 1:
                        System.out.println("Nhập tên khoá học mới: ");
                        course.setName(course.inputCourseName(scanner));
                        boolean result1 = courseService.updateCourse(course, 1);
                        if (result1) {
                            System.out.println("cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 2:
                        int newDuration = course.inputCourseDuration(scanner);
                        course.setDuration(newDuration);
                        boolean result2 = courseService.updateCourse(course, 2);
                        if (result2) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 3:
                        course.setInstructor(course.inputInstructorName(scanner));
                        boolean result3 = courseService.updateCourse(course, 3);
                        if (result3) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                }
            } while (true);
        } else {
            System.err.println("Lỗi! Không tìm thấy khoá học");
        }
    }

    // xoá khoá học
    public static void deleteCourse(Scanner scanner) {
        System.out.println("Nhập id khoá học cần xoá:");
        int id = ChoiceValidate.validateChoice(scanner);
        List<SaveStudentForCourse> saveStuForCourse = enrollmentService.getAllStudentForCourse(id);
        if(!saveStuForCourse.isEmpty()){
            System.err.println("Khoá học đang có sinh viên . Không thể xoá !");
            return;
        }
        if (courseService.findIdCourse(id) != null) {
            System.out.println("Bạn có chắc chăn muốn xoá không ?");
            String confirm = scanner.nextLine();
            if (confirm.equals("y")) {
                Course course = new Course();
                course.setId(id);
                boolean result = courseService.deleteCourse(course);
                if (result) {
                    System.out.println("xoá thành công!");
                } else {
                    System.err.println("xoá thất bại!");
                }
            } else if (confirm.equals("n")) {
                System.out.println("Huỷ");
            }
        } else {

            System.err.println("Không tìm thấy khoá học");
        }
    }

    // tìm kiếm khoá học
    public static void searchCourse(Scanner scanner) {
        System.out.println("Nhập tên khóa học cần tìm:");
        String name = scanner.nextLine();
        List<Course> courses = courseService.searchCourse(name);

        if (courses != null && !courses.isEmpty()) {
            System.out.println("Danh sách khóa học tìm được:");
            Course course = new Course();
            course.displayHeader("ADMIN");
            courses.forEach(c -> {
                c.displayData("ADMIN");
            });
            course.displayFooter("ADMIN");
            do{
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
                int startIndex = (currentPages-1)*5;
                int finalIndex = Math.min(startIndex+5,courses.size());
                List<Course> courseList = courses.subList(startIndex, finalIndex);
                Course coursess = new Course();
                coursess.displayHeader("ADMIN");
                courseList.forEach(courseses -> {
                    courseses.displayData("ADMIN");
                });
                coursess.displayFooter("ADMIN");
            }while(true);
        } else {
            System.err.println("Không tìm thấy khóa học với tên: " + name);

        }
    }
    public static void sortCourse(Scanner scanner) {
        // Giả sử danh sách các khóa học đã có trong một list
        List<Course> courses = courseService.getAllCourse("ADMIN");

        if (courses != null && !courses.isEmpty()) {
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
                    courses.sort(Comparator.comparing(Course::getName));
                    break;
                case 2:
                    courses.sort(Comparator.comparing(Course::getName).reversed());
                    break;
                case 3:
                    courses.sort(Comparator.comparingInt(Course::getId)); // Tăng dần theo ID
                    break;
                case 4:
                    courses.sort(Comparator.comparingInt(Course::getId).reversed());
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Sử dụng mặc định (tăng dần theo tên).");
                    courses.sort(Comparator.comparing(Course::getName));
                    break;
            }
            // Hiển thị kết quả sau khi sắp xếp
            System.out.println("Danh sách khóa học sau khi sắp xếp:");
            Course course = new Course();
            course.displayHeader("ADMIN");
            courses.forEach(c -> c.displayData("ADMIN"));
            course.displayFooter("ADMIN");

            // Phân trang
            int pageSize = 5;
            int totalPages = (int) Math.ceil((double) courses.size() / pageSize); // Tính số trang

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
                int endIndex = Math.min(startIndex + pageSize, courses.size());
                List<Course> pageCourses = courses.subList(startIndex, endIndex);

                // Hiển thị danh sách khóa học trên trang hiện tại
                course.displayHeader("ADMIN");
                pageCourses.forEach(c -> c.displayData("ADMIN"));
                course.displayFooter("ADMIN");
            } while (true);
        } else {
            System.err.println("Danh sách khóa học trống.");
        }
    }
}
