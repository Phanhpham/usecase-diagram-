package ra.edu.business.dao.Enrollment;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImp implements EnrollmentDAO {
    // xoá svien đã đky
    @Override
    public boolean deleteStudentRegister(int student_id, int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL delete_student_register(?,?)}");
            callSt.setInt(1, student_id);
            callSt.setInt(2, course_id);

            // 3. Thực thi stored procedure
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return false;
    }

    // duyệt dsach hoc vien da đky khoá
    @Override
    public boolean confirmStudent(int student_id, int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL confirm_Student(?,?)}");
            callSt.setInt(1, student_id);
            callSt.setInt(2, course_id);

            // 3. Thực thi stored procedure
             callSt.executeUpdate();
           return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return false;
    }

    // hien thi dsach hvien đa đky khoa hoc do
    @Override
    public List<SaveStudentForCourse> getAllStudentForCourse(int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<SaveStudentForCourse> saveStuForCourse = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL show_student_course(?)}");
            callSt.setInt(1, course_id);

            // 3. Thực thi stored procedure
            ResultSet rs = callSt.executeQuery();
            saveStuForCourse = new ArrayList<>();
            while (rs.next()) {
                SaveStudentForCourse saveStudentForCourse = new SaveStudentForCourse();
                saveStudentForCourse.setCourse_id(rs.getInt("course_id"));
                saveStudentForCourse.setStudent_id(rs.getInt("student_id"));
                saveStudentForCourse.setCourse_name(rs.getString("course_name"));
                saveStudentForCourse.setStudent_name(rs.getString("student_name"));
                saveStudentForCourse.setStatus(RegisterStatus.valueOf(rs.getString("status")));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                saveStudentForCourse.setCreate_at(LocalDateTime.parse(rs.getString("enrollment_create_at"),formatter));
                saveStuForCourse.add(saveStudentForCourse);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return saveStuForCourse;
    }

    @Override
    public Enrollment findRegisterCourse(int student_id, int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Enrollment enrollment = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call find_cancel_register(?,?)");
            callSt.setInt(1,student_id);
            callSt.setInt(2,course_id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
                enrollment= new Enrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setCourse_id(rs.getInt("course_id"));
                enrollment.setStudent_id(rs.getInt("student_id"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                enrollment.setCreate_at(LocalDateTime.parse(rs.getString("create_at"), formatter));
                enrollment.setStatus(RegisterStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return enrollment;
    }

    @Override
    public boolean cancelRegistration(int student_id, int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL cancel_register(?, ?)}");
            callSt.setInt(1, student_id);
            callSt.setInt(2, course_id);

            // 3. Thực thi stored procedure
          callSt.executeUpdate();
          return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return false;
    }

    @Override
    public List<SaveCourseRegister> saveCourse(int student_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<SaveCourseRegister> saveCourse = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL show_course_register(?)}");
            callSt.setInt(1, student_id);

            // 3. Thực thi stored procedure
            ResultSet rs = callSt.executeQuery();
            saveCourse = new ArrayList<>();
            while (rs.next()) {
              SaveCourseRegister saveCourseRegister = new SaveCourseRegister();
                saveCourseRegister.setId(rs.getInt("id"));
                saveCourseRegister.setName(rs.getString("name"));
                saveCourseRegister.setDuration(rs.getInt("duration"));
                saveCourseRegister.setInstructor(rs.getString("instructor"));
                saveCourseRegister.setStatus(RegisterStatus.valueOf(rs.getString("status")));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                saveCourseRegister.setCreate_at(LocalDateTime.parse(rs.getString("create_at"),formatter));
                saveCourse.add(saveCourseRegister);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return saveCourse;
    }

    @Override
    public boolean checkCourse(int student_id, int course_id) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL check_courses_register(?, ?)}");
            callSt.setInt(1, student_id);
            callSt.setInt(2, course_id);

            // 3. Thực thi stored procedure
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("check_course");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }

        return false;
    }

    @Override
    public boolean course_register(Enrollment enrollment) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL courses_register(?, ?)}");
            callSt.setInt(1, enrollment.getStudent_id());
            callSt.setInt(2, enrollment.getCourse_id());

            // 3. Thực thi stored procedure
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return false;
    }
}
