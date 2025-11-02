package com.leshka_and_friends.lgvb.auth;

import javax.swing.SwingUtilities;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionWatcher {
    private final SessionManager sessionManager;
    private final Runnable onSessionExpired;
    private final ScheduledExecutorService executor;

    public SessionWatcher(SessionManager sessionManager, Runnable onSessionExpired) {
        this.sessionManager = sessionManager;
        this.onSessionExpired = onSessionExpired;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        executor.scheduleAtFixedRate(() -> {
            Session session = sessionManager.getCurrentSession();
            if (session != null && session.isExpired()) {
                System.out.println("Session expired!");
                sessionManager.endSession();
                // Update UI on main thread
                SwingUtilities.invokeLater(onSessionExpired);
            }
        }, 1, 1, TimeUnit.MINUTES); // check every minute
    }

    public void stop() {

        executor.shutdownNow();
    }
}
