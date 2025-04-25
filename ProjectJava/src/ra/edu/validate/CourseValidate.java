package ra.edu.validate;

import ra.edu.business.model.Course;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CourseValidate {

    // tên khoá học ko dc trùng lặp
    public static boolean isCourseNameDuplicate(String courseName, List<Course> courseList, int id) {
        for (Course course : courseList) {
            if (course.getName().equalsIgnoreCase(courseName) && course.getId() != id ) {
                return true;
            }
        }
        return false;
    }
    // Tên khóa học không được rỗng, tối đa 100 ký tự
    public static boolean isValidCourseName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

    // Thời lượng là số nguyên dương
    public static boolean isValidDuration(int duration) {
        try {
            return duration > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Giảng viên không được rỗng, tối đa 100 ký tự
    public static boolean isValidInstructor(String instructor) {
        return instructor != null && !instructor.trim().isEmpty() && instructor.length() <= 100;
    }
    // Regex kiểm tra ngày
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");


}
