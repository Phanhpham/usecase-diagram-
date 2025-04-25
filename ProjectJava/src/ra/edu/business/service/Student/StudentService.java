package ra.edu.business.service.Student;

import ra.edu.business.model.Account;
import ra.edu.business.model.Course;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Student;

import java.util.List;

public interface StudentService {
    boolean checkLoginStudent(String email, String password);
    List<Student> getAllStudent();
    List<Student> separatePage(int current_page);
    boolean addStudent(Student student);
    boolean createAccount(Student student);
    boolean deleteStudent(Student student);
    Student findIdStudent(int id);
    List<Student> searchStudent(String name , String email, int id);
    List<Student> sortStudent(String name, int id);
    boolean updateStudent(Student student , int option);
    boolean changePass(int student_id, String newPass);


}
