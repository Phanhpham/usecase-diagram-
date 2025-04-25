package ra.edu.business.service.Account;

import ra.edu.business.dao.Account.AccountDAO;
import ra.edu.business.dao.Account.AccountDAOImp;
import ra.edu.business.model.Account;

public class AccountServiceImp implements AccountService {
    private AccountDAO accountDAO;
    public AccountServiceImp(){

        accountDAO = new AccountDAOImp();
    }
    @Override
    public Account checkLogin(String email, String password) {

        return accountDAO.checkLogin(email,password);
    }
}
