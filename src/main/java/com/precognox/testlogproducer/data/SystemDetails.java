package com.precognox.testlogproducer.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SystemDetails implements Details {

    private Integer cpuCores;
    private Integer cpuThreads;
    private Long memTotal;
    private Long memFree;
    private Long fsTotal;
    private Long fsFree;
    private Integer cpuPrecent;
}
