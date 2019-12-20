package com.precognox.testlogproducer.data;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class LogObject {

    private EventType eventType;
    private String realmId;
    private String userId;
    private String operationSessionId;
    private long processTime;
    private String error;
    private Map<String, Object> details;

    public enum EventType {
        DOCKER_STAT,
        SYSTEM_STAT,
        ELASTIC_INDEX_STAT,
    }
}
