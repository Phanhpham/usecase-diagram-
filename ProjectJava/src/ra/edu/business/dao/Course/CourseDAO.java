package ra.edu.business.dao.Course;

import ra.edu.business.model.Course;

import java.util.List;

public interface CourseDAO {
    List<Course> separatePage(int current_page,String role);

    List<Course> getAllCourses(String role);
    boolean addCourses(Course course);

    boolean updateCourse(Course course , int option);

    Course findIdCourse(int id);
    boolean deleteCourse(Course course);

    List<Course> searchCourse( String name);
    List<Course> sortCourse( String name, int id);

}
