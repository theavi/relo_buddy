FROM openjdk:17-alpine
RUN apk add --no-cache curl
COPY target/rlb-core-0.0.1-SNAPSHOT.jar rlb-core.jar
ENTRYPOINT ["java", "-jar", "rlb-core.jar"]
