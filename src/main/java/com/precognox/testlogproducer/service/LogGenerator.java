package com.precognox.testlogproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.precognox.testlogproducer.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LogGenerator {

    private static final JsonMapper MAPPER = new JsonMapper();
    private final static Long MEM_TOTAL = 64 * 1000 * 1000000L;
    private final static Long FS_TOTAL = 5 * 1000000 * 1000000L;
    private final static String SYSTEM_USER = "SYSTEM_INFO_COLLECTOR";
    private final static int CONTAINERS_COUNT = 15;
    private final static int ES_INDEX_COUNT = 8;
    private final static long LOG_DATE_INTERVAL_MIN = 1;

    public void systemLogGenerator(int logCounts) {

        LocalDateTime startDate = getStartDate();

        for (int i = 0; i < logCounts; i++) {

            LogObject logObject;
            SystemDetails systemDetails = SystemDetails.builder()
                    .cpuCores(8)
                    .cpuThreads(12)
                    .memTotal(MEM_TOTAL)
                    .memFree(MEM_TOTAL / getRandomNumber(1, 100))
                    .cpuPrecent(getRandomNumber(1, 100))
                    .fsTotal(FS_TOTAL)
                    .fsFree(FS_TOTAL / getRandomNumber(43, 90))
                    .build();

            logObject = getLogObject(LogObject.EventType.SYSTEM_STAT, systemDetails, startDate.toString());
            logging(logObject, getRandomNumber(0, 3));

            startDate = startDate.plusMinutes(LOG_DATE_INTERVAL_MIN);
        }
    }

    public void dockerLogGenerator(int logCounts) {

        LocalDateTime startDate = getStartDate();

        for (int i = 0; i < logCounts; i++) {

            for (int j = 0; j < CONTAINERS_COUNT; j++) {
                LogObject logObject;
                DockerDetails dockerDetails = DockerDetails.builder()
                        .containerId("720da53389db_" + j)
                        .name("DOCKER_CONTAINER_" + j)
                        .memUsage(String.valueOf(getRandomNumber(300, (int) (MEM_TOTAL/2))))
                        .memPercent(String.valueOf(getRandomNumber(5,60)))
                        .build();

                logObject = getLogObject(LogObject.EventType.DOCKER_STAT, dockerDetails, startDate.toString());
                logging(logObject, getRandomNumber(0, 3));
            }
            startDate = startDate.plusMinutes(LOG_DATE_INTERVAL_MIN);
        }
    }

    public void elasticLogGenerator(int logCounts) {

        LocalDateTime startDate = getStartDate();

        for (int i = 0; i < logCounts; i++) {

            for (int j = 0; j < ES_INDEX_COUNT; j++) {
                LogObject logObject;
                long indexSize = (long)getRandomNumber(10000, 2 * 1000000);
                ElasticsearchIndexStat elasticsearchIndexStat = ElasticsearchIndexStat.builder()
                        .index("ELASTIC_INDEX_" + j)
                        .indexHealth("yellow")
                        .indexPrimary(1)
                        .indexReplica(1)
                        .indexSize(indexSize)
                        .indexSizeWithReplicas(indexSize * 2)
                        .indexUuid("SDKASDSDKASDA_" + j)
                        .docCount((long)getRandomNumber(1000, 1000000))
                        .docDeleted((long)getRandomNumber(1000, 10000))
                        .docType("doc")
                        .shardCount(getRandomNumber(4,10))
                        .indexStatus("open")
                        .build();

                logObject = getLogObject(LogObject.EventType.ELASTIC_INDEX_STAT, elasticsearchIndexStat, startDate.toString());
                logging(logObject, getRandomNumber(0, 3));
            }
            startDate = startDate.plusMinutes(LOG_DATE_INTERVAL_MIN);
        }
    }

    private LogObject getLogObject(LogObject.EventType eventType, Details details, String osi) {
        return LogObject.builder()
                .eventType(eventType)
                .processTime(getRandomNumber(1, 500))
                .realmId("TEST")
                .operationSessionId(osi)
                .userId(SYSTEM_USER)
                .details(details.getDetails())
                .build();
    }


    private int getRandomNumber(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    private void logging(LogObject logObject, int logStatus) {

        switch (logStatus) {
            case 0:
                log.info(getJsonString(logObject));
                break;
            case 1:
                logObject.setError("Some warning. Type: " + getRandomNumber(1, 5));
                log.warn(getJsonString(logObject));
                break;
            case 2:
                logObject.setError("Some error. Type: " + getRandomNumber(1, 5));
                log.error(getJsonString(logObject));
                break;
            default:
                log.error("logStatus out of range");
        }
    }

    private static String getJsonString(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            log.error("JSON conversion failed");
        }
        return "ERROR IN JSON CONVERSION";
    }

    private LocalDateTime getStartDate(){
        return LocalDateTime.now().minusDays(60);
    }

    private Date incrementMinsFor(Date date, long mins) {
        return new Date(date.getTime() + TimeUnit.MINUTES.toMillis(mins));
    }

    private Date minusDaysFrom(Date date, long days) {
        return new Date(date.getTime() - TimeUnit.DAYS.toMillis(days));
    }

}
