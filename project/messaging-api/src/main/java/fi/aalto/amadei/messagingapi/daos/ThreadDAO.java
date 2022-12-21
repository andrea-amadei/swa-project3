package fi.aalto.amadei.messagingapi.daos;

import fi.aalto.amadei.messagingapi.beans.ThreadBean;
import fi.aalto.amadei.messagingapi.exceptions.InvalidDatabaseInteractionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThreadDAO {

    public static final int MAX_VISIBLE_THREADS = 20;

    public static List<ThreadBean> getAllThreads(Connection connection) {
        Objects.requireNonNull(connection);

        String query = "SELECT * FROM threads_and_replies LIMIT ?;";

        List<ThreadBean> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MAX_VISIBLE_THREADS);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    ThreadBean thread = new ThreadBean();
                    thread.setThreadID(result.getInt("thread_ID"));
                    thread.setSessionID(result.getString("session_ID"));
                    thread.setContent(result.getString("content"));
                    thread.setCreationTime(result.getTimestamp("creation_time").toInstant());
                    thread.setReplies(result.getInt("n_replies"));

                    list.add(thread);
                }
            }
        } catch (SQLException e) {
            throw new InvalidDatabaseInteractionException(e);
        }

        return list;
    }

    public static ThreadBean getThreadByID(Connection connection, int threadID) {
        Objects.requireNonNull(connection);

        String query = "SELECT * FROM threads_and_replies WHERE thread_ID = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, threadID);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if(!result.isBeforeFirst())
                    return null;

                result.next();

                ThreadBean thread = new ThreadBean();
                thread.setThreadID(result.getInt("thread_ID"));
                thread.setSessionID(result.getString("session_ID"));
                thread.setContent(result.getString("content"));
                thread.setCreationTime(result.getTimestamp("creation_time").toInstant());
                thread.setReplies(result.getInt("n_replies"));

                return thread;
            }
        } catch (SQLException e) {
            throw new InvalidDatabaseInteractionException(e);
        }
    }

    public static ThreadBean addThread(Connection connection, String sessionID, String content) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(sessionID);
        Objects.requireNonNull(content);

        String query = "SELECT * FROM add_thread(?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sessionID);
            preparedStatement.setString(2, content);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if(!result.isBeforeFirst())
                    return null;

                result.next();

                ThreadBean thread = new ThreadBean();
                thread.setThreadID(result.getInt("thread_ID"));
                thread.setSessionID(result.getString("session_ID"));
                thread.setContent(result.getString("content"));
                thread.setCreationTime(result.getTimestamp("creation_time").toInstant());
                thread.setReplies(0);

                return thread;
            }
        } catch (SQLException e) {
            throw new InvalidDatabaseInteractionException(e);
        }
    }
}
