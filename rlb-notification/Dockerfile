FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY target/*.jar rlb-notification.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "eureka-server", "8761", "--", "java", "-jar", "rlb-notification.jar"]


