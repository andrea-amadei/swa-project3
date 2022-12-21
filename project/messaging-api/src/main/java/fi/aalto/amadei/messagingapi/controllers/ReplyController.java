package fi.aalto.amadei.messagingapi.controllers;

import fi.aalto.amadei.messagingapi.DatabaseConnectionInitializer;
import fi.aalto.amadei.messagingapi.beans.ReplyBean;
import fi.aalto.amadei.messagingapi.beans.ThreadBean;
import fi.aalto.amadei.messagingapi.beans.UserSessionBean;
import fi.aalto.amadei.messagingapi.beans.responses.SuccessBean;
import fi.aalto.amadei.messagingapi.daos.ReplyDAO;
import fi.aalto.amadei.messagingapi.daos.ThreadDAO;
import fi.aalto.amadei.messagingapi.exceptions.ElementNotFoundException;
import fi.aalto.amadei.messagingapi.exceptions.InvalidParameterTypeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ReplyController {

    @GetMapping("/replies")
    public SuccessBean<List<ReplyBean>> getAllReplies(HttpServletRequest request,
        @RequestParam(value = "threadID", required = true) String threadID
    ) {
        UserSessionBean session = UserSessionBean.getSessionFromRequest(request);

        int id;
        try {
            id = Integer.parseInt(threadID);
        } catch (NumberFormatException e) {
            throw new InvalidParameterTypeException("Query parameter threadID must be integer");
        }

        ThreadBean thread = ThreadDAO.getThreadByID(DatabaseConnectionInitializer.getConnection(), id);

        if(thread == null)
            throw new ElementNotFoundException("No thread found with id " + id);

        return new SuccessBean<>(
                session,
                ReplyDAO.getAllRepliesOfThread(
                        DatabaseConnectionInitializer.getConnection(),
                        id
                )
        );
    }

    @PostMapping("/replies")
    public SuccessBean<ReplyBean> addNewSubmission(HttpServletRequest request,
        @RequestParam(value = "threadID", required = true) String threadID,
        @RequestBody(required = true) String payload
    ) {
        UserSessionBean session = UserSessionBean.getSessionFromRequest(request);

        int id;
        try {
            id = Integer.parseInt(threadID);
        } catch (NumberFormatException e) {
            throw new InvalidParameterTypeException("Query parameter threadID must be integer");
        }

        ThreadBean thread = ThreadDAO.getThreadByID(DatabaseConnectionInitializer.getConnection(), id);

        if(thread == null)
            throw new ElementNotFoundException("No thread found with id " + id);

        ReplyBean submission = ReplyDAO.addReplyToThread(
                DatabaseConnectionInitializer.getConnection(), session.getSessionID(), id, payload
        );

        return new SuccessBean<>(session, submission);
    }
}
