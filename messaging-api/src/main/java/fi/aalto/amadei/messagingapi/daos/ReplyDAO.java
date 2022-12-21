package fi.aalto.amadei.messagingapi.daos;

import fi.aalto.amadei.messagingapi.beans.ReplyBean;
import fi.aalto.amadei.messagingapi.exceptions.InvalidDatabaseInteractionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplyDAO {

    public static List<ReplyBean> getAllRepliesOfThread(Connection connection, int threadID) {
        Objects.requireNonNull(connection);

        String query = "SELECT * FROM replies WHERE thread_ID = ? ORDER BY creation_time;";

        List<ReplyBean> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, threadID);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while(result.next()) {
                    ReplyBean reply = new ReplyBean();
                    reply.setReplyID(result.getInt("reply_ID"));
                    reply.setThreadID(result.getInt("thread_ID"));
                    reply.setSessionID(result.getString("session_ID"));
                    reply.setContent(result.getString("content"));
                    reply.setCreationTime(result.getTimestamp("creation_time").toInstant());

                    list.add(reply);
                }
            }
        } catch (SQLException e) {
            throw new InvalidDatabaseInteractionException(e);
        }

        return list;
    }

    public static ReplyBean addReplyToThread(Connection connection, String sessionID, int threadID, String content) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(sessionID);
        Objects.requireNonNull(content);

        String query = "SELECT * FROM add_reply(?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sessionID);
            preparedStatement.setInt(2, threadID);
            preparedStatement.setString(3, content);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if(!result.isBeforeFirst())
                    return null;

                result.next();

                ReplyBean reply = new ReplyBean();
                reply.setReplyID(result.getInt("reply_ID"));
                reply.setThreadID(result.getInt("thread_ID"));
                reply.setSessionID(result.getString("session_ID"));
                reply.setContent(result.getString("content"));
                reply.setCreationTime(result.getTimestamp("creation_time").toInstant());

                return reply;
            }
        } catch (SQLException e) {
            throw new InvalidDatabaseInteractionException(e);
        }
    }
}
