package com.checkpoint.StudentsCourses.dto;

public class AuthResponseDTO {
    private String sessionKey;

    public AuthResponseDTO(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
