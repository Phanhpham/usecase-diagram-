package ra.edu.business.service.Course;


import ra.edu.business.dao.Course.CourseDAO;
import ra.edu.business.dao.Course.CourseDAOImp;
import ra.edu.business.model.Course;

import java.util.List;

public class CourseServiceImp implements CourseService{
    private CourseDAO courseDao;
    public CourseServiceImp(){

        courseDao = new CourseDAOImp();
    }
    @Override
    public List<Course> separatePages(int current_page, String role) {
        return courseDao.separatePage(current_page, role);
    }

    @Override
    public List<Course> getAllCourse(String role) {
        return courseDao.getAllCourses(role);
    }

    @Override
    public boolean addAllCourse(Course course) {
        return courseDao.addCourses(course);
    }

    @Override
    public boolean updateCourse(Course course, int option) {

        return courseDao.updateCourse(course,option);
    }

    @Override
    public Course findIdCourse(int id) {
        return  courseDao.findIdCourse(id);
    }

    @Override
    public boolean deleteCourse(Course course) {
        return courseDao.deleteCourse(course);
    }

    @Override
    public List<Course> searchCourse(String name) {
        return courseDao.searchCourse(name);
    }

    @Override
    public List<Course> sortCourse(String name, int id) {
        return courseDao.sortCourse(name,id);
    }
}
