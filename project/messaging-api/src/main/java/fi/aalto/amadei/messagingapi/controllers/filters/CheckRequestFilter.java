package fi.aalto.amadei.messagingapi.controllers.filters;

import fi.aalto.amadei.messagingapi.beans.UserSessionBean;
import fi.aalto.amadei.messagingapi.exceptions.InvalidUserSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class CheckRequestFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(CheckRequestFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserSessionBean session = UserSessionBean.getSessionFromRequest(request);

        if(session == null)
            throw new InvalidUserSessionException("Unable to confirm user session");

        chain.doFilter(request, response);
    }
}
