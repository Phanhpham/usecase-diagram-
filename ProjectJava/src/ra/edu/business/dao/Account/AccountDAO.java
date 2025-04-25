package ra.edu.business.dao.Account;

import ra.edu.business.model.Account;

public interface AccountDAO {
Account checkLogin(String email, String password);
}
