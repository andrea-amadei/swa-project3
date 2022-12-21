package fi.aalto.amadei.messagingapi.controllers;

import fi.aalto.amadei.messagingapi.DatabaseConnectionInitializer;
import fi.aalto.amadei.messagingapi.daos.AuthenticationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RoutineScheduler {
    private final Logger logger = LoggerFactory.getLogger(RoutineScheduler.class);

    @Scheduled(cron = "0 */10 * * * *")    // Every 10 minutes
    public void scheduleInactiveSessionCleanup() {
        logger.info("Starting inactive users cleanup");
        AuthenticationDAO.cleanupInactiveSessions(DatabaseConnectionInitializer.getConnection());
    }
}
