package ra.edu.business.model;

import java.time.LocalDateTime;

public class Enrollment {
    private int id;
    private int student_id;
    private int course_id;
    private LocalDateTime create_at;
    private RegisterStatus status;

    public Enrollment(int id, int student_id, int course_id, LocalDateTime create_at, RegisterStatus status) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.create_at = create_at;
        this.status = status;
    }

    public Enrollment() {
    }

    public int getId() {
        return id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public RegisterStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public void setStatus(RegisterStatus status) {
        this.status = status;
    }
}

