package ra.edu.business.model;

import java.time.LocalDateTime;

public class SaveCourseRegister {
    private int id;
    private String name;
    private int duration;
    private String instructor;
    private LocalDateTime create_at;
    private RegisterStatus status;

    public SaveCourseRegister(int id, String name, int duration, String instructor, LocalDateTime create_at, RegisterStatus status) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
        this.create_at = create_at;
        this.status = status;
    }
    public SaveCourseRegister(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public void setStatus(RegisterStatus status) {
        this.status = status;
    }
    // hiẻn thị
    public static void printTableHeader() {
        System.out.printf("+----+--------------------+----------+--------------------+---------------------+-----------------+\n");
        System.out.printf("| ID | Course Name        | Duration | Instructor         | Created At          | Status          |\n");
        System.out.printf("+----+--------------------+----------+--------------------+---------------------+-----------------+\n");
    }

    public void displayCourseRegister() {
        System.out.printf("| %-2d | %-18s | %-8d | %-18s | %-19s | %-15s |\n",
              id,name,duration,instructor,status,create_at);
    }
    public static void printTableFooter() {
        System.out.printf("+----+--------------------+----------+--------------------+---------------------+-----------------+\n");
    }
}
