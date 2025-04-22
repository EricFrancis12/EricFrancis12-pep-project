package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private final AccountDAO acctDAO = new AccountDAO();

    public Account addAccount(Account acct) {
        return acctDAO.insertAccount(acct);
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        return acctDAO.getAccountByUsernameAndPassword(username, password);
    }
}
