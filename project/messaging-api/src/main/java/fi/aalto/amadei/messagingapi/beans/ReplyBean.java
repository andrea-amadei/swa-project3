package fi.aalto.amadei.messagingapi.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({ "reply_ID", "thread_ID", "content", "created_by", "created_at" })
public class ReplyBean {
    @JsonProperty("reply_ID")
    private int replyID;
    @JsonProperty("thread_ID")
    private int threadID;
    @JsonProperty("created_by")
    private String sessionID;

    private String content;

    @JsonProperty("created_at")
    private Instant creationTime;

    public int getReplyID() {
        return replyID;
    }

    public void setReplyID(int replyID) {
        this.replyID = replyID;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
}
