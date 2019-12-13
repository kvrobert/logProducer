package com.precognox.testlogproducer.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LogObject {

    private EventType eventType;
    private String realmId;
    private String userId;
    private String operationSessionId;
    private long processTime;
    private String error;
    private Map<String, String> details = new HashMap<>();

    public enum EventType {
        DOCKER_STAT,
        SYSTEM_STAT,
        ELASTIC_STAT,
    }
}
