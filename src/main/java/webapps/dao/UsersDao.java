package webapps.dao;

import webapps.model.Transaction;
import webapps.model.User;
import webapps.model.UserNotFoundException;

public interface UsersDao {
    User getUser(String Id) throws DatabaseException, UserNotFoundException;

    void fillBalance(Transaction transaction) throws DatabaseException, UserNotFoundException;
}
