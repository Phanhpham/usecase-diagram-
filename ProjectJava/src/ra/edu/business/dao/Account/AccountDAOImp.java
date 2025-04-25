package ra.edu.business.dao.Account;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Account;
import ra.edu.business.model.Role;
import ra.edu.business.model.Status;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImp implements AccountDAO {

    @Override
    public Account checkLogin(String email, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        Account account = null;

        try {
            // 1. Mở kết nối
            conn = ConnectionDB.openConnection();

            // 2. Gọi stored procedure: check_admin_login(username, password, OUT result)
            callSt = conn.prepareCall("{CALL check_login(?, ?)}");
            callSt.setString(1, email);
            callSt.setString(2, password);

            // 3. Thực thi stored procedure
            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
                 account= new Account();
                 account.setId(rs.getInt("id"));
                account.setStudent_id(rs.getInt("student_id"));
                account.setEmail(rs.getString("email"));
                account.setPassword(rs.getString("password"));
                account.setRole(Role.valueOf(rs.getString("role")));
                account.setStatus(Status.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập account: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return account;
    }
}
