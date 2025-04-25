package ra.edu.business.dao.Statistic;

import ra.edu.business.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticDAOImp implements  StatisticDAO {
    @Override
    public Map<Integer, Integer> getTotalCourseAndStudent() {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_total_course_student()");

            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                int totalCourse = rs.getInt("count_course");
                int totalStudent = rs.getInt("count_student");

                map.put(totalCourse, totalStudent);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return map;
    }

    @Override
    public Map<String, Integer> getTotalStudentOfCourse() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_total_student_of_course()");

            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                String nameCourse = rs.getString("course_name");
                int totalStudent = rs.getInt("count_student");

                map.put( nameCourse, totalStudent);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return map;
    }

    @Override
    public Map<String, Integer> getTop5CourseMostStudent() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_top5_course_most_student()");

            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                String nameCourse = rs.getString("course_name");
                int totalStudent = rs.getInt("count_student");

                map.put( nameCourse, totalStudent);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return map;
    }

    @Override
    public Map<String, Integer> getCourseMoreThan10Students() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_course_more_than_10students()");

            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                String nameCourse = rs.getString("course_name");
                int totalStudent = rs.getInt("count_student");

                map.put( nameCourse, totalStudent);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return map;
    }
}