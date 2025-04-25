package ra.edu.business.service.Account;

import ra.edu.business.model.Account;

public interface AccountService {
    Account checkLogin(String email, String password);
}
