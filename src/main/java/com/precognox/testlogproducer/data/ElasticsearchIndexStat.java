package com.precognox.testlogproducer.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ElasticsearchIndexStat implements Details {

    private String index;
    private String indexHealth;
    private String indexStatus;
    private String indexUuid;
    private Integer indexPrimary;
    private Integer indexReplica;
    private Long indexSize;
    private Integer shardCount;
    private Long indexSizeWithReplicas;
    private String docType;
    private Long docCount;
    private Long docDeleted;
}
