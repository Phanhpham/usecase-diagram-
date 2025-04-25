package ra.edu.business.model;

import java.util.Scanner;

public class Account {
   private int id;
   private int student_id;
   private String email;
   private String password;
   private Role role;
   private Status status;

   public Account(int id,int student_id, String email, String password, Role role, Status status) {
      this.id = id;
      this.student_id = student_id;
      this.email = email;
      this.password = password;
      this.role = role;
      this.status = status;
   }
   public Account (){

   }

   public int getId() {
      return id;
   }
   public int getStudent_id() {
      return student_id;
   }
   public String getEmail() {
      return email;
   }

   public String getPassword() {
      return password;
   }

   public Role getRole() {
      return role;
   }

   public Status getStatus() {
      return status;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setStudent_id(int student_id) {
      this.student_id = student_id;
   }

   public void setEmail(String username) {
      this.email = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public void inputData(Scanner scanner){
      System.out.println("Nhập tên email của bạn:");
      this.email =scanner.nextLine();
      System.out.println("Nhập mật khẩu tài khoản của bạn:");
      this.password=scanner.nextLine();
   }
}
