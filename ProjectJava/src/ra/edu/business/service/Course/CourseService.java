package ra.edu.business.service.Course;

import ra.edu.business.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> separatePages(int current_page, String role);
    List<Course> getAllCourse(String role);
   boolean addAllCourse(Course course);
    boolean updateCourse(Course course , int option);
    Course findIdCourse(int id);
    boolean deleteCourse(Course course);
    List<Course> searchCourse( String name);
    List<Course> sortCourse( String name, int id);
}
