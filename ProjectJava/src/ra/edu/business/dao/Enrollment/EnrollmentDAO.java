package ra.edu.business.dao.Enrollment;

import ra.edu.business.model.Course;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.SaveCourseRegister;
import ra.edu.business.model.SaveStudentForCourse;

import java.util.List;

public interface EnrollmentDAO {
    boolean course_register(Enrollment enrollment);
    boolean checkCourse(int student_id,int course_id);
    List<SaveCourseRegister> saveCourse(int student_id);
    Enrollment findRegisterCourse(int student_id,int course_id);
    boolean cancelRegistration(int student_id,int course_id);
    List<SaveStudentForCourse> getAllStudentForCourse(int course_id);
    boolean confirmStudent(int student_id, int course_id);
    boolean deleteStudentRegister(int student_id, int course_id);

}
