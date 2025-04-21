package com.checkpoint.StudentsCourses.service;

import com.checkpoint.StudentsCourses.model.Session;
import com.checkpoint.StudentsCourses.repository.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
//    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();
//
//    public void createSession(String sessionKey, String username, List<String> roles) {
//        sessions.put(sessionKey, new SessionInfo(username, roles));
//    }
//
//    public boolean isValidSession(String sessionKey) {
//        return sessions.containsKey(sessionKey);
//    }
//
//    public String getUsername(String sessionKey) {
//        SessionInfo info = sessions.get(sessionKey);
//        return info != null ? info.username() : null;
//    }
//
//    public List<String> getRoles(String sessionKey) {
//        SessionInfo info = sessions.get(sessionKey);
//        return info != null ? info.roles() : List.of();
//    }
//
//    private record SessionInfo(String username, List<String> roles) {}

    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public String createSession(String username) {
        String sessionKey = UUID.randomUUID().toString();
        sessions.put(sessionKey, username);
        return sessionKey;
    }

    public String getUsername(String sessionKey) {
        return sessions.get(sessionKey);
    }

    public boolean isValidSession(String sessionKey) {
        return sessions.containsKey(sessionKey);
    }
}
