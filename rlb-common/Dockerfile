FROM openjdk:17-alpine
RUN apk add --no-cache curl
COPY target/rlb-common-0.0.1-SNAPSHOT.jar rlb-common.jar
ENTRYPOINT ["java", "-jar", "rlb-common.jar"]
