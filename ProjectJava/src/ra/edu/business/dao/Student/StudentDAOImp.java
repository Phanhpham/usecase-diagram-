package ra.edu.business.dao.Student;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Account;
import ra.edu.business.model.Course;
import ra.edu.business.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImp implements StudentDAO {
    @Override
    public boolean changePass(int student_id, String newPass) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call password_change(?, ?)");
            callSt.setInt(1,student_id);
            callSt.setString(2,newPass);
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }
    // update hoc vien
    @Override
    public boolean updateStudent(Student student, int option) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call updateStudent(?, ?, ? ,?,?,?,?,?)");
            callSt.setInt(1,student.getId());
            callSt.setInt(2,option);
            if (option == 1) {
                callSt.setString(3, student.getName());
                callSt.setNull(4, Types.DATE);  // Thay vì INT cho "dob"
                callSt.setNull(5, Types.VARCHAR);  // Thay vì VARCHAR cho "email"
                callSt.setNull(6, Types.BIT);  // Thay vì BIT cho "sex"
                callSt.setNull(7, Types.VARCHAR);  // Thay vì VARCHAR cho "phone"
                callSt.setNull(8, Types.VARCHAR);  // Thay vì VARCHAR cho "password"
            } else if (option == 2) {
                callSt.setNull(3, Types.VARCHAR);  // Thay vì "name"
                callSt.setDate(4, Date.valueOf(student.getDob()));
                callSt.setNull(5, Types.VARCHAR);  // Cập nhật "email"
                callSt.setNull(6, Types.BIT);  // Cập nhật "sex"
                callSt.setNull(7, Types.VARCHAR);  // Cập nhật "phone"
                callSt.setNull(8, Types.VARCHAR);  // Cập nhật "password"
            } else if (option == 3) {
                callSt.setNull(3, Types.VARCHAR);  // Thay vì "name"
                callSt.setNull(4, Types.DATE);  // Thay vì "dob"
                callSt.setString(5, student.getEmail());  // Cập nhật "email"
                callSt.setNull(6, Types.BIT);  // Cập nhật "sex"
                callSt.setNull(7, Types.VARCHAR);  // Cập nhật "phone"
                callSt.setNull(8, Types.VARCHAR);  // Cập nhật "password"
            } else if (option == 4) {
                callSt.setNull(3, Types.VARCHAR);  // Thay vì "name"
                callSt.setNull(4, Types.DATE);  // Thay vì "dob"
                callSt.setNull(5, Types.VARCHAR);  // Thay vì "email"
                callSt.setBoolean(6, student.getSex());  // Cập nhật "sex" với kiểu BIT
                callSt.setNull(7, Types.VARCHAR);  // Cập nhật "phone"
                callSt.setNull(8, Types.VARCHAR);  // Cập nhật "password"
            } else if (option == 5) {
                callSt.setNull(3, Types.VARCHAR);  // Thay vì "name"
                callSt.setNull(4, Types.DATE);  // Thay vì "dob"
                callSt.setNull(5, Types.VARCHAR);  // Thay vì "email"
                callSt.setNull(6, Types.BIT);  // Cập nhật "sex"
                callSt.setString(7, student.getPhone());  // Cập nhật "phone"
                callSt.setNull(8, Types.VARCHAR);  // Cập nhật "password"
            } else if (option == 6) {
                callSt.setNull(3, Types.VARCHAR);  // Thay vì "name"
                callSt.setNull(4, Types.DATE);  // Thay vì "dob"
                callSt.setNull(5, Types.VARCHAR);  // Thay vì "email"
                callSt.setNull(6, Types.BIT);  // Cập nhật "sex"
                callSt.setNull(7, Types.VARCHAR);  // Cập nhật "phone"
                callSt.setString(8, student.getPassword());  // Cập nhật "password"
            }

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    // sap xep theo ten ,id (tăng dan , giam dan )
    @Override
    public List<Student> sortStudent(String name, int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> listStudent= null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call sortStudent(?,?)");
            callSt.setString(1,name);
            callSt.setInt(1,id);

            ResultSet rs = callSt.executeQuery();
            listStudent = new ArrayList();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreate_at(rs.getDate("create_at").toLocalDate());
                listStudent.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return null;
    }

    // tìm kiếm hoc viên theo tên , id ,email
    @Override
    public List<Student> searchStudent(String name, String email, int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> listStudent= null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call searchStudent(?,?,?)");
            callSt.setInt(1,id);
            callSt.setString(2,name);
            callSt.setString(3,email);

            ResultSet rs = callSt.executeQuery();
            listStudent = new ArrayList();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreate_at(rs.getDate("create_at").toLocalDate());
                listStudent.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listStudent;
    }

    // tìm id hoc vien de xoa
    @Override
    public Student findIdStudent(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Student student = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call findStudentById(?)");
            callSt.setInt(1,id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreate_at(rs.getDate("create_at").toLocalDate());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return student;
    }

    // xoá hoc vien
    @Override
    public boolean deleteStudent(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call deleteStudent(?)");
            callSt.setInt(1,student.getId());

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean createAccount(Student student) {

        Connection conn = null;
        CallableStatement callSt = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call create_account( ?, ?,?)");
            callSt.setInt(1,student.getId());
            callSt.setString(2,student.getEmail());
            callSt.setString(3,student.getPassword());
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;

    }

    //thêm hoc vien
    @Override
    public boolean addStudent(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call add_student(?, ?, ?, ?, ?, ?,?)");
            callSt.setString(1,student.getName());
            callSt.setString(2,student.getDob().toString());
            callSt.setString(3,student.getEmail());
            callSt.setBoolean(4,student.getSex());
            callSt.setString(5,student.getPhone());
            callSt.setString(6,student.getPassword());

            callSt.registerOutParameter(7, Types.INTEGER);
            callSt.executeUpdate();

            int studentId = callSt.getInt(7);

            // Gán ID cho đối tượng student
            student.setId(studentId);
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;

    }

    //phan trag khoá học
    @Override
    public List<Student> separatePage(int current_page) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> listStudent= null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call get_student_by_page (?)");
            callSt.setInt(1,current_page);
            ResultSet rs = callSt.executeQuery();
            listStudent = new ArrayList();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreate_at(rs.getDate("create_at").toLocalDate());
                listStudent.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập user: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listStudent;
    }
    //hiẻn thị dsach
    @Override
    public List<Student> getAllStudent() {

        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> listStudent= null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call get_all_students()");
            ResultSet rs = callSt.executeQuery();
            listStudent = new ArrayList();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDob(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                student.setCreate_at(rs.getDate("create_at").toLocalDate());
                listStudent.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập student: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listStudent;
    }

    @Override
    public boolean checkLoginStudent(String username, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password)
            callSt = conn.prepareCall("{CALL check_user_login(?, ?)}");
            callSt.setString(1, username);
            callSt.setString(2, password);

            // 3. Thực thi stored procedure
            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập admin: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }
}

