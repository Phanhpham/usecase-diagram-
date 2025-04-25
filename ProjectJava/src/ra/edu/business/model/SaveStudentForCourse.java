package ra.edu.business.model;

import java.time.LocalDateTime;

public class SaveStudentForCourse {
    private int course_id;
    private String course_name;
    private int student_id;
    private String student_name;
    private LocalDateTime create_at;
    private RegisterStatus status;
    public SaveStudentForCourse(){

    }

    public SaveStudentForCourse(int course_id, String course_name, int student_id, String student_name, LocalDateTime create_at, RegisterStatus status) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.student_id = student_id;
        this.student_name = student_name;
        this.create_at = create_at;
        this.status = status;
    }

    public int getCourse_id() {
        return course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public RegisterStatus getStatus() {
        return status;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public void setStatus(RegisterStatus status) {
        this.status = status;
    }
        // In header table
        public static void printTableHeaders() {
            System.out.printf("+-----------+--------------------+-------------+--------------------+---------------------+-----------------+\n");
            System.out.printf("| Course ID | Course Name        | Student ID  | Student Name        | Created At          | Status          |\n");
            System.out.printf("+-----------+--------------------+-------------+--------------------+---------------------+-----------------+\n");
        }

        // Display course registration information
        public void displayTable() {
            System.out.printf("| %-9d | %-18s | %-11d | %-18s | %-19s | %-15s |\n",
                    course_id, course_name, student_id, student_name, create_at, status);
        }

        // In footer table
        public static void printTableFooters() {
            System.out.printf("+-----------+--------------------+-------------+--------------------+---------------------+-----------------+\n");
        }
    }
