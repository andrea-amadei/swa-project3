package fi.aalto.amadei.messagingapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.UnavailableException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DatabaseConnectionInitializer {
    private final Logger logger = LoggerFactory.getLogger(DatabaseConnectionInitializer.class);

    @Value("${DB_FULL:}")
    private String dbFull;

    @Value("${DB_USER:postgres}")
    private String dbUser;

    @Value("${DB_PASSWORD:MyDatabasePassword00}")
    private String dbPassword;

    @Value("${DB_HOST:localhost}")
    private String dbHost;

    @Value("${DB_PORT:5432}")
    private String dbPort;

    @Value("${DB_DATABASE:messaging}")
    private String dbName;

    private static Connection connection;

    @PostConstruct
    public void init() throws UnavailableException {
        if(!(dbFull.isBlank() || dbFull == null)) {
            String[] arguments = dbFull.trim().split(":");

            if(arguments.length == 5) {
                dbHost = arguments[0];
                dbPort = arguments[1];
                dbName = arguments[2];
                dbUser = arguments[3];
                dbPassword = arguments[4];
            }
        }

        String dbURL = String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName);
        String dbDriver = "org.postgresql.Driver";

        logger.info("Connecting to '{}' with username '{}' and password '{}'", dbURL, dbUser, dbPassword);

        try {
            Class.forName(dbDriver);

            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new UnavailableException("Couldn't load database driver");
        } catch (SQLException e) {
            throw new UnavailableException("Couldn't connect to database: " + e.getMessage());
        }

        if(connection == null)
            throw new UnavailableException("Couldn't connect to database");

        logger.info("Successfully connected to database");
    }

    public static Connection getConnection() {
        return connection;
    }
}
