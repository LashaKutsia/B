package webapps.ws;

import webapps.dao.DatabaseException;
import webapps.dao.DatabaseManager;
import webapps.dao.UsersDao;
import webapps.dao.UsersDaoImpl;
import webapps.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.Connection;

public class UserCrudServiceImpl implements UserCrudService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Response userById(String Id) {
        Connection connection = null;
        try {
            logger.info("Opening database connection");
            connection = DatabaseManager.getDatabaseConnection();

            UsersDao usersDao = new UsersDaoImpl(connection);
            logger.info("Getting user from the database, user_id: {}", Id);
            User user = usersDao.getUser(Id);
            logger.trace("User got from the database: {}", user);

            return Response.status(Status.OK).entity(user).build();
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return Response.status(Status.NOT_FOUND).build();
        } catch (DatabaseException e) {
            logger.error("Database exception", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } finally {
            DatabaseManager.close(connection);
        }
    }

    @Override
    public Response fillBalance(Transaction transaction) {
        Connection connection = null;
        try {
            logger.info("Opening database connection");
            connection = DatabaseManager.getDatabaseConnection();

            UsersDao usersDao = new UsersDaoImpl(connection);
            logger.info("Updating user to the database: {}", transaction);
            usersDao.fillBalance(transaction);
            logger.trace("user updated to the database");

            return Response.status(Status.OK).build();
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return Response.status(Status.NOT_FOUND).build();
        } catch (DatabaseException e) {
            logger.error("Database exception", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } finally {
            DatabaseManager.close(connection);
        }
    }
}
