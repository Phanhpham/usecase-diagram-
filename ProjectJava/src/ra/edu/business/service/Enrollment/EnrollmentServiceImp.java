package ra.edu.business.service.Enrollment;

import ra.edu.business.dao.Course.CourseDAO;
import ra.edu.business.dao.Course.CourseDAOImp;
import ra.edu.business.dao.Enrollment.EnrollmentDAO;
import ra.edu.business.dao.Enrollment.EnrollmentDAOImp;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.SaveCourseRegister;
import ra.edu.business.model.SaveStudentForCourse;

import java.util.List;

public class EnrollmentServiceImp implements EnrollmentService {
    private EnrollmentDAO enrollmentDao;
    public EnrollmentServiceImp(){

        enrollmentDao = new EnrollmentDAOImp();
    }
    @Override
    public boolean course_register(Enrollment enrollment) {
        return enrollmentDao.course_register(enrollment);
    }

    @Override
    public boolean checkCourse(int student_id, int course_id) {
        return enrollmentDao.checkCourse(student_id,course_id);
    }

    @Override
    public List<SaveCourseRegister> saveCourse(int student_id) {
        return enrollmentDao.saveCourse(student_id);
    }

    @Override
    public Enrollment findRegisterCourse(int student_id, int course_id) {
        return enrollmentDao.findRegisterCourse(student_id, course_id);
    }

    @Override
    public boolean cancelRegistration(int student_id, int course_id) {
        return enrollmentDao.cancelRegistration(student_id, course_id);
    }

    @Override
    public List<SaveStudentForCourse> getAllStudentForCourse( int course_id) {
        return enrollmentDao.getAllStudentForCourse(course_id);
    }

    @Override
    public boolean confirmStudent(int student_id, int course_id) {
        return enrollmentDao.confirmStudent(student_id,course_id);
    }

    @Override
    public boolean deleteStudentRegister(int student_id, int course_id) {
        return enrollmentDao.deleteStudentRegister(student_id,course_id);
    }
}
