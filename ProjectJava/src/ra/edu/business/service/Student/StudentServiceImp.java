package ra.edu.business.service.Student;
import ra.edu.business.dao.Student.StudentDAO;
import ra.edu.business.dao.Student.StudentDAOImp;
import ra.edu.business.model.Account;
import ra.edu.business.model.Course;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Student;

import java.util.List;

public class StudentServiceImp implements StudentService {
    private StudentDAO studentDAO;
    public StudentServiceImp(){

        studentDAO = new StudentDAOImp();
    }

    @Override
    public boolean checkLoginStudent(String email, String password) {
        return studentDAO.checkLoginStudent(email,password);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentDAO.getAllStudent();
    }

    @Override
    public List<Student> separatePage(int current_page) {
        return studentDAO.separatePage(current_page);
    }

    @Override
    public boolean createAccount(Student student) {
        return studentDAO.createAccount(student);
    }

    @Override
    public boolean addStudent(Student student) {
        return studentDAO.addStudent(student);
    }

    @Override
    public boolean deleteStudent(Student student) {
        return studentDAO.deleteStudent(student);
    }

    @Override
    public Student findIdStudent(int id) {
        return studentDAO.findIdStudent(id);
    }

    @Override
    public List<Student> searchStudent(String name, String email, int id) {
        return studentDAO.searchStudent(name, email,id);
    }

    @Override
    public List<Student> sortStudent(String name, int id) {
        return studentDAO.sortStudent(name,id);
    }

    @Override
    public boolean updateStudent(Student student, int option) {
        return studentDAO.updateStudent( student,option);

    }

    @Override
    public boolean changePass(int student_id, String newPass) {

        return studentDAO.changePass(student_id,newPass);
    }


}