package ra.edu.business.dao.Course;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImp implements CourseDAO{
    // sap xêp khoa hoc
    @Override
    public List<Course> sortCourse(String name, int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> listCourses= null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call sortCourse(?,?)");
            callSt.setString(1,name);
            callSt.setInt(1,id);

            ResultSet rs = callSt.executeQuery();
            listCourses = new ArrayList();
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreate_at(rs.getDate("create_at").toLocalDate());
                listCourses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return null;
    }
    // tim kiếm khoa học
    @Override
    public List<Course> searchCourse(String name) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> listCourses= null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call searchCourse(?)");
            callSt.setString(1,name);

            ResultSet rs = callSt.executeQuery();
            listCourses = new ArrayList();
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreate_at(rs.getDate("create_at").toLocalDate());
                listCourses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCourses;
    }

    //xoá khoa hoc
    @Override
    public boolean deleteCourse(Course course) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call deleteCourse(? )");
            callSt.setInt(1,course.getId());

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
    public boolean updateCourse(Course course, int option) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call updateCourse(?, ?, ? ,?,?)");
            callSt.setInt(1,course.getId());
            callSt.setInt(2,option);
            if(option == 1){
                callSt.setString(3,course.getName());
                callSt.setNull(4, Types.INTEGER);
                callSt.setNull(5,Types.VARCHAR);
            }else if(option == 2){
                callSt.setNull(3,Types.VARCHAR);
                callSt.setInt(4,course.getDuration());
                callSt.setNull(5,Types.VARCHAR);
            }else if(option ==3){
                callSt.setNull(3,Types.VARCHAR);
                callSt.setNull(4, Types.INTEGER);
                callSt.setString(5,course.getInstructor());
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

    @Override
    public Course findIdCourse(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Course course = null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();
            
            callSt = conn.prepareCall("call findCourseById(?)");
            callSt.setInt(1,id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
                course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreate_at(rs.getDate("create_at").toLocalDate());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return course;
    }

    @Override
    public boolean addCourses(Course course) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call add_courses(?, ?, ? )");
            callSt.setString(1,course.getName());
            callSt.setInt(2,course.getDuration());
            callSt.setString(3,course.getInstructor());

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
    public List<Course> getAllCourses(String role) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> listCourses= null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call get_all_courses(?)");
            callSt.setString(1,role);
            ResultSet rs = callSt.executeQuery();
            listCourses = new ArrayList();
            while(rs.next()){
                Course course = new Course();
                if(role.equals("ADMIN")){
                    course.setId(rs.getInt("id"));
                }
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreate_at(rs.getDate("create_at").toLocalDate());
                listCourses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập admin: " + e.getMessage());
        } finally {
            // 5. Đóng kết nối
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCourses;
    }
    @Override
    public List<Course> separatePage(int current_page, String role) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> listCourses= null;
        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            callSt = conn.prepareCall("call get_courses_by_page (?,?)");
            callSt.setInt(1,current_page);
            callSt.setString(2,role);

            ResultSet rs = callSt.executeQuery();
            listCourses = new ArrayList();
            while(rs.next()){
               Course course = new Course();
               course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreate_at(rs.getDate("create_at").toLocalDate());
                listCourses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập admin: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCourses;
    }
}
