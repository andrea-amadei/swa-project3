package fi.aalto.amadei.messagingapi.controllers;

import fi.aalto.amadei.messagingapi.DatabaseConnectionInitializer;
import fi.aalto.amadei.messagingapi.beans.ThreadBean;
import fi.aalto.amadei.messagingapi.beans.UserSessionBean;
import fi.aalto.amadei.messagingapi.beans.responses.SuccessBean;
import fi.aalto.amadei.messagingapi.daos.ThreadDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ThreadController {

    @GetMapping("/threads")
    public SuccessBean<List<ThreadBean>> getAllThreads(HttpServletRequest request) {
        return new SuccessBean<>(
                UserSessionBean.getSessionFromRequest(request),
                ThreadDAO.getAllThreads(DatabaseConnectionInitializer.getConnection())
                );
    }

    @PostMapping("/threads")
    public SuccessBean<ThreadBean> createNewThread(HttpServletRequest request,
        @RequestBody(required = true) String payload
    ) {
        UserSessionBean session = UserSessionBean.getSessionFromRequest(request);

        ThreadBean thread = ThreadDAO.addThread(
                DatabaseConnectionInitializer.getConnection(), session.getSessionID(), payload
        );

        return new SuccessBean<>(session, thread);
    }
}
