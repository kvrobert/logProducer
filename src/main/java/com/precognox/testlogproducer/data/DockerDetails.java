package com.precognox.testlogproducer.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DockerDetails implements Details{

    private String containerId;
    private String name;
    private String memUsage;
    private String memPercent;

}
