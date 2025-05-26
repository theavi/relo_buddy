FROM openjdk:17-alpine

# install curl for healthcheck
RUN apk add --no-cache curl

ARG JAR_FILE=target/rlb-oc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
