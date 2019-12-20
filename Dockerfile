FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT java $JAVA_OPTS -jar /app.jar
