package com.precognox.testlogproducer.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public interface Details {

    @JsonIgnore
    default Map<String, Object> getDetails() {
        return  new ObjectMapper().convertValue(this, new TypeReference<Map<String, Object>>() {});
    };
}
