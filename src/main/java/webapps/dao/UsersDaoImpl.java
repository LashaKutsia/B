package webapps.dao;

import webapps.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import webapps.model.User;
import webapps.model.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsersDaoImpl implements UsersDao, Job {
    private static final String DUPLICATE_KEY_ERROR = "23505";
    private static final Logger logger = LogManager.getLogger();

    private Connection connection;


    public UsersDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUser(String Id) throws DatabaseException, UserNotFoundException {
        String sql = "SELECT * FROM Users WHERE Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, Id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("Name"), rs.getString("LastName"), rs.getDouble("Balance"));
                } else {
                    logger.error("User not found in the database");
                    throw new UserNotFoundException("User not found in the database");
                }
            }
        } catch (SQLException e) {
            logger.error("Unable to get user from the database");

            throw new DatabaseException("Unable to get user from the database", e);
        }
    }

    @Override
    public void fillBalance(Transaction transaction) throws DatabaseException, UserNotFoundException {
        String sql = "UPDATE Transactions SET Amount = ? WHERE Agent_id = ?";
        String sql_1 = "INSERT INTO Payments (User_id, Amount, Status) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(true);
            try {
                try (PreparedStatement ps = connection.prepareStatement(sql);PreparedStatement pss = connection.prepareStatement(sql_1)) {
                    Double a = transaction.getAmount();

                    if (transaction.getAmount() > 0) {
                        ps.setDouble(1, a + transaction.getAmount());
                        ps.setInt(2, transaction.getAgent_id());
                        pss.setInt(1, transaction.getAgent_id());
                        pss.setDouble(2, transaction.getAmount());
                        pss.setInt(3, 0);
                    } else {
                        pss.setInt(3, 2);
                        logger.error("Amount is not true");
                        throw new RuntimeException("Amount not Positive Fault");
                    }
                    int numRows = ps.executeUpdate();
                    if (numRows == 0) {
                        pss.setInt(3, 1);
                        logger.error("User is not in database");
                        throw new UserNotFoundException("User not found in the database");
                    }
                    pss.execute();
                } catch (RuntimeException | UserNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            if (DUPLICATE_KEY_ERROR.equals(e.getSQLState())) {
                try {
                    logger.error("User already exists");
                    throw new UserAlreadyExistsException("User already exists in the database");

                } catch (UserAlreadyExistsException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            fillBalance((Transaction) this.connection);
        } catch (DatabaseException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
